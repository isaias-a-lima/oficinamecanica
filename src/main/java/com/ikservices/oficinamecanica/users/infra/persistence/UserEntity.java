package com.ikservices.oficinamecanica.users.infra.persistence;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "USUARIOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cpf")
public class UserEntity implements UserDetails {

    @Id
    private Long cpf;
    private String nome;
    private String email;
    private String senha;
    private boolean ativo;

    public void atualizar(UserEntity entity) {
        if (Objects.nonNull(entity.getNome()) && !entity.getNome().isEmpty()) {
            this.nome = entity.getNome();
        }
        if (Objects.nonNull(entity.getEmail()) && !entity.getEmail().isEmpty()) {
            this.email = entity.getEmail();
        }
        if (Objects.nonNull(entity.getSenha()) && !entity.getSenha().isEmpty()) {
            this.senha = entity.getSenha();
        }
    }

    public void inativar() {
        this.ativo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
