package com.ikservices.oficinamecanica.users.domain;

import com.ikservices.oficinamecanica.commons.vo.CPFVO;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "cpf")
public class User {

    private Long cpf;
    private String nome;
    private String email;
    private String senha;
    private boolean ativo;

    public User(Long cpf, String nome, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = false;
    }

}