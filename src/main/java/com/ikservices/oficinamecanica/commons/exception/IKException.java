package com.ikservices.oficinamecanica.commons.exception;

public class IKException extends RuntimeException{
    private String code;

    public IKException(String message) {
        super(message);
    }

    public IKException(String message, Throwable cause) {
        super(message, cause);
    }

    public IKException(String code, String message) {
        super(message);
        this.code = code;
    }

    public IKException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
