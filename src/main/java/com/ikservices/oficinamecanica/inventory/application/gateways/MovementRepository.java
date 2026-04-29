package com.ikservices.oficinamecanica.inventory.application.gateways;

import java.time.LocalDate;
import java.util.List;

import com.ikservices.oficinamecanica.inventory.domain.InventoryMovement;
import com.ikservices.oficinamecanica.inventory.domain.enumerates.MovementTypeEnum;

public interface MovementRepository {
	public InventoryMovement saveMovement(InventoryMovement movement);
	public InventoryMovement getMovement(Long workshopId, Long inventoryId);
	public List<InventoryMovement> ListMovementByPartAndType(Integer partId, Long workshopId, LocalDate startDate, 
			LocalDate endDate, MovementTypeEnum movementTypeEnum);
	public LocalDate getLastFinalBalanceDateByPart(Integer partId, Long workshopId);
	public Integer getLastFinalBalanceValueByPart(Integer partId, Long workshopId, 
			LocalDate basedate);
	public Integer getMovementQuantityByPart(Integer partId, Long workshopId, 
			LocalDate lastFinalBalanceDate);
	public Integer getBalanceByPart(Integer partId, Long workshopId);
	public InventoryMovement updateMovement(InventoryMovement movement);
}
