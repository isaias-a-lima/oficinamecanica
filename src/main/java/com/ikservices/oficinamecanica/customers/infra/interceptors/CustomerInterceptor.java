package com.ikservices.oficinamecanica.customers.infra.interceptors;

import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.customers.infra.constants.CustomerConstants;
import com.ikservices.oficinamecanica.customers.infra.controller.CustomerResponse;
import com.ikservices.oficinamecanica.customers.infra.gateway.CustomerMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class CustomerInterceptor {

    private final CustomerMessages customerMessages;

    public CustomerInterceptor(CustomerMessages customerMessages) {
        this.customerMessages = customerMessages;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<IKResponse<CustomerResponse>> entityNotFound() {
        IKMessage ikMessage = customerMessages.getPropertyMessage(CustomerConstants.CUSTOMER_NOT_FOUND_MESSAGE_KEY);
        return ResponseEntity.status(ikMessage.getCode()).body(
                IKResponse.<CustomerResponse>build().addMessage(ikMessage.getIKMessageType(), ikMessage.getMessage())
        );
    }
}
