package com.ikservices.oficinamecanica.users.infra.controller.requests;

import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long cpf;
    private String nome;
    private String email;
    private boolean ativo;

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
                userEntity.getNome(),
                userEntity.getEmail(),
                userEntity.isAtivo()
        );
    }
}
