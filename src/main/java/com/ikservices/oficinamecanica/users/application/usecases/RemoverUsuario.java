package com.ikservices.oficinamecanica.users.application.usecases;

import com.ikservices.oficinamecanica.users.application.UserException;
import com.ikservices.oficinamecanica.users.application.gateways.UserProperties;
import com.ikservices.oficinamecanica.users.application.gateways.UserRepository;

public class RemoverUsuario {

    private final UserRepository repository;
    private final UserProperties properties;

    public RemoverUsuario(UserRepository repository, UserProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    public Boolean execute(Long cpf) throws UserException {
        return repository.removeUser(cpf);
    }
}
