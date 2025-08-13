package com.ikservices.oficinamecanica.inventory.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.inventory.domain.InventoryMovement;
import com.ikservices.oficinamecanica.inventory.domain.enumerates.MovementTypeEnum;

public interface MovementRepository {
	public InventoryMovement saveMovement(InventoryMovement movement);
	public InventoryMovement getMovement(Long workshopId, Long inventoryId);
	public List<InventoryMovement> ListMovementByPartAndType(Integer partId, MovementTypeEnum movementTypeEnum);
	public Integer getBalanceByPart(Integer partId);
	public InventoryMovement updateMovement(InventoryMovement movement);
}
