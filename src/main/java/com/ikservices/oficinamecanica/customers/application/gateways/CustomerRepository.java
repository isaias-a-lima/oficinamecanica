package com.ikservices.oficinamecanica.customers.application.gateways;

import com.ikservices.oficinamecanica.customers.application.SearchCriteria;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;

import java.util.List;
import java.util.Map;

public interface CustomerRepository {
    Customer saveCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Customer getCustomer(CustomerId id);
    List<Customer> getCustomerList(Long workshopId, SearchCriteria criteria, String search);

    List<Customer> getCustomerByVehicles(Long workshopId, String plate);
}
