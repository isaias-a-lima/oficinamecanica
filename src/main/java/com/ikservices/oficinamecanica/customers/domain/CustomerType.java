package com.ikservices.oficinamecanica.customers.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum CustomerType {
    NONE(null, "Nenhum"),
    PHYSICAL_PERSON('F', "Pessoa Física"),
    COMPANY_PERSON('J', "Pessoa Jurídica");

    private final Character type;

    private final String description;

    public static CustomerType getByType(Character type) {
        for (CustomerType value : CustomerType.values()) {
            if (Objects.nonNull(value.getType()) && value.getType().equals(type)) {
                return value;
            }
        }
        return CustomerType.NONE;
    }
}
