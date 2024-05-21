package com.ikservices.oficinamecanica.users.domain;

public class UserFactory {

    private User user;

    public UserFactory withCpfNomeEmailSenha(Long cpf, String nome, String email, String senha) {
        this.user = new User(cpf, nome, email, senha);
        return this;
    }

    public UserFactory ativo() {
        this.user.ativar();
        return this;
    }

    public User build() {
        return this.user;
    }
}
