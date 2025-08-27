package com.ikservices.oficinamecanica.inventory.application.usecases;

import java.time.LocalDate;
import java.util.List;

import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;
import com.ikservices.oficinamecanica.inventory.domain.InventoryMovement;
import com.ikservices.oficinamecanica.inventory.domain.enumerates.MovementTypeEnum;

public class ListMovementByPartAndType {
	private final MovementRepository repository;
	
	public ListMovementByPartAndType(MovementRepository repository) {
		this.repository = repository;
	}
	
	public List<InventoryMovement> execute(Integer partId, Long workshopId, 
			LocalDate startDate, LocalDate endDate, MovementTypeEnum movementTypeEnum) {
		return repository.ListMovementByPartAndType(partId, workshopId, startDate, endDate, movementTypeEnum);
	}
}
