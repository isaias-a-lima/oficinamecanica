package com.ikservices.oficinamecanica.users.infra.gateways;

import com.ikservices.oficinamecanica.users.application.UserException;
import com.ikservices.oficinamecanica.users.application.gateways.UserProperties;
import com.ikservices.oficinamecanica.users.application.gateways.UserRepository;
import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.users.infra.UserConverter;
import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;
import com.ikservices.oficinamecanica.users.infra.persistence.UserRepositoryJPA;

import java.util.List;
import java.util.Objects;

public class UserRepositoryJPAImpl implements UserRepository {

    private final UserRepositoryJPA repository;
    private final UserConverter converter;
    private final UserProperties properties;

    public UserRepositoryJPAImpl(UserRepositoryJPA repository,
                                 UserConverter converter,
                                 UserProperties properties) {
        this.repository = repository;
        this.converter = converter;
        this.properties = properties;
    }

    @Override
    public User createUser(User user) {
        UserEntity saved = repository.save(converter.toEntity(user));
        return converter.toModel(saved);
    }

    @Override
    public User updateUser(User user) {
        UserEntity userEntity = repository.findByCpf(user.getCpf());
        userEntity.atualizar(converter.toEntity(user));
        return converter.toModel(userEntity);
    }

    @Override
    public User findUser(Long cpf) {
        UserEntity userEntity = repository.findByCpf(cpf);
        if (Objects.isNull(userEntity)) {
            throw new UserException(properties.getErrorNotFoundDatas());
        }
        return converter.toModel(userEntity);
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
        userEntity.inativar();
        return true;
    }

    @Override
    public UserEntity loginUser(String username) throws UserException {
        return repository.findByUsername(username);
    }
}
