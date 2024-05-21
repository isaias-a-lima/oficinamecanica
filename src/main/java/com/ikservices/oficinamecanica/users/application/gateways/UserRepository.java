package com.ikservices.oficinamecanica.users.application.gateways;

import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.users.application.UserException;
import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;

import java.util.List;

public interface UserRepository {

    User createUser(User user);

    User updateUser(User user);

    User findUser(Long cpf);

    List<User> getUserList();

    Boolean removeUser(Long cpf);

    UserEntity loginUser(String email) throws UserException;
}
