package com.ikservices.oficinamecanica.customers.infra.controller;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.customers.application.usecases.GetCustomer;
import com.ikservices.oficinamecanica.customers.application.usecases.ListCustomers;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
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

    private final GetCustomer getCustomer;
    private final CustomerConverter converter;

    public CustomerController(ListCustomers listCustomers, CustomerConverter converter,
                              GetCustomer getCustomer) {
        this.listCustomers = listCustomers;
        this.converter = converter;
        this.getCustomer = getCustomer;
    }

    @GetMapping("/workshop/{id}")
    public ResponseEntity<IKResponse<CustomerResponse>> listCustomers(@PathVariable("id") Long workshopId) {

        List<CustomerResponse> customerList = null;
        try {
            customerList = converter.customerResponseList(listCustomers.execute(workshopId));
        } catch (IKException ike) {
            return ResponseEntity.status(ike.getCode()).body(IKResponse.<CustomerResponse>build().addMessage(ike.getIKMessageType(), ike.getMessage()));
        }
        return ResponseEntity.ok(IKResponse.<CustomerResponse>build().body(customerList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IKResponse<CustomerResponse>> getCustomer(@PathVariable("id") Long id) {
        Customer customer = getCustomer.execute(id);
        return ResponseEntity.ok(IKResponse.<CustomerResponse>build().body(new CustomerResponse(id, customer)));
    }
}
