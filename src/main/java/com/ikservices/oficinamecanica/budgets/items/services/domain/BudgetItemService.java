package com.ikservices.oficinamecanica.budgets.items.services.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.services.domain.Service;

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
public class BudgetItemService {
	private BudgetItemServiceId itemId;
	private Service service;
	private Budget budget;
	private Integer quantity;
	private BigDecimal discount;
	
	public BigDecimal getTotal() {
		return NumberUtil.calcPrice(quantity, service.getCost(), discount);
	}
}
