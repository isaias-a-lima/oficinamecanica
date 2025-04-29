package com.ikservices.oficinamecanica.workorders.payments.infra.dto;

import com.ikservices.oficinamecanica.workorders.infra.controller.WorkOrderResponseDTO;
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
    private WorkOrderResponseDTO workOrder;
    private Long budgetId;
    private String dueDate;
    private String payValue;
    private Integer paymentType;
    private String note;
    private String payDate;
}
