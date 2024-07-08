package com.ikservices.oficinamecanica.customers.infra.controller;

import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@EqualsAndHashCode
public class CustomerResponse {
    private final Long workshopId;
    private final String docId;
    private final String name;
    private final String landline;
    private final String mobilePhone;
    private final String email;
    private final String type;

    public CustomerResponse(Customer customer) {
        this.workshopId = customer.getId().getWorkshopId();
        this.docId = customer.getId().getDocId();
        this.name = customer.getName();
        this.landline = Objects.nonNull(customer.getLandline()) ? customer.getLandline().getFullPhone() : null;
        this.mobilePhone = Objects.nonNull(customer.getMobilePhone()) ? customer.getMobilePhone().getFullPhone() : null;
        this.email = Objects.nonNull(customer.getEmail()) ? customer.getEmail().getMailAddress() : null;
        this.type = customer.getType().getDescription();
    }
}
