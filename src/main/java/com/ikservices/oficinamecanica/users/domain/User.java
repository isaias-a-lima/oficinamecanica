package com.ikservices.oficinamecanica.users.domain;

import com.ikservices.oficinamecanica.commons.vo.CPFVO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "cpf")
public class User {
    @Getter
    private Long cpf;
    @Getter
    @Setter
    @NonNull
    private String nome;
    @Getter
    @Setter
    @NonNull
    private String email;
    @Getter
    @Setter
    @NonNull
    private String senha;
    @Getter
    @Setter
    @NonNull
    private boolean ativo;

    public User(Long cpf, String nome, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = false;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }
}