package com.ikservices.oficinamecanica.payables.application.enumerates;

public enum PayableStateEnum {
    NONE,
    PAID,
    UNPAID;

    public static PayableStateEnum findByIndex(int index) {
        for (PayableStateEnum value : PayableStateEnum.values()) {
            if(value.ordinal() == index) {
                return value;
            }
        }
        return PayableStateEnum.NONE;
    }
}
