package com.ikservices.oficinamecanica.users.application;

import com.ikservices.oficinamecanica.users.domain.User;

public class UserException extends RuntimeException {

    private String code;

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, String code) {
        super(message);
        this.code = code;
    }

    public UserException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
