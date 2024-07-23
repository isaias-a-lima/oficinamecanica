package com.ikservices.oficinamecanica.users.application.usecases;

import com.ikservices.oficinamecanica.users.application.gateways.UserProperties;
import com.ikservices.oficinamecanica.users.application.gateways.UserRepository;
import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.users.application.UserException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

public class LogarUsuario implements UserDetailsService {

    private final UserRepository repository;

    public LogarUsuario(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.loginUser(username);
    }
}
