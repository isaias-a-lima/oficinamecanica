package com.ikservices.oficinamecanica.inventory.application.usecases;

import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;

public class GetBalanceByPart {
	private final MovementRepository repository;
	
	public GetBalanceByPart(MovementRepository repository) {
		this.repository = repository;
	}
	
	public Integer execute(Integer partId) {
		return repository.getBalanceByPart(partId);
	}
}
