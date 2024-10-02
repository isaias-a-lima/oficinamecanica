package com.ikservices.oficinamecanica.budgets.infra.controller;

import java.math.BigDecimal;

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
}
