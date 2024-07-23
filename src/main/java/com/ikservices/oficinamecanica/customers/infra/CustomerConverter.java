package com.ikservices.oficinamecanica.customers.infra;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;
import com.ikservices.oficinamecanica.customers.domain.CustomerType;
import com.ikservices.oficinamecanica.customers.infra.controller.CustomerDTO;
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
        customer.setWorkshop(Objects.nonNull(customerEntity.getWorkshopEntity()) ? workshopConverter.parseWorkshop(customerEntity.getWorkshopEntity()) : null);
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

    public CustomerEntity parseEntity(Customer customer) {
        if (Objects.isNull(customer)) {
            throw new IKException("Null object.");
        }

        CustomerEntity entity = new CustomerEntity();
        entity.setId(new CustomerEntityId(customer.getId().getWorkshopId(), customer.getId().getDocId()));
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail().getMailAddress());
        entity.setLandline(customer.getLandline().getFullPhone());
        entity.setMobilePhone(customer.getMobilePhone().getFullPhone());
        entity.setType(customer.getType().getType());

        return entity;
    }

    public List<CustomerDTO> parseCustomerResponseList(List<Customer> customerMap) {
        List<CustomerDTO> responseList = new ArrayList<>();
        if (Objects.nonNull(customerMap) && !customerMap.isEmpty()) {
            for (Customer customer : customerMap) {
                responseList.add(new CustomerDTO(customer));
            }
        }
        return responseList;
    }

    public Customer parseCustomer(CustomerDTO customerDTO) {
        if (Objects.isNull(customerDTO)) {
            throw new IKException("Null object.");
        }

        Customer customer = new Customer();
        customer.setId(new CustomerId(customerDTO.getWorkshopId(), customerDTO.getDocId()));
        customer.setName(customerDTO.getName());
        customer.setLandline(Objects.nonNull(customerDTO.getLandline()) ? PhoneVO.parsePhoneVO(customerDTO.getLandline()) : null);
        customer.setMobilePhone(Objects.nonNull(customerDTO.getMobilePhone()) ? PhoneVO.parsePhoneVO(customerDTO.getMobilePhone()) : null);
        customer.setEmail(Objects.nonNull(customerDTO.getEmail()) ? new EmailVO(customerDTO.getEmail()) : null);
        customer.setType(CustomerType.getByDescription(customerDTO.getType()));

        return customer;
    }
}
