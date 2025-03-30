package com.ikservices.oficinamecanica.workorders.infra.controller;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class WorkOrderEndingDTO {
    private Long workOrderId;
    private Long budgetId;
}
