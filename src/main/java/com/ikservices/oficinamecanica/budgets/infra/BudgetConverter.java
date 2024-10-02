package com.ikservices.oficinamecanica.budgets.infra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.infra.controller.BudgetDTO;
import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.vehicles.infra.VehicleConverter;

public class BudgetConverter {
	private final VehicleConverter vehicleConverter;
	
	public BudgetConverter(VehicleConverter vehicleConverter) {
		this.vehicleConverter = vehicleConverter;
	}
	
	public Budget parseBudget(BudgetEntity entity) {
		if(Objects.isNull(entity)) {
			throw new IKException("Null object.");
		}
		
		Budget budget = new Budget();
		budget.setAmount(entity.getAmount());
		budget.setBudgetStatus(entity.getBudgetStatus());
		budget.setKm(entity.getKm());
		budget.setOpeningDate(entity.getOpeningDate());
		budget.setVehicle(vehicleConverter.parseVehicle(entity.getVehicleEntity()));
		
		return budget;
	}
	
	public BudgetEntity parseEntity(Budget budget) {
		if(Objects.isNull(budget)) {
			throw new IKException("Null object.");
		}
		
		BudgetEntity entity = new BudgetEntity();
		entity.setAmount(budget.getAmount());
		entity.setBudgetStatus(budget.getBudgetStatus());
		entity.setKm(budget.getKm());
		entity.setOpeningDate(budget.getOpeningDate());
		entity.setVehicleEntity(vehicleConverter.parseEntity(budget.getVehicle()));
		
		return entity;
	}
	
	public List<BudgetEntity> parseBudgetEntityList(List<Budget> budgetList) {
		List<BudgetEntity> budgetEntityList = new ArrayList<>();
		
		if(Objects.nonNull(budgetList) && !budgetEntityList.isEmpty()) {
			for(Budget budget : budgetList) {
				budgetEntityList.add(this.parseEntity(budget));
			}
		}
		
		return budgetEntityList;
	}
	
	public List<Map<Long, Budget>> parseBudgetList(List<BudgetEntity> budgetEntityList) {
		List<Map<Long, Budget>> budgetList = new ArrayList<>();
		
		if(Objects.nonNull(budgetEntityList) && !budgetEntityList.isEmpty()) {
			for(BudgetEntity entity : budgetEntityList) {
				Map<Long, Budget> budgetMap = new HashMap<>();
				budgetMap.put(entity.getBudgetId(), this.parseBudget(entity));
				budgetList.add(budgetMap);
			}
		}
		
		return budgetList;
	}
	
	public BudgetDTO parseDTO(Budget budget) {
		if(Objects.isNull(budget)) {
			throw new IKException("Null object");
		}
		
		BudgetDTO dto = new BudgetDTO();
		dto.setAmount(NumberUtil.parseStringMoney(budget.getAmount()));
		dto.setBudgetStatus(budget.getBudgetStatus());
		dto.setKm(budget.getKm());
		dto.setOpeningDate(budget.getOpeningDate().toString());
		dto.setVehicle(vehicleConverter.parseDTO(budget.getVehicle()));
		
		return dto;
	}
	
	public Budget parseBudget(BudgetDTO dto) {
		if(Objects.isNull(dto)) {
			throw new IKException("Null object");
		}
		
		Budget budget = new Budget();
		budget.setAmount(NumberUtil.parseBigDecimal(dto.getAmount()));
		budget.setBudgetStatus(dto.getBudgetStatus());
		budget.setKm(dto.getKm());
		budget.setOpeningDate(LocalDate.parse(dto.getOpeningDate()));
		
		return budget;
	}
}
