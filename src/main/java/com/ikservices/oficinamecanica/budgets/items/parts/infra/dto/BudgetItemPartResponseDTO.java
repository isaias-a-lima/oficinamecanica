package com.ikservices.oficinamecanica.budgets.items.parts.infra.dto;

import com.ikservices.oficinamecanica.parts.infra.controller.PartDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"itemId", "budgetId"})
public class BudgetItemPartResponseDTO {
    private Long itemId;
    private Long budgetId;
    private PartDTO part;
    private Integer quantity;
    private String cost;
    private BigDecimal discount;
    private String amount;
}
