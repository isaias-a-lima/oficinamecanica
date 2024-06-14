package com.ikservices.oficinamecanica.customers.infra;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerType;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerConverter {

    public Customer parseCustomer(CustomerEntity customerEntity) {
        if (Objects.isNull(customerEntity)) {
            throw new IKException("Null object.");
        }

        Customer customer = new Customer();
        customer.setIdDoc(customerEntity.getDocId());
        customer.setName(customerEntity.getName());
        customer.setLandline(PhoneVO.parsePhoneVO(customerEntity.getLandline()));
        customer.setMobilePhone(PhoneVO.parsePhoneVO(customerEntity.getMobilePhone()));
        customer.setEmail(new EmailVO(customerEntity.getEmail()));
        customer.setType(CustomerType.getByType(customerEntity.getType()));

        return customer;
    }

    public List<Customer> parseCustomerList(List<CustomerEntity> customerEntityList) {
        List<Customer> customerList = new ArrayList<>();
        if (Objects.nonNull(customerEntityList) && !customerEntityList.isEmpty()) {
            for (CustomerEntity customerEntity : customerEntityList) {
                customerList.add(this.parseCustomer(customerEntity));
            }
        }
        return customerList;
    }
}
