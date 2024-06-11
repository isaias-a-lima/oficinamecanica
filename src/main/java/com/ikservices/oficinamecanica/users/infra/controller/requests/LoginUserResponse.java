package com.ikservices.oficinamecanica.users.infra.controller.requests;

import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserResponse {
    private Long cpf;
    private String name;
    private String email;
    private boolean active;
    private String token;

    public static LoginUserResponse toLoginUserResponse(UserEntity userEntity) {
        return new LoginUserResponse(
                userEntity.getCpf(),
                userEntity.getName(),
                userEntity.getUsername(),
                userEntity.isActive(),
                ""
        );
    }

    public LoginUserResponse setToken(String token) {
        this.token = token;
        return this;
    }
}
