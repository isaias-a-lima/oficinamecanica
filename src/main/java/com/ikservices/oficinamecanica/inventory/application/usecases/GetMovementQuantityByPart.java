package com.ikservices.oficinamecanica.inventory.application.usecases;

import java.time.LocalDate;

import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;

public class GetMovementQuantityByPart {
	private final MovementRepository repository;
	
	public GetMovementQuantityByPart(MovementRepository repository) {
		this.repository = repository;
	}
	
	public Integer execute(Integer partId, Long workshopId, 
			LocalDate lastFinalBalanceDate) {
		return repository.getMovementQuantityByPart(partId, workshopId, lastFinalBalanceDate);
	}
}

