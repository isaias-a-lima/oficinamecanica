package com.ikservices.oficinamecanica.services.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.service.spi.ServiceException;

import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;
import com.ikservices.oficinamecanica.services.infra.persistence.ServiceEntity;
import com.ikservices.oficinamecanica.services.infra.persistence.ServiceEntityId;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopConverter;

public class ServiceConverter {
	
	private final WorkshopConverter workshopConverter;
	
	public ServiceConverter(WorkshopConverter workshopConverter) {
		this.workshopConverter = workshopConverter;
	}
	
	public Service parseService(ServiceEntity entity) {
		if(Objects.isNull(entity)) {
			throw new ServiceException("Null object");
		}
		
		Service service = new Service();
		service.setId(new ServiceId(entity.getId().getId(), entity.getId().getWorkshopId()));
		service.setWorkshop(workshopConverter.parseWorkshop(entity.getWorkshop()));
		service.setCost(entity.getCost());
		service.setDescription(entity.getDescription());
		
		return service;
	}
	
	public ServiceEntity parseEntity(Service service) {
		if(Objects.isNull(service)) {
			throw new ServiceException("Null object");
		}
		
		ServiceEntity entity = new ServiceEntity();
		
		entity.setId(new ServiceEntityId(service.getId().getId(), 
				service.getId().getWorkshopId()));
		entity.setCost(service.getCost());
		entity.setDescription(service.getDescription());
		entity.setWorkshop(Objects.nonNull(service.getWorkshop()) ?
				workshopConverter.parseWorkshopEntity(service.getWorkshop(), service.getId()
						.getWorkshopId()) : null);
		
		return entity;
	}
	
	public List<Service> parseServiceList(List<ServiceEntity> serviceEntityList) {
		List<Service> serviceList = new ArrayList<>();
		if(Objects.nonNull(serviceEntityList) && !serviceEntityList.isEmpty()) {
			for(ServiceEntity serviceEntity : serviceEntityList) {
				serviceList.add(this.parseService(serviceEntity));
			}
		}
		
		return serviceList;
	}
}
