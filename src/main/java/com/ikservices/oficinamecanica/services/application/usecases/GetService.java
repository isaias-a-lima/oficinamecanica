package com.ikservices.oficinamecanica.services.application.usecases;

import java.util.Map;

import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;
import com.ikservices.oficinamecanica.services.domain.Service;

public class GetService {
	private final ServiceRepository repository;
	
	public GetService(ServiceRepository repository) {
		this.repository = repository;
	}
	
	public Map<Long, Service> getService(Long id) {
		return repository.getService(id);
	}
}

