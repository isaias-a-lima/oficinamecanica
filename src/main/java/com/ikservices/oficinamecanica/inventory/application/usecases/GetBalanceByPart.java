package com.ikservices.oficinamecanica.inventory.application.usecases;

import java.time.LocalDate;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;

public class GetBalanceByPart {
	private final MovementRepository repository;
	
	public GetBalanceByPart(MovementRepository repository) {
		this.repository = repository;
	}
	
	public Integer execute(Integer partId, Long workshopId) {
		LocalDate lastFinalBalanceDate = repository.getLastFinalBalanceDateByPart(partId, workshopId);
		Integer lastFinalBalanceValue = repository.getLastFinalBalanceValueByPart(partId, workshopId, lastFinalBalanceDate);
		Integer movementQuantity = repository.getMovementQuantityByPart(partId, workshopId, lastFinalBalanceDate);
	
		if(lastFinalBalanceDate == null || lastFinalBalanceValue == null) {
			throw new IKException("Null object.");
		}
		
		return lastFinalBalanceValue + movementQuantity;
	}
}
