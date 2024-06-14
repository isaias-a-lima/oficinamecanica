package com.ikservices.oficinamecanica.customers.application.usecases;

import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;

import java.util.List;

public class ListCustomers {

    private final CustomerRepository repository;

    public ListCustomers(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> execute(Long workshopId) {
        return repository.getCustomerList(workshopId);
    }
}
