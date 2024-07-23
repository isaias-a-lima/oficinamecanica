package com.ikservices.oficinamecanica.users.application.usecases;

import com.ikservices.oficinamecanica.users.application.gateways.UserRepository;
import com.ikservices.oficinamecanica.users.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListarUsuarios {

    private final UserRepository repository;

    public ListarUsuarios(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> execute() {
        List<User> userList = repository.getUserList();
        if (Objects.isNull(userList)) {
            userList = new ArrayList<>();
        }
        return userList;
    }
}
