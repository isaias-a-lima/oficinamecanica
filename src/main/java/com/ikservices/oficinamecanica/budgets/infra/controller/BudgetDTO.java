package com.ikservices.oficinamecanica.budgets.infra.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import com.ikservices.oficinamecanica.budgets.items.parts.infra.dto.BudgetItemPartResponseDTO;
import com.ikservices.oficinamecanica.budgets.items.services.infra.controller.BudgetItemServiceResponseDTO;
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
	Long budgetId;
	VehicleDTO vehicle;
	String openingDate;
	Long km;
	Integer budgetStatus;
	String amount;
	List<BudgetItemServiceResponseDTO> serviceItems = new ArrayList<>();
	List<BudgetItemPartResponseDTO> partItems = new ArrayList<>();


	public BudgetDTO(Budget budget, Long budgetId, Long vehicleId) {
		this.openingDate = budget.getOpeningDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		this.km = budget.getKm();
		this.budgetStatus = budget.getBudgetStatus().ordinal();
		this.amount = NumberUtil.parseStringMoney(budget.getAmount());
		this.budgetId = budgetId;
		this.vehicle = Objects.nonNull(budget.getVehicle()) && Objects.nonNull(vehicleId) ? new VehicleDTO(budget.getVehicle(), vehicleId) : null;
	}
}
