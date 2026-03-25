package com.ikservices.oficinamecanica.receivables.domain;

import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentTypeEnum;

import java.util.Objects;

public enum ReceivableTypeEnum {
    NONE,
    DEBIT,
    CREDIT,
    CASH,
    PIX,
    INVOICE,
    CHECK,
    EXCHANGE;

    public static ReceivableTypeEnum findByIndex(Integer index) {
        if (Objects.nonNull(index)) {
            for(ReceivableTypeEnum value : ReceivableTypeEnum.values()) {
                if(value.ordinal() == index) {
                    return value;
                }
            }
        }

        return ReceivableTypeEnum.NONE;
    }
}
