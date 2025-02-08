package com.ikservices.oficinamecanica.workorders.items.parts.infra.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"itemId", "workOrderId", "budgetId"})
public class WorkOrderPartItemRequestDTO {
    private Long itemId;
    private Long workOrderId;
    private Long budgetId;
    private Integer partId;
    private Integer quantity;
    private BigDecimal itemValue;
    private BigDecimal discount;
}
