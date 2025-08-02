package com.ikservices.oficinamecanica.workorders.payments.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentsDTO {
    private List<PaymentDTO> payments;
    private Long workOrderId;
    private Long budgetId;
}
