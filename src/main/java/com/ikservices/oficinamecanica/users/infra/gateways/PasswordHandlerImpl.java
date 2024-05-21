package com.ikservices.oficinamecanica.users.infra.gateways;

import com.ikservices.oficinamecanica.users.application.gateways.PasswordHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHandlerImpl implements PasswordHandler {

    PasswordEncoder passwordEncoder;

    public PasswordHandlerImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Boolean matches(String password, String passwordHash) {
        return passwordEncoder.matches(password, passwordHash);
    }
}
