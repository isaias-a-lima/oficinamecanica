package com.ikservices.oficinamecanica.users.infra.controller.requests;

import com.ikservices.oficinamecanica.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CadastroUserRequest {

    private Long cpf;
    private String nome;
    private String email;
    private String senha;
    private Boolean ativo;

    public User toUser() {
        return new User(cpf, nome, email, senha);
    }
}
