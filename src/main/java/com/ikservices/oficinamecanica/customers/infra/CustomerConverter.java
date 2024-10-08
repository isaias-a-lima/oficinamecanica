package com.ikservices.oficinamecanica.customers.infra;

import com.ikservices.oficinamecanica.commons.enumerates.TaxPayerEnum;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.vo.AddressVO;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;
import com.ikservices.oficinamecanica.customers.infra.controller.CustomerDTO;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        customer.setId(new CustomerId(customerEntity.getId().getWorkshopId(),
                new IdentificationDocumentVO(TaxPayerEnum.getByType(customerEntity.getType()) ,customerEntity.getId().getDocId())));
        customer.setName(customerEntity.getName());
        customer.setLandline(PhoneVO.parsePhoneVO(customerEntity.getLandline()));
        customer.setMobilePhone(PhoneVO.parsePhoneVO(customerEntity.getMobilePhone()));
        customer.setEmail(new EmailVO(customerEntity.getEmail()));
        customer.setType(TaxPayerEnum.getByType(customerEntity.getType()));
        customer.setAddress(AddressVO.getFactory().setPostalCode(customerEntity.getPostalCode())
                .setStreetAndNumberAndComplement(customerEntity.getAddress(), null, null)
                .setNeighborhood(null)
                .setCityAndStateAndCountry(customerEntity.getCity(), customerEntity.getState(), null)
                .build());

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
        entity.setId(new CustomerEntityId(customer.getId().getWorkshopId(), customer.getId().getDocId().getDocument()));
        entity.setName(customer.getName());
        entity.setEmail(Objects.nonNull(customer.getEmail()) ? customer.getEmail().getMailAddress() : null);
        entity.setLandline(Objects.nonNull(customer.getLandline()) ? customer.getLandline().getFullPhone() : null);
        entity.setMobilePhone(Objects.nonNull(customer.getMobilePhone()) ? customer.getMobilePhone().getFullPhone(): null);
        entity.setType(Objects.nonNull(customer.getType()) ? customer.getType().getType() : null);
        entity.setAddress(Objects.nonNull(customer.getAddress()) ? customer.getAddress().getPartialAddress() : null);
        entity.setCity(Objects.nonNull(customer.getAddress()) ? customer.getAddress().getCity() : null);
        entity.setState(Objects.nonNull(customer.getAddress()) ? customer.getAddress().getState() : null);
        entity.setPostalCode(Objects.nonNull(customer.getAddress()) ? customer.getAddress().getPostalCode(): null);

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
        customer.setId(new CustomerId(customerDTO.getWorkshopId(),
                new IdentificationDocumentVO(TaxPayerEnum.getByDescription(customerDTO.getType()) ,customerDTO.getDocId())));
        customer.setName(customerDTO.getName());
        customer.setLandline(Objects.nonNull(customerDTO.getLandline()) ? PhoneVO.parsePhoneVO(customerDTO.getLandline()) : null);
        customer.setMobilePhone(Objects.nonNull(customerDTO.getMobilePhone()) ? PhoneVO.parsePhoneVO(customerDTO.getMobilePhone()) : null);
        customer.setEmail(Objects.nonNull(customerDTO.getEmail()) ? new EmailVO(customerDTO.getEmail()) : null);
        customer.setType(TaxPayerEnum.getByDescription(customerDTO.getType()));
        customer.setAddress(AddressVO.getFactory().setPostalCode(customerDTO.getPostalCode())
                .setStreetAndNumberAndComplement(customerDTO.getAddress(), null, null)
                .setNeighborhood(null)
                .setCityAndStateAndCountry(customerDTO.getCity(), customerDTO.getState(), null)
                .build());

        return customer;
    }
}
