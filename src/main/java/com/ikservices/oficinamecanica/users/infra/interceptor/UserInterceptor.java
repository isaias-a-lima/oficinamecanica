package com.ikservices.oficinamecanica.users.infra.interceptor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserInterceptor {

    //@ExceptionHandler()
    public ResponseEntity teste() {
        return ResponseEntity.status(401).build();
    }
}
