package com.ikservices.oficinamecanica.workorders.payments.infra.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = {"number", "workOrderId", "budgetId"})
public class PaymentDTO {
    private Integer number;
    private Long workOrderId;
    private Long budgetId;
    private LocalDate dueDate;
    private String payValue;
    private Integer paymentType;
    private String note;
    private LocalDate payDate;
}
