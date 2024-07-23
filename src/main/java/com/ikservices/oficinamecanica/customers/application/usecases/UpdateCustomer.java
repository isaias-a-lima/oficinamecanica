package com.ikservices.oficinamecanica.customers.application.usecases;

import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;

public class UpdateCustomer {

    private final CustomerRepository repository;

    public UpdateCustomer(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer updateCustomer(Customer customer) {
        return repository.updateCustomer(customer);
    }
}
