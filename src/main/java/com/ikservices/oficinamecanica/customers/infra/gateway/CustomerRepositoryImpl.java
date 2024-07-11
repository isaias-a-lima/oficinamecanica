package com.ikservices.oficinamecanica.customers.infra.gateway;

import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
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
        CustomerEntity entitySaved = repository.save(converter.parseEntity(customer));
        return converter.parseCustomer(entitySaved);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer getCustomer(CustomerId id) {
        return converter.parseCustomer(repository.getById(new CustomerEntityId(id.getWorkshopId(), id.getDocId())));
    }

    @Override
    public List<Customer> getCustomerList(Long workshopId) {
        return converter.parseCustomerList(repository.findAllByWorkshopId(workshopId));
    }
}
