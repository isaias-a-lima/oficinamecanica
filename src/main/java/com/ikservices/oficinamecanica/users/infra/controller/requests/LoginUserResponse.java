package com.ikservices.oficinamecanica.users.infra.controller.requests;

import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserResponse {
    private Long cpf;
    private String nome;
    private String email;
    private boolean ativo;
    private String token;

    public static LoginUserResponse toLoginUserResponse(UserEntity userEntity) {
        return new LoginUserResponse(
                userEntity.getCpf(),
                userEntity.getNome(),
                userEntity.getEmail(),
                userEntity.isAtivo(),
                ""
        );
    }

    public LoginUserResponse setToken(String token) {
        this.token = token;
        return this;
    }
}
