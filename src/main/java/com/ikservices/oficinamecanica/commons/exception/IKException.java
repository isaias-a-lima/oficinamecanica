package com.ikservices.oficinamecanica.commons.exception;

import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IKException extends RuntimeException{
    private IKMessage ikMessage;

    public IKException(String message) {
        super(message);
    }

    public IKException(String message, Throwable cause) {
        super(message, cause);
    }

    public IKException(IKMessage ikMessage) {
        super(ikMessage.getMessage());
        this.ikMessage = ikMessage;
    }

    public IKException(IKMessage ikMessage,  Throwable cause) {
        super(ikMessage.getMessage(), cause);
        this.ikMessage = ikMessage;
    }

    public static IKException build(String code, IKMessageType type, String message) {
        return new IKException(new IKMessage(code, type.getCode(), message));
    }
}
