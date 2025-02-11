package com.ikservices.oficinamecanica.workorders.items.services.infra;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ikservices.oficinamecanica.commons.generics.IKConverter;
import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;
import com.ikservices.oficinamecanica.services.infra.ServiceConverter;
import com.ikservices.oficinamecanica.workorders.infra.WorkOrderConverter;
import com.ikservices.oficinamecanica.workorders.items.services.domain.WorkOrderServiceItem;
import com.ikservices.oficinamecanica.workorders.items.services.domain.WorkOrderServiceItemId;
import com.ikservices.oficinamecanica.workorders.items.services.infra.dto.WorkOrderServiceItemRequestDTO;
import com.ikservices.oficinamecanica.workorders.items.services.infra.dto.WorkOrderServiceItemResponseDTO;
import com.ikservices.oficinamecanica.workorders.items.services.infra.persistence.WorkOrderServiceItemEntity;
import com.ikservices.oficinamecanica.workorders.items.services.infra.persistence.WorkOrderServiceItemEntityId;

@Component
public class WorkOrderServiceItemsConverter extends IKConverter<WorkOrderServiceItemRequestDTO, 
WorkOrderServiceItem, WorkOrderServiceItemEntity, WorkOrderServiceItemResponseDTO>{

	@Autowired
	@Lazy
	private ServiceConverter serviceConverter;
	
	@Override
	public WorkOrderServiceItem parseRequestToDomain(WorkOrderServiceItemRequestDTO request) {
		WorkOrderServiceItem domain = null;
		
		if(Objects.nonNull(request)) {
			domain = new WorkOrderServiceItem();
			
			domain.setItemId(new WorkOrderServiceItemId(request.getItemId(), 
					request.getWorkOrderId(), request.getBudgetId()));
			domain.setService(new Service(new ServiceId(request.getServiceId(), request.getWorkshopId())));
			domain.setQuantity(request.getQuantity());
			domain.setItemValue(request.getItemValue());
			domain.setDiscount(request.getDiscount());
		}
		
		return domain;
	}

	@Override
	public WorkOrderServiceItemEntity parseDomainToEntity(WorkOrderServiceItem domain) {
		WorkOrderServiceItemEntity entity = null;
		
		if(Objects.nonNull(domain)) {
			entity = new WorkOrderServiceItemEntity();
			
			entity.setId(new WorkOrderServiceItemEntityId(domain.getItemId().getItemId(), 
					domain.getItemId().getWorkOrder(),
					domain.getItemId().getBudgetId()));
			entity.setServiceId(domain.getService().getId().getId());
			entity.setWorkshopId(domain.getService().getId().getWorkshopId());
			entity.setService(serviceConverter.parseEntity(domain.getService()));
			entity.setQuantity(domain.getQuantity());
			entity.setItemValue(domain.getItemValue());
			entity.setDiscount(domain.getDiscount());
		}
		
		return entity;
	}

	@Override
	public WorkOrderServiceItem parseEntityToDomain(WorkOrderServiceItemEntity entity) {
		WorkOrderServiceItem domain = null;
		
		if(Objects.nonNull(domain)) {
			domain = new WorkOrderServiceItem();
			
			domain.setItemId(new WorkOrderServiceItemId(entity.getId().getItemId(),
					entity.getId().getWorkOrderItemId(), entity.getServiceId()));
			domain.setService(serviceConverter.parseService(entity.getService()));
			domain.setQuantity(entity.getQuantity());
			domain.setItemValue(entity.getItemValue());
			domain.setDiscount(entity.getDiscount());
		}
		
		return domain;
	}

	@Override
	public WorkOrderServiceItemResponseDTO parseDomainToResponse(WorkOrderServiceItem domain) {
		WorkOrderServiceItemResponseDTO dto = null;
		
		if(Objects.nonNull(domain)) {
			dto = new WorkOrderServiceItemResponseDTO();
			
			dto.setItemId(domain.getItemId().getItemId());
			dto.setWorkOrderId(domain.getItemId().getWorkOrder());
			dto.setBudgetId(domain.getItemId().getBudgetId());
			dto.setServiceDTO(serviceConverter.parseDTO(domain.getService()));
			dto.setQuantity(domain.getQuantity());
			dto.setItemValue(domain.getItemValue());
			dto.setDiscount(domain.getDiscount());
		}
		
		return dto;
	}	
}
