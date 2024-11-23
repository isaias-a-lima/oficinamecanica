package com.ikservices.oficinamecanica.customers.infra.gateway;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.customers.application.CustomerBusinessConstants;
import com.ikservices.oficinamecanica.customers.application.SearchCriteria;
import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import com.ikservices.oficinamecanica.customers.infra.constants.CustomerConstants;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerRepositoryJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final CustomerRepositoryJPA repository;
    @Autowired
    @Lazy
    private CustomerConverter converter;
    @Autowired
    private Environment environment;

    public CustomerRepositoryImpl(CustomerRepositoryJPA repository) {
        this.repository = repository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Optional<CustomerEntity> optional = repository.findById(new CustomerEntityId(customer.getId().getWorkshopId(), customer.getId().getDocId().getDocument()));
        if (optional.isPresent()) {
            throw new IKException(new IKMessage(CustomerBusinessConstants.ERROR_CODE, IKMessageType.WARNING.getCode(), environment.getProperty(CustomerConstants.CUSTOMER_ALREADY_SAVED_MESSAGE)));
        }
        CustomerEntity entitySaved = repository.save(converter.parseEntity(customer));
        return converter.parseCustomer(entitySaved);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Optional<CustomerEntity> optional = repository.findById(new CustomerEntityId(customer.getId().getWorkshopId(), customer.getId().getDocId().getDocument()));
        CustomerEntity entity = optional.orElse(null);
        if (Objects.nonNull(entity)) {
            entity.update(converter.parseEntity(customer));
        }
        return converter.parseCustomer(entity);
    }

    @Override
    public Customer getCustomer(CustomerId id) {
        return converter.parseCustomer(repository.getById(new CustomerEntityId(id.getWorkshopId(), id.getDocId().getDocument())));
    }

    @Override
    public List<Customer> getCustomerList(Long workshopId, SearchCriteria criteria, String search) {
        return converter.parseCustomerList(repository.findAllByWorkshopId(workshopId, criteria.ordinal(), search));
    }

    @Override
    public List<Customer> getCustomerByVehicles(Long workshopId, String plate) {
        return converter.parseCustomerList(repository.findByVehicles(workshopId, plate));
    }
}
