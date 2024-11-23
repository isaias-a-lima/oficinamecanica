package com.ikservices.oficinamecanica.vehicles.domain;

public enum FuelEnum {
    NONE,
    GASOLINA,
    ALCOOL,
    FLEX,
    DIESEL,
    HIBRIDO,
    ELETRICO,
    HIDROGENIO;

    public static FuelEnum getByIndex(int index) {
        for (FuelEnum e : FuelEnum.values()) {
            if (e.ordinal() == index) {
                return  e;
            }
        }
        return FuelEnum.NONE;
    }
}
