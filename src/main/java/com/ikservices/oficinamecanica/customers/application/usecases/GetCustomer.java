package com.ikservices.oficinamecanica.customers.application.usecases;

import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;

public class GetCustomer {

    private final CustomerRepository repository;

    public GetCustomer(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer getCustomer(Long id) {
        return repository.getCustomer(id);
    }
}
