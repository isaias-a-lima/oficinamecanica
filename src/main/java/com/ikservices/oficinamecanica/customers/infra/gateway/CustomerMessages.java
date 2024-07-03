package com.ikservices.oficinamecanica.customers.infra.gateway;

import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

public class CustomerMessages implements IKMessages {

    private final Environment environment;

    public CustomerMessages(Environment environment) {
        this.environment = environment;
    }

    @Override
    public IKMessage getPropertyMessage(String propertyKey) {
        String codeKey = propertyKey + ".code";
        String typeKey = propertyKey + ".type";
        String messageKey = propertyKey + ".message";

        Integer code = Integer.parseInt(Objects.requireNonNull(environment.getProperty(codeKey)));
        IKMessageType messageType = IKMessageType.getByCode(Integer.parseInt(Objects.requireNonNull(environment.getProperty(typeKey))));
        String message = environment.getProperty(messageKey);

        return new IKMessage(code, messageType, message);
    }
}
