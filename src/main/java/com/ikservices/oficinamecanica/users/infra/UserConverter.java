package com.ikservices.oficinamecanica.users.infra;

import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.users.infra.controller.requests.UserResponse;
import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserConverter {

    public UserEntity toEntity(User user) {
        return new UserEntity(user.getCpf(), user.getNome(), user.getEmail(),
                user.getSenha(), user.isAtivo());
    }

    public User toModel(UserEntity entity) {
        return new User(entity.getCpf(), entity.getNome(), entity.getEmail(),
                entity.getSenha(), entity.isAtivo());
    }

    public UserResponse toUserDTO(User user) {
        return new UserResponse(
                user.getCpf(),
                user.getNome(),
                user.getEmail(),
                user.isAtivo()
        );
    }

    public List<UserResponse> toUserDTOList(List<User> userList) {
        List<UserResponse> list = new ArrayList<>();
        if (Objects.nonNull(userList) && !userList.isEmpty()) {
            for (User user : userList) {
                list.add(toUserDTO(user));
            }
        }
        return list;
    }

    public List<User> toUserList(List<UserEntity> entityList) {
        List<User> list = new ArrayList<>();
        if (Objects.nonNull(entityList) && !entityList.isEmpty()) {
            for (UserEntity userEntity : entityList) {
                list.add(toModel(userEntity));
            }
        }
        return list;
    }

    public UserResponse toUserDTO(UserEntity principal) {
        return new UserResponse(
                principal.getCpf(),
                principal.getNome(),
                principal.getEmail(),
                principal.isAtivo()
        );
    }
}
