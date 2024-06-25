package com.ikservices.oficinamecanica.services.application.usecases;

import java.util.Map;

import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;
import com.ikservices.oficinamecanica.services.domain.Service;

public class ListServices {
	private final ServiceRepository repository;
	
	public ListServices(ServiceRepository repository) {
		this.repository = repository;
	}
	
	public Map<Long, Service> execute(Long workshopId){
		return repository.getServiceList(workshopId);
	}
}
