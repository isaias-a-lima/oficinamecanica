package com.ikservices.oficinamecanica.commons.enumerates;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum TaxPayerEnum {
    NONE(null, "Nenhum"),
    PHYSICAL_PERSON('F', "Pessoa Física"),
    COMPANY_PERSON('J', "Pessoa Jurídica");

    private final Character type;

    private final String description;

    public static TaxPayerEnum getByType(Character type) {
        for (TaxPayerEnum value : TaxPayerEnum.values()) {
            if (Objects.nonNull(type) && Objects.nonNull(value.getType()) && value.getType().equals(type)) {
                return value;
            }
        }
        return TaxPayerEnum.NONE;
    }

    public static TaxPayerEnum getByDescription(String description) {
        for (TaxPayerEnum value : TaxPayerEnum.values()) {
            if (Objects.nonNull(description) && Objects.nonNull(value.getType()) && value.getDescription().equals(description)) {
                return value;
            }
        }
        return TaxPayerEnum.NONE;
    }
}
