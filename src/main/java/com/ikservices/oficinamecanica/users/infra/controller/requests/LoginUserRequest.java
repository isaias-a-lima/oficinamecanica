package com.ikservices.oficinamecanica.users.infra.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserRequest {
    private String username;
    private String password;
}
