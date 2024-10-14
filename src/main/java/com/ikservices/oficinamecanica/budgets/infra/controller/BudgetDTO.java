package com.ikservices.oficinamecanica.budgets.infra.controller;

import java.math.BigDecimal;
import java.util.Objects;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.vehicles.infra.controller.VehicleDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BudgetDTO {
	VehicleDTO vehicle;
	String openingDate;
	Long km;
	Character budgetStatus;
	String amount;
	Long budgetId;

	public BudgetDTO(Budget budget, Long budgetId, Long vehicleId) {
		this.openingDate = budget.getOpeningDate().toString();
		this.km = budget.getKm();
		this.budgetStatus = budget.getBudgetStatus();
		this.amount = NumberUtil.parseStringMoney(budget.getAmount());
		this.budgetId = budgetId;
		this.vehicle = Objects.nonNull(budget.getVehicle()) && Objects.nonNull(vehicleId) ? new VehicleDTO(budget.getVehicle(), vehicleId) : null;
	}
}
