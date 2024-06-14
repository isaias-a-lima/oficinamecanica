package com.ikservices.oficinamecanica.customers.infra.controller;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.MessageType;
import com.ikservices.oficinamecanica.commons.response.ResponseIKS;
import com.ikservices.oficinamecanica.customers.application.usecases.ListCustomers;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final ListCustomers listCustomers;

    public CustomerController(ListCustomers listCustomers) {
        this.listCustomers = listCustomers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseIKS<Customer>> listCustomers(@PathVariable("id") Long id) {

        List<Customer> customerList = null;
        try {
            customerList = listCustomers.execute(id);
        } catch (IKException ike) {
            int code = Objects.nonNull(ike.getCode()) ? ike.getCode() : HttpStatus.INTERNAL_SERVER_ERROR.value();
            return ResponseEntity.status(code).body(ResponseIKS.<Customer>build().addMessage(MessageType.WARNING, ike.getMessage()));
        }
        return ResponseEntity.ok(ResponseIKS.<Customer>build().body(customerList));
    }
}
