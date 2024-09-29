package com.ikservices.oficinamecanica.services.application.usecases;

import java.util.List;

import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;
import com.ikservices.oficinamecanica.services.domain.Service;

public class ListServices {
	private final ServiceRepository repository;
	
	public ListServices(ServiceRepository repository) {
		this.repository = repository;
	}
	
	public List<Service> execute(Long workshopId, String search){
		return repository.getServiceList(workshopId, search);
	}
}
