package com.ikservices.oficinamecanica.workorders.payments.domain;

import java.util.Objects;

public enum PaymentTypeEnum {
    NONE,
    DEBIT,
    CREDIT,
    CASH,
    PIX,
    INVOICE,
    CHECK,
    EXCHANGE;

    public static PaymentTypeEnum findByIndex(Integer index) {
        if (Objects.nonNull(index)) {
            for(PaymentTypeEnum value : PaymentTypeEnum.values()) {
                if(value.ordinal() == index) {
                    return value;
                }
            }
        }

        return PaymentTypeEnum.NONE;
    }
}
