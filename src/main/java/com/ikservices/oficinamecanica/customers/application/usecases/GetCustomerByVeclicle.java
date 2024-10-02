package com.ikservices.oficinamecanica.customers.application.usecases;

import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;

import java.util.List;

public class GetCustomerByVeclicle {

    private CustomerRepository repository;

    public GetCustomerByVeclicle(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> execute(Long workshopId, String plate) {
        return repository.getCustomerByVehicles(workshopId, plate);
    }
}
