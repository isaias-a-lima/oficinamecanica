package com.ikservices.oficinamecanica.inventory.application.usecases;

import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;
import com.ikservices.oficinamecanica.inventory.domain.InventoryMovement;

public class SaveMovement {
	private final MovementRepository repository;
	
	public SaveMovement(MovementRepository repository) {
		this.repository = repository;
	}
	
	public InventoryMovement execute(InventoryMovement movement) {
		return this.repository.saveMovement(movement);
	}
}
