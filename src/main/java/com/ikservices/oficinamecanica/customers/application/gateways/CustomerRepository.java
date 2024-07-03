package com.ikservices.oficinamecanica.customers.application.gateways;

import com.ikservices.oficinamecanica.customers.domain.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerRepository {
    Customer saveCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Customer getCustomer(Long id);
    Map<Long, Customer> getCustomerList(Long workshopId);
    void deleteCustomer(Long id);
}
