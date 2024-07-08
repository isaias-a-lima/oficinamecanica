package com.ikservices.oficinamecanica.customers.application.usecases;

import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;

public class GetCustomer {

    private final CustomerRepository repository;

    public GetCustomer(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer execute(CustomerId id) {
        return repository.getCustomer(id);
    }
}
