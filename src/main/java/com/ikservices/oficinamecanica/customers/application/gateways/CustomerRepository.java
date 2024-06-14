package com.ikservices.oficinamecanica.customers.application.gateways;

import com.ikservices.oficinamecanica.customers.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer saveCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Customer getCustomer(Long id);
    List<Customer> getCustomerList(Long workshopId);
    void deleteCustomer(Long id);
}
