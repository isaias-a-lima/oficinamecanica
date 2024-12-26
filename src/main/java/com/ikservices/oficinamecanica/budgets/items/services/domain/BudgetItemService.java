package com.ikservices.oficinamecanica.budgets.items.services.domain;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
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
	BudgetItemServiceId itemId;
	Service service;
	Budget budget;
	Integer quantity;
	BigDecimal discount;
	
	public BigDecimal getTotal() {
		//quantity * (cost - ((discount / 100) * cost));
		BigDecimal percentage = discount.divide(BigDecimal.valueOf(100.0));
		BigDecimal discountValue = this.service.getCost().multiply(percentage);
		BigDecimal costValue = this.service.getCost().subtract(discountValue);
		
		return costValue.multiply(BigDecimal.valueOf(quantity.doubleValue()));
	}
}
