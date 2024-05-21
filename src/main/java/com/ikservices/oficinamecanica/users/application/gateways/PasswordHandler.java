package com.ikservices.oficinamecanica.users.application.gateways;

public interface PasswordHandler {
    String encode(String password);

    Boolean matches(String password, String passwordHash);
}
