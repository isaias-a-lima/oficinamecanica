package com.ikservices.oficinamecanica.services.application.usecases;

import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;

public class DeleteService {
	private final ServiceRepository repository;
	
	public DeleteService(ServiceRepository repository) {
		this.repository = repository;
	}
	
	public void execute(Long id) {
		repository.deleteService(id);
	}
}
