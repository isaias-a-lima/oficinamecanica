package com.ikservices.oficinamecanica.inventory.application.usecases;

import java.time.LocalDate;

import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;

public class GetLastFinalBalanceValueByPart {
	private final MovementRepository repository;
	
	public GetLastFinalBalanceValueByPart(MovementRepository repository) {
		this.repository = repository;
	}
	
	public Integer execute(Integer partId, Long workshopId, LocalDate basedate) {
		return repository.getLastFinalBalanceValueByPart(partId, workshopId, basedate);
	}
}
