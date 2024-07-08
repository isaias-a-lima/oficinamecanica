package com.ikservices.oficinamecanica.customers.infra;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;
import com.ikservices.oficinamecanica.customers.domain.CustomerType;
import com.ikservices.oficinamecanica.customers.infra.controller.CustomerResponse;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopConverter;

import java.util.*;

public class CustomerConverter {

    private final WorkshopConverter workshopConverter;

    public CustomerConverter(WorkshopConverter workshopConverter) {
        this.workshopConverter = workshopConverter;
    }

    public Customer parseCustomer(CustomerEntity customerEntity) {
        if (Objects.isNull(customerEntity)) {
            throw new IKException("Null object.");
        }

        Customer customer = new Customer();
        customer.setWorkshop(workshopConverter.parseWorkshop(customerEntity.getWorkshopEntity()));
        customer.setId(new CustomerId(customerEntity.getId().getWorkshopId(), customerEntity.getId().getDocId()));
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

    public List<CustomerResponse> customerResponseList(List<Customer> customerMap) {
        List<CustomerResponse> responseList = new ArrayList<>();
        if (Objects.nonNull(customerMap) && !customerMap.isEmpty()) {
            for (Customer customer : customerMap) {
                responseList.add(new CustomerResponse(customer));
            }
        }
        return responseList;
    }
}
