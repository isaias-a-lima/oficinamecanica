package com.ikservices.oficinamecanica.budgets.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;

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
public class Budget {
	Vehicle vehicle;
	LocalDate openingDate;
	Long km;
	BudgetStatusEnum budgetStatus;
	BigDecimal amount;
	List<BudgetItemService> serviceItems;
}
