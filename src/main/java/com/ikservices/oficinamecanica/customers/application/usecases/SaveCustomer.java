package com.ikservices.oficinamecanica.customers.application.usecases;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;

public class SaveCustomer {

    private final CustomerRepository repository;

    public SaveCustomer(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer execute(Customer customer) throws IKException {
        return repository.saveCustomer(customer);
    }
}
