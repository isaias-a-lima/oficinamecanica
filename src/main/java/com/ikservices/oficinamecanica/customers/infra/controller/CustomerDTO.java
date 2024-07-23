package com.ikservices.oficinamecanica.customers.infra.controller;

import com.ikservices.oficinamecanica.customers.domain.Customer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CustomerDTO implements Serializable {
    private Long workshopId;
    private String docId;
    private String name;
    private String landline;
    private String mobilePhone;
    private String email;
    private String type;

    public CustomerDTO(Customer customer) {
        this.workshopId = customer.getId().getWorkshopId();
        this.docId = customer.getId().getDocId();
        this.name = customer.getName();
        this.landline = Objects.nonNull(customer.getLandline()) ? customer.getLandline().getFullPhone() : null;
        this.mobilePhone = Objects.nonNull(customer.getMobilePhone()) ? customer.getMobilePhone().getFullPhone() : null;
        this.email = Objects.nonNull(customer.getEmail()) ? customer.getEmail().getMailAddress() : null;
        this.type = customer.getType().getDescription();
    }
}
