package com.ikservices.oficinamecanica.services.infra.gateway;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;
import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;
import com.ikservices.oficinamecanica.services.infra.ServiceConverter;
import com.ikservices.oficinamecanica.services.infra.persistence.ServiceEntity;
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
		Optional<ServiceEntity> optional = repository.findById(new ServiceEntityId(service.getId().getId(), service.getId().getWorkshopId()));
		if(optional.isPresent()) {
			throw new IKException(HttpStatus.FOUND.value(), IKMessageType.WARNING, "Serviço já cadastrado.");
		}
		ServiceEntity savedEntity = repository.save(converter.parseEntity(service));
		return converter.parseService(savedEntity);
	}

	@Override
	public Service updateService(Service service) {
		Optional<ServiceEntity> optional = repository.findById(new ServiceEntityId(service.getId().getId(), service.getId().getWorkshopId()));
		ServiceEntity entity = optional.orElse(null);
		
		if(Objects.nonNull(entity)) {
			entity.update(converter.parseEntity(service));
		}
		
		return converter.parseService(entity);
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

	@Override
	public Long getNextServiceId(Long workshopId) {
		return this.repository.getNextServiceId(workshopId);
	}

	
}
