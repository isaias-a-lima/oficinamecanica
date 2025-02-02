package com.ikservices.oficinamecanica.budgets.infra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.infra.controller.BudgetDTO;
import com.ikservices.oficinamecanica.budgets.infra.controller.BudgetRequest;
import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.budgets.items.parts.infra.BudgetItemPartConverter;
import com.ikservices.oficinamecanica.budgets.items.services.infra.BudgetItemServiceConverter;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.VehicleConverter;



@Component
public class BudgetConverter {
	private final VehicleConverter vehicleConverter;

	@Autowired
	@Lazy
	private BudgetItemServiceConverter budgetItemServiceConverter;
	@Autowired
	@Lazy
	private BudgetItemPartConverter budgetItemPartConverter;
	
	public BudgetConverter(VehicleConverter vehicleConverter) {
		this.vehicleConverter = vehicleConverter;
	}
	
	public Budget parseBudget(BudgetEntity entity, Boolean avoidStackOverflow) {
		if(Objects.isNull(entity)) {
			throw new IKException("Null object.");
		}
		
		Budget budget = new Budget();
		budget.setAmount(entity.getAmount());
		budget.setBudgetStatus(entity.getBudgetStatus());
		budget.setKm(entity.getKm());
		budget.setOpeningDate(entity.getOpeningDate());

		budget.setVehicle(Objects.nonNull(entity.getVehicle()) ? vehicleConverter.parseVehicle(entity.getVehicle()) : null);

		budget.setServiceItems(
				!avoidStackOverflow && Objects.nonNull(entity.getServiceItems()) ?
						budgetItemServiceConverter.parseDomainList(entity.getServiceItems()) :
						new ArrayList<>()
		);

		budget.setPartItems(
				!avoidStackOverflow && Objects.nonNull(entity.getPartItems()) ?
						budgetItemPartConverter.parseEntityToDomainList(entity.getPartItems()) :
						new ArrayList<>()
		);
		
		return budget;
	}
	
	public BudgetEntity parseEntity(Budget budget, Long vehicleId, Long budgetId) {
		if(Objects.isNull(budget)) {
			throw new IKException("Null object.");
		}
		
		BudgetEntity entity = new BudgetEntity();
		entity.setBudgetId(budgetId);
		entity.setVehicleId(vehicleId);
		
		entity.setOpeningDate(budget.getOpeningDate());

		entity.setVehicle(Objects.nonNull(budget.getVehicle()) ? vehicleConverter.parseEntity(budget.getVehicle(), null) : null);

		entity.setKm(budget.getKm());
		entity.setBudgetStatus(budget.getBudgetStatus());
		entity.setAmount(budget.getAmount());
		entity.setServiceItems(
				Objects.nonNull(budget.getServiceItems()) ?
						budgetItemServiceConverter.parseEntityList(budget.getServiceItems()) :
						new ArrayList<>()
		);

		entity.setPartItems(
				Objects.nonNull(budget.getPartItems()) ?
						budgetItemPartConverter.parseDomainToEntityList(budget.getPartItems()) :
						new ArrayList<>()
		);
		
		return entity;
	}
	
	public BudgetDTO parseBudgetDTO(Map<Long, Budget> budgetMap, Long vehicleId, Boolean avoidStackOverflow) {
		if(Objects.isNull(budgetMap)) {
			throw new IKException("Null object");
		}
		
		BudgetDTO dto = new BudgetDTO();

		Set<Entry<Long, Budget>> entries = budgetMap.entrySet();
		
		for(Map.Entry<Long, Budget> entry : entries) {
			dto.setAmount(NumberUtil.parseStringMoney(entry.getValue().getAmount()));
			dto.setBudgetStatus(entry.getValue().getBudgetStatus().ordinal());
			dto.setKm(entry.getValue().getKm());
			dto.setOpeningDate(entry.getValue().getOpeningDate().toString());
			dto.setBudgetId(entry.getKey());

			if (!avoidStackOverflow && Objects.nonNull(entry.getValue().getServiceItems()) && !entry.getValue().getServiceItems().isEmpty()) {
				dto.setServiceItems(budgetItemServiceConverter.parseItemListToResponseList(entry.getValue().getServiceItems()));
			}

			if (!avoidStackOverflow && Objects.nonNull(entry.getValue().getPartItems()) && !entry.getValue().getPartItems().isEmpty()) {
				dto.setPartItems(budgetItemPartConverter.parseDomainToResponse(entry.getValue().getPartItems()));
			}

			if (Objects.nonNull(entry.getValue().getVehicle())) {
				Map<Long, Vehicle> vehicleMap = new HashMap<>();
				vehicleMap.put(vehicleId, entry.getValue().getVehicle());
				dto.setVehicle(vehicleConverter.parseDTO(vehicleMap));
			}
			break;
		}
		
		return dto;
	}
	
	public List<BudgetDTO> parseBudgetDTOList(List<Map<Long, Map<Long, Budget>>> budgetList) {
		List<BudgetDTO> dtoList = new ArrayList<>();

		for (Map<Long, Map<Long, Budget>> outerMap : budgetList) {
			for (Long vehicleId : outerMap.keySet()) {
				Map<Long, Budget> innerMap = outerMap.get(vehicleId);
				dtoList.add(parseBudgetDTO(innerMap, vehicleId, false));
			}
		}

		return dtoList;
	}

	public List<Map<Long, Map<Long, Budget>>> parseBudgetList(List<BudgetEntity> entities) {

		List<Map<Long, Map<Long, Budget>>> budgetList = new ArrayList<>();

		for (BudgetEntity entity : entities) {
			Map<Long, Budget> mapInner = new HashMap<>();
			mapInner.put(entity.getBudgetId(), this.parseBudget(entity, false));

			Map<Long, Map<Long, Budget>> mapOuter = new HashMap<>();
			mapOuter.put(entity.getVehicleId(), mapInner);

			budgetList.add(mapOuter);
		}

		return budgetList;
	}

	public Budget parseBudget(BudgetRequest request) {
		Budget budget = new Budget();
		budget.setOpeningDate(LocalDate.parse(request.getOpeningDate()));
		budget.setKm(request.getKm());
		budget.setBudgetStatus(request.getBudgetStatus());
		budget.setAmount(NumberUtil.parseBigDecimal(request.getAmount()));
		budget.setServiceItems(Objects.nonNull(request.getServiceItems()) ? budgetItemServiceConverter.parseRequestToDomainList(request.getServiceItems()) : null);
		budget.setPartItems(Objects.nonNull(request.getPartItems()) ? budgetItemPartConverter.parseRequestToDomainList(request.getPartItems()) : null);
		return budget;
	}
}
