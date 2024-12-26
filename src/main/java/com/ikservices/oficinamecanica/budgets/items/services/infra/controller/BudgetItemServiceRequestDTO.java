package com.ikservices.oficinamecanica.budgets.items.services.infra.controller;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BudgetItemServiceRequestDTO {
	private Long itemId;
	private Long budgetId;
	private Long serviceId;
	private Long workshopId;
	private Integer quantity;
	private BigDecimal cost;
	private BigDecimal discount;
}
