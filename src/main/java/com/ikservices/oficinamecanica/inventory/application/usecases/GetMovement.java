package com.ikservices.oficinamecanica.inventory.application.usecases;

import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;
import com.ikservices.oficinamecanica.inventory.domain.InventoryMovement;

public class GetMovement {
	private final MovementRepository repository;
	
	public GetMovement(MovementRepository repository) {
		this.repository = repository;
	}
	
	public InventoryMovement execute(Long workshopId, Long inventoryId) {
		return repository.getMovement(workshopId, inventoryId);
	}
}
