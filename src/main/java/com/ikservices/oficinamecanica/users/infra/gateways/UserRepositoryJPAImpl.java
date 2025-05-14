package com.ikservices.oficinamecanica.users.infra.gateways;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.users.application.UserException;
import com.ikservices.oficinamecanica.users.application.gateways.UserProperties;
import com.ikservices.oficinamecanica.users.application.gateways.UserRepository;
import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.users.infra.UserConverter;
import com.ikservices.oficinamecanica.users.infra.constants.UserConstants;
import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;
import com.ikservices.oficinamecanica.users.infra.persistence.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserRepositoryJPAImpl implements UserRepository {

    private final UserRepositoryJPA repository;
    private final UserConverter converter;
    private final UserProperties properties;
    @Autowired
    @Lazy
    private Environment environment;

    public UserRepositoryJPAImpl(UserRepositoryJPA repository,
                                 UserConverter converter,
                                 UserProperties properties) {
        this.repository = repository;
        this.converter = converter;
        this.properties = properties;
    }

    @Override
    public User createUser(User user) {
        Optional<UserEntity> optional = repository.findById(user.getCpf());
        if (optional.isPresent()) {
            throw new IKException(new IKMessage(Constants.IK_HTTP_ALREADY_EXISTS, IKMessageType.WARNING.getCode(), UserConstants.SAVE_ERROR_ALREADY_EXISTS_MESSAGE));
        }
        UserEntity saved = repository.save(converter.toEntity(user));
        return converter.toModel(saved);
    }

    @Override
    public User updateUser(User user) {
        Optional<UserEntity> optional = repository.findById(user.getCpf());
        if (!optional.isPresent()) {
            throw new IKException(new IKMessage(Constants.IK_HTTP_NOT_FOUND_CODE, IKMessageType.WARNING.getCode(), UserConstants.USER_TO_UPDATE_NOT_FOUND_MESSAGE));
        }
        UserEntity userEntity = optional.get();
        userEntity.atualizar(converter.toEntity(user));
        return converter.toModel(userEntity);
    }

    @Override
    public User findUser(Long cpf) {
        Optional<UserEntity> optional = repository.findById(cpf);

        if(!optional.isPresent()) {
            throw new IKException(new IKMessage(Constants.IK_HTTP_NOT_FOUND_CODE, IKMessageType.WARNING.getCode(), UserConstants.USER_NOT_FOUND_MESSAGE));
        }

        return converter.toModel(optional.get());
    }

    @Override
    public List<User> getUserList() {
        List<UserEntity> userEntityList = repository.findAllByActiveTrue();
        if (Objects.isNull(userEntityList)) {
            throw new UserException(properties.getErrorNotFoundDatas());
        }
        return converter.toUserList(userEntityList);
    }

    @Override
    public Boolean removeUser(Long cpf) {
        UserEntity userEntity = repository.findByCpf(cpf);
        if (Objects.isNull(userEntity)) {
            throw new UserException(properties.getErrorNotFoundDatas(), String.valueOf(HttpStatus.NOT_FOUND.value()));
        }
        userEntity.inativar();
        return true;
    }

    @Override
    public UserEntity loginUser(String username) throws UserException {
        return repository.findByUsername(username);
    }
}
