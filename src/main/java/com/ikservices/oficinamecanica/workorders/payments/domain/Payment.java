package com.ikservices.oficinamecanica.workorders.payments.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Payment {
    private PaymentId id;
    private LocalDate dueDate;
    private BigDecimal paymentValue;
    private PaymentTypeEnum paymentType;
    private String note;
    private LocalDate payDate;
}
