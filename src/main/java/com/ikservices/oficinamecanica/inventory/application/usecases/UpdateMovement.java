package com.ikservices.oficinamecanica.inventory.application.usecases;

import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;
import com.ikservices.oficinamecanica.inventory.domain.InventoryMovement;

public class UpdateMovement {
	private final MovementRepository repository;
	
	public UpdateMovement(MovementRepository repository) {
		this.repository = repository;
	}
	
	public InventoryMovement execute(InventoryMovement movement) {
		return repository.updateMovement(movement);
	}
}
