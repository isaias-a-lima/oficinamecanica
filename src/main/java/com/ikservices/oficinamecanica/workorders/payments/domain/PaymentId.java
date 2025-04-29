package com.ikservices.oficinamecanica.workorders.payments.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class PaymentId {
    private Integer number;
    private Long workOrderId;
    private Long budgetId;
}
