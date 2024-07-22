package com.ikservices.oficinamecanica.services.infra.gateway;

import java.util.List;

import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;
import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;
import com.ikservices.oficinamecanica.services.infra.ServiceConverter;
import com.ikservices.oficinamecanica.services.infra.persistence.ServiceEntityId;
import com.ikservices.oficinamecanica.services.infra.persistence.ServiceRepositoryJPA;

public class ServiceRepositoryImpl implements ServiceRepository{
	
	private final ServiceConverter converter;
	private final ServiceRepositoryJPA repository;
	
	public ServiceRepositoryImpl(ServiceConverter converter, 
			ServiceRepositoryJPA repository) {
		this.converter = converter;
		this.repository = repository;
	}
	
	@Override
	public Service saveService(Service service) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service updateService(Service service) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service getService(ServiceId id) {
		return this.converter.parseService(repository.getById(new ServiceEntityId(id.getId(), id.getWorkshopId())));
	}

	@Override
	public List<Service> getServiceList(Long workshopId) {
		return this.converter.parseServiceList(repository.findAllByWorkshopId(workshopId));
	}

	@Override
	public void deleteService(Long id) {
		// TODO Auto-generated method stub
		
	}

}
