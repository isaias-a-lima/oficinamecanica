package com.ikservices.oficinamecanica.users.infra.controller.requests;

import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long cpf;
    private String name;
    private String email;
    private boolean active;

    public static UserResponse parse(User user) {
        return new UserResponse(
                user.getCpf(),
                user.getNome(),
                user.getEmail(),
                user.isAtivo()
        );
    }

    public static UserResponse parse(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getCpf(),
                userEntity.getName(),
                userEntity.getUsername(),
                userEntity.isActive()
        );
    }

    public static List<UserResponse> parse(List<User> userList) {
        List<UserResponse> list = new ArrayList<>();
        if (Objects.nonNull(userList) && !userList.isEmpty()) {
            for (User user : userList) {
                list.add(parse(user));
            }
        }
        return list;
    }
}
