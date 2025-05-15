package com.ikservices.oficinamecanica.commons.exception;

import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class IKExceptionHandler {

    private static final Logger LOGGER = IKLoggerUtil.getLogger(IKExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<IKResponse<Object>> handleValidations(MethodArgumentNotValidException ex) {
        LOGGER.info("Handler begin.");

        List<IKMessage> messages = new ArrayList<>();

        ex.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String fieldMessage = error.getDefaultMessage();
            messages.add(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), fieldMessage));
        });

        IKResponse<Object> response = IKResponse.<Object>build();
        response.getMessages().addAll(messages);
        LOGGER.info("Handler end.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
