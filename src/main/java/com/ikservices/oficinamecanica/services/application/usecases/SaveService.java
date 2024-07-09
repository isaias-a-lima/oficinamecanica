package com.ikservices.oficinamecanica.services.application.usecases;

import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;
import com.ikservices.oficinamecanica.services.domain.Service;

public class SaveService {
	private final ServiceRepository repository;
	
	public SaveService(ServiceRepository repository) {
		this.repository = repository;
	}
	
	public Service saveService(Service service) {
		return repository.saveService(service);
	}
}
