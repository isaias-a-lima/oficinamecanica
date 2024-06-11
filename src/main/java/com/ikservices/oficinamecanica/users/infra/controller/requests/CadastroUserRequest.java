package com.ikservices.oficinamecanica.users.infra.controller.requests;

import com.ikservices.oficinamecanica.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CadastroUserRequest {

    private Long cpf;
    private String name;
    private String email;
    @Setter
    private String password;
    private Boolean active;

    public User toUser() {
        return new User(cpf, name, email, password);
    }
}
