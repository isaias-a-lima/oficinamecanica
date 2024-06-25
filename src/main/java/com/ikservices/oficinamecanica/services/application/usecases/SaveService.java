package com.ikservices.oficinamecanica.services.application.usecases;

import java.util.Map;

import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;
import com.ikservices.oficinamecanica.services.domain.Service;

public class SaveService {
	private final ServiceRepository repository;
	
	public SaveService(ServiceRepository repository) {
		this.repository = repository;
	}
	
	public Map<Long, Service> saveService(Service service) {
		return repository.saveService(service);
	}
}
