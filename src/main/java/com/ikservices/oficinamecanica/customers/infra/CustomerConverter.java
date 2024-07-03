package com.ikservices.oficinamecanica.customers.infra;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerType;
import com.ikservices.oficinamecanica.customers.infra.controller.CustomerResponse;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;

import java.util.*;

public class CustomerConverter {

    public Customer parseCustomer(CustomerEntity customerEntity) {
        if (Objects.isNull(customerEntity)) {
            throw new IKException("Null object.");
        }

        Customer customer = new Customer();
        customer.setWorkshopId(customerEntity.getWorkshopId());
        customer.setIdDoc(customerEntity.getDocId());
        customer.setName(customerEntity.getName());
        customer.setLandline(PhoneVO.parsePhoneVO(customerEntity.getLandline()));
        customer.setMobilePhone(PhoneVO.parsePhoneVO(customerEntity.getMobilePhone()));
        customer.setEmail(new EmailVO(customerEntity.getEmail()));
        customer.setType(CustomerType.getByType(customerEntity.getType()));

        return customer;
    }

    public Map<Long, Customer> parseCustomerList(List<CustomerEntity> customerEntityList) {
        Map<Long, Customer> customerList = new HashMap<>();
        if (Objects.nonNull(customerEntityList) && !customerEntityList.isEmpty()) {
            for (CustomerEntity customerEntity : customerEntityList) {
                customerList.put(customerEntity.getId(), this.parseCustomer(customerEntity));
            }
        }
        return customerList;
    }

    public List<CustomerResponse> customerResponseList(Map<Long, Customer> customerMap) {
        List<CustomerResponse> responseList = new ArrayList<>();
        if (Objects.nonNull(customerMap) && !customerMap.isEmpty()) {
            for (Long id : customerMap.keySet()) {
                responseList.add(new CustomerResponse(id, customerMap.get(id)));
            }
        }
        return responseList;
    }
}
