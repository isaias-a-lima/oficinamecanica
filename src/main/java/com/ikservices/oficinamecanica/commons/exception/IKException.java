package com.ikservices.oficinamecanica.commons.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IKException extends RuntimeException{
    private Integer code;

    public IKException(String message) {
        super(message);
    }

    public IKException(String message, Throwable cause) {
        super(message, cause);
    }

    public IKException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public IKException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
