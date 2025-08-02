package com.ikservices.oficinamecanica.users.application.business;

import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.users.domain.User;

public class CPFUpdateRule implements UserBusiness{

    private static final String ERROR_MESSAGE = "CPF não pode ser alterado.";

    @Override
    public void validate(Object[] users) {
        User newUser = (User) users[0];
        User oldUser = (User) users[1];
        if (!newUser.getCpf().equals(oldUser.getCpf())) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), ERROR_MESSAGE));
        }
    }
}
