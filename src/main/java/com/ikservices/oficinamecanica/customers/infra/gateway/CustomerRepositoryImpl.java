package com.ikservices.oficinamecanica.customers.infra.gateway;

import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerRepositoryJPA;

import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerRepositoryJPA repository;
    private final CustomerConverter converter;

    public CustomerRepositoryImpl(CustomerRepositoryJPA repository,
                                  CustomerConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer getCustomer(Long id) {
        return null;
    }

    @Override
    public List<Customer> getCustomerList(Long workshopId) {
        return converter.parseCustomerList(repository.findAllByWorkshopId(workshopId));
    }

    @Override
    public void deleteCustomer(Long id) {

    }
}
