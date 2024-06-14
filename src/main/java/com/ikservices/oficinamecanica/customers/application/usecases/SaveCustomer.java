package com.ikservices.oficinamecanica.customers.application.usecases;

import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;

public class SaveCustomer {

    private final CustomerRepository repository;

    public SaveCustomer(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer saveCustomer(Customer customer) {
        return repository.saveCustomer(customer);
    }
}
