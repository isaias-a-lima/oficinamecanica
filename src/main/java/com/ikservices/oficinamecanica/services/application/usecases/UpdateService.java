package com.ikservices.oficinamecanica.services.application.usecases;

import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;
import com.ikservices.oficinamecanica.services.domain.Service;

public class UpdateService {
	private final ServiceRepository repository;
	
	public UpdateService(ServiceRepository repository) {
		this.repository = repository;
	}
	
	public Service execute(Service service) {
		return this.repository.updateService(service);
	}
}
