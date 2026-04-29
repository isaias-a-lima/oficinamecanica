package com.ikservices.oficinamecanica.inventory.application.usecases;

import java.time.LocalDate;

import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;

public class GetLastFinalBalanceDateByPart {
	private final MovementRepository repository;
	
	public GetLastFinalBalanceDateByPart(MovementRepository repository) {
		this.repository = repository;
	}
	
	public LocalDate execute(Integer partId, Long workshopId) {
		return repository.getLastFinalBalanceDateByPart(partId, workshopId);
	}
}
