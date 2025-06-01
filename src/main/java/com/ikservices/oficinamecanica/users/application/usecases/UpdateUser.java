package com.ikservices.oficinamecanica.users.application.usecases;

import com.ikservices.oficinamecanica.users.application.business.CPFUpdateRule;
import com.ikservices.oficinamecanica.users.application.business.UserBusiness;
import com.ikservices.oficinamecanica.users.application.business.UserUpdateRules;
import com.ikservices.oficinamecanica.users.application.gateways.UserProperties;
import com.ikservices.oficinamecanica.users.application.gateways.UserRepository;
import com.ikservices.oficinamecanica.users.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateUser {

    private final UserRepository repository;
    private final List<UserBusiness> userBusinessList = new ArrayList<>();

    public UpdateUser(UserRepository repository) {
        this.repository = repository;
        this.userBusinessList.add(new CPFUpdateRule());
    }

    public User execute(User newUser, User oldUser) {
        Object[] users = {newUser, oldUser};
        UserUpdateRules.validate(userBusinessList, users);
        return repository.updateUser(newUser);
    }
}
