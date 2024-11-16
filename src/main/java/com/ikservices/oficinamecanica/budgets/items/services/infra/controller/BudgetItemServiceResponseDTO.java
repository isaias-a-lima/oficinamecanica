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
	private Long itemId;
	private BudgetDTO budgetDTO;
	private ServiceDTO serviceDTO;
	private Integer quantity;
	private BigDecimal cost;
	private BigDecimal discount;
}
