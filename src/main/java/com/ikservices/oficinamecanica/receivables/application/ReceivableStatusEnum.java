package com.ikservices.oficinamecanica.receivables.application;

import com.ikservices.oficinamecanica.workorders.payments.application.enumerates.PaymentStateEnum;

public enum ReceivableStatusEnum {
    NONE,
    PAID,
    UNPAID;

    public static ReceivableStatusEnum findByIndex(int index) {
        for (ReceivableStatusEnum value : ReceivableStatusEnum.values()) {
            if(value.ordinal() == index) {
                return value;
            }
        }
        return ReceivableStatusEnum.NONE;
    }
}
