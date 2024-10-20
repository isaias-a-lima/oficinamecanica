package com.ikservices.oficinamecanica.vehicles.domain;

public enum TransmissionEnum {
    NONE,
    MANUAL,
    AUTOMATICO,
    SEM_CAMBIO;

    public static TransmissionEnum getByIndex(int index) {
        for (TransmissionEnum e : TransmissionEnum.values()) {
            if (e.ordinal() == index) {
                return  e;
            }
        }
        return TransmissionEnum.NONE;
    }
}
