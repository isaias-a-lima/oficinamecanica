package com.ikservices.oficinamecanica.vehicles.domain;

public enum TransmissionEnum {
    NONE,
    MANUAL,
    AUTOMATIC,
    OTHERS;

    public static TransmissionEnum getByIndex(int index) {
        for (TransmissionEnum e : TransmissionEnum.values()) {
            if (e.ordinal() == index) {
                return  e;
            }
        }
        return TransmissionEnum.NONE;
    }
}
