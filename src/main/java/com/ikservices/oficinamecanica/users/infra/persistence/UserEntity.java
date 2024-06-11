package com.ikservices.oficinamecanica.users.infra.persistence;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "USERS")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cpf")
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "cpf")
    @Getter
    private Long cpf;

    @Column(name = "name")
    @Getter
    private String name;

    @Column(name = "email")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    @Getter
    private boolean active;

    public void atualizar(UserEntity entity) {
        if (Objects.nonNull(entity.getName()) && !entity.getName().isEmpty()) {
            this.name = entity.getName();
        }
        if (Objects.nonNull(entity.getUsername()) && !entity.getUsername().isEmpty()) {
            this.username = entity.getUsername();
        }
        if (Objects.nonNull(entity.getPassword()) && !entity.getPassword().isEmpty()) {
            this.password = entity.getPassword();
        }
    }

    public void inativar() {
        this.active = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
