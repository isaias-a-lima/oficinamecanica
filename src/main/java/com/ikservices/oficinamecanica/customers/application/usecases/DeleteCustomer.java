package com.ikservices.oficinamecanica.customers.application.usecases;

import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;

public class DeleteCustomer {

    private final CustomerRepository repository;

    public DeleteCustomer(CustomerRepository repository) {
        this.repository = repository;
    }

    public void deleteCustomer(Long id) {
        repository.deleteCustomer(id);
    }
}
