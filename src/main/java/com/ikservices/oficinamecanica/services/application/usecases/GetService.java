package com.ikservices.oficinamecanica.services.application.usecases;

import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;
import com.ikservices.oficinamecanica.services.domain.Service;

public class GetService {
	private final ServiceRepository repository;
	
	public GetService(ServiceRepository repository) {
		this.repository = repository;
	}
	
	public Service getService(Long id) {
		return repository.getService(id);
	}
}

