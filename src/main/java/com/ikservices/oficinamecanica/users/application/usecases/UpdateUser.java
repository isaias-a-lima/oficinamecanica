package com.ikservices.oficinamecanica.users.application.usecases;

import com.ikservices.oficinamecanica.users.application.gateways.UserProperties;
import com.ikservices.oficinamecanica.users.application.gateways.UserRepository;
import com.ikservices.oficinamecanica.users.domain.User;

public class UpdateUser {

    private final UserRepository repository;
    private final UserProperties properties;

    public UpdateUser(UserRepository repository, UserProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    public User execute(User user) {
        return repository.updateUser(user);
    }
}
