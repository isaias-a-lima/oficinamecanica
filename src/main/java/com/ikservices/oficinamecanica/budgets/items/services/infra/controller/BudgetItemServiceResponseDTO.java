package com.ikservices.oficinamecanica.budgets.items.services.infra.controller;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.budgets.infra.controller.BudgetDTO;
import com.ikservices.oficinamecanica.services.infra.controller.ServiceDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BudgetItemServiceResponseDTO {
	//TODO verify wy there are no budgetId here to identify the compound key
	private Long itemId;
	private Long budgetId;
	private ServiceDTO service;
	private Integer quantity;
	private BigDecimal cost;
	private BigDecimal discount;
	private BigDecimal totalAmount;
}
