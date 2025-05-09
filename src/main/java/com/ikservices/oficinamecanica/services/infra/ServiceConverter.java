package com.ikservices.oficinamecanica.services.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;
import com.ikservices.oficinamecanica.services.infra.controller.ServiceDTO;
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
			throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), Constants.getNULL_PARAM_MESSAGE()));
		}
		
		Service service = new Service();
		service.setId(new ServiceId(entity.getId().getId(), entity.getId().getWorkshopId()));
		service.setWorkshop(Objects.nonNull(entity.getWorkshopEntity()) ? 
				workshopConverter.parseWorkshop(entity.getWorkshopEntity()) : null);
		service.setCost(entity.getCost());
		service.setDescription(entity.getDescription());
		
		return service;
	}
	
	public ServiceEntity parseEntity(Service service) {
		if(Objects.isNull(service)) {
			throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), Constants.getNULL_PARAM_MESSAGE()));
		}
		
		ServiceEntity entity = new ServiceEntity();
		
		entity.setId(new ServiceEntityId(service.getId().getId(), 
				service.getId().getWorkshopId()));
		entity.setCost(service.getCost());
		entity.setDescription(service.getDescription());
		entity.setWorkshopEntity(Objects.nonNull(service.getWorkshop()) ?
				workshopConverter.parseWorkshopEntity(service.getWorkshop(), service.getId()
						.getWorkshopId()) : null);
		
		return entity;
	}
	
	public ServiceDTO parseDTO(Service service) {
		if(Objects.isNull(service)) {
			throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), Constants.getNULL_PARAM_MESSAGE()));
		}
		
		ServiceDTO dto = new ServiceDTO();
		
		dto.setServiceId(service.getId().getId());
		dto.setWorkshopId(service.getId().getWorkshopId());
		dto.setDescription(service.getDescription());
		dto.setCost(NumberUtil.parseStringMoney(service.getCost()));
		
		return dto;
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
	
	public List<ServiceEntity> parseServiceEntityList(List<Service> serviceList){
		List<ServiceEntity> serviceEntityList = new ArrayList<>();
		if(Objects.nonNull(serviceList) && !serviceList.isEmpty()){
			for (Service service : serviceList) {
				serviceEntityList.add(this.parseEntity(service));
			}
		}
		
		return serviceEntityList;
	}
	
	public List<ServiceDTO> parseServiceDTOList(List<Service> serviceList){
		List<ServiceDTO> serviceDTOList = new ArrayList<>();
		if(Objects.nonNull(serviceList) && !serviceList.isEmpty()){
			for (Service service : serviceList) {
				serviceDTOList.add(this.parseDTO(service));
			}
		}
		
		return serviceDTOList;
	}

	public Service parseService(ServiceDTO serviceDTO) {
		if(Objects.isNull(serviceDTO)) {
			throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), Constants.getNULL_PARAM_MESSAGE()));
		}
		
		Service service = new Service();
		service.setId(new ServiceId(serviceDTO.getServiceId(), serviceDTO.getWorkshopId()));
		service.setDescription(serviceDTO.getDescription());
		service.setCost(NumberUtil.parseBigDecimal(serviceDTO.getCost()));
		
		return service;
	}
}
