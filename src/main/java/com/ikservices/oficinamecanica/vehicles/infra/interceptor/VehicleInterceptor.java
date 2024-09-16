package com.ikservices.oficinamecanica.vehicles.infra.interceptor;

import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.vehicles.infra.controller.VehicleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class VehicleInterceptor {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<IKResponse<VehicleResponse>> badRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(IKResponse.<VehicleResponse>build().addMessage(IKMessageType.WARNING, "Parâmetro inválido."));
    }
}
