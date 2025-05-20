package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import lombok.Getter;

import java.util.Objects;

@Getter
public class PasswordVO {

    private final String password;

    public PasswordVO(String password) {
        if (Objects.isNull(password) || password.isEmpty()) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.PASSWORD_NULL_OR_EMPTY_MESSAGE));
        }

        if (!password.matches(IKConstants.PASSWORD_LATIN_PATTERN)) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.PASSWORD_NO_LATIN_MESSAGE));
        }

        if (!password.matches(IKConstants.PASSWORD_PATTERN)) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.PASSWORD_INVALID_MESSAGE));
        }

        this.password = password;
    }
}
