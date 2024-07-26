package com.ikservices.oficinamecanica.services.application.usecases;

import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;

public class GetNextServiceId {
	private final ServiceRepository repository;
	
	public GetNextServiceId(ServiceRepository repository) {
		this.repository = repository;
	}
	
	public Long execute(Long workshopId) {
		return this.repository.getNextServiceId(workshopId);
	}
}
