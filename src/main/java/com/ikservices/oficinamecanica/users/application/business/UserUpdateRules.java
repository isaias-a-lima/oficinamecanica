package com.ikservices.oficinamecanica.users.application.business;

import com.ikservices.oficinamecanica.users.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateRules {

    private final List<UserBusiness> userBusinessList;

    private UserUpdateRules(List<UserBusiness> userBusinessList) {
        this.userBusinessList = userBusinessList;
    }

    public static void validate(List<UserBusiness> userBusinessList, Object[] users) {
        UserUpdateRules obj = new UserUpdateRules(userBusinessList);
        obj.userBusinessList.forEach(v -> v.validate(users));
    }
}
