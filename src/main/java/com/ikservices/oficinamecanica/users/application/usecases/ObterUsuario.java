package com.ikservices.oficinamecanica.users.application.usecases;

import com.ikservices.oficinamecanica.users.application.UserException;
import com.ikservices.oficinamecanica.users.application.gateways.UserProperties;
import com.ikservices.oficinamecanica.users.application.gateways.UserRepository;
import com.ikservices.oficinamecanica.users.domain.User;

import java.util.Objects;

public class ObterUsuario {
    private final UserRepository repository;
    private final UserProperties properties;

    public ObterUsuario(UserRepository repository, UserProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    public User execute(Long cpf) {
        if (Objects.isNull(cpf)) {
            throw new UserException(properties.getErrorNullObject("CPF"));
        }
        return repository.findUser(cpf);
    }
}
