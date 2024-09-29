package com.ikservices.oficinamecanica.parts.application.usecases;

import com.ikservices.oficinamecanica.parts.application.gateways.PartRepository;

public class GetNextPartId {
	private final PartRepository repository;
	
	public GetNextPartId(PartRepository repository) {
		this.repository = repository;
	}
	
	public Long execute(Long workshopId) {
		return repository.getNextPartId(workshopId);
	}
}
