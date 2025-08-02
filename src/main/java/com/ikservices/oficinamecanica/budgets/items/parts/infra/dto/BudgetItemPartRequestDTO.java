package com.ikservices.oficinamecanica.budgets.items.parts.infra.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"itemId", "budgetId"})
public class BudgetItemPartRequestDTO {
    private Long itemId;
    private Long budgetId;
    private Long partId;
    private Long workshopId;
    private Integer quantity;
    private BigDecimal cost;
    private BigDecimal discount;
    private BigDecimal serviceCost;
}
