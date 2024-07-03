package com.ikservices.oficinamecanica.commons.exception;

import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IKException extends RuntimeException{
    private Integer code;
    private IKMessageType IKMessageType;

    public IKException(String message) {
        super(message);
    }

    public IKException(String message, Throwable cause) {
        super(message, cause);
    }

    public IKException(Integer code, IKMessageType IKMessageType, String message) {
        super(message);
        this.code = code;
        this.IKMessageType = IKMessageType;
    }

    public IKException(Integer code, IKMessageType IKMessageType, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.IKMessageType = IKMessageType;
    }
}
