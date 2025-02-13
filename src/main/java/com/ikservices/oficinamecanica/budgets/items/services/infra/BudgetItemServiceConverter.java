package com.ikservices.oficinamecanica.budgets.items.services.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ikservices.oficinamecanica.workorders.items.services.infra.persistence.WorkOrderServiceItemEntity;
import com.ikservices.oficinamecanica.workorders.items.services.infra.persistence.WorkOrderServiceItemEntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.budgets.infra.controller.BudgetDTO;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemServiceId;
import com.ikservices.oficinamecanica.budgets.items.services.infra.controller.BudgetItemServiceRequestDTO;
import com.ikservices.oficinamecanica.budgets.items.services.infra.controller.BudgetItemServiceResponseDTO;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntity;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntityId;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;
import com.ikservices.oficinamecanica.services.infra.ServiceConverter;

@Component
public class BudgetItemServiceConverter {

	@Autowired()
	@Lazy
	private BudgetConverter budgetConverter;
	private final ServiceConverter serviceConverter;
	
	public BudgetItemServiceConverter(ServiceConverter serviceConverter) {
		this.serviceConverter = serviceConverter;
	}
	
	public BudgetItemService parseDomain(BudgetItemServiceEntity entity) {
		if(Objects.isNull(entity)) {
			throw new IKException("Null object.");
		}
		
		BudgetItemService item = new BudgetItemService();
		item.setItemId(new BudgetItemServiceId(entity.getId().getId(), entity.getId().getBudgetId()));
		item.setBudget(Objects.nonNull(entity.getBudgetEntity()) ? budgetConverter.parseBudget(entity.getBudgetEntity(), true) : null);
		if (Objects.nonNull(entity.getServiceEntity())) {
			item.setService(serviceConverter.parseService(entity.getServiceEntity()));
			item.getService().setCost(entity.getCost());
		}
		item.setDiscount(entity.getDiscount());
		item.setQuantity(entity.getQuantity());
		
		return item;
	}
	
	public BudgetItemServiceEntity parseEntity(BudgetItemService item) {
		if(Objects.isNull(item)) {
			throw new IKException("Null Object.");
		}
		
		BudgetItemServiceEntity entity = new BudgetItemServiceEntity();
		
		entity.setId(new BudgetItemServiceEntityId(item.getItemId().getId(), item.getItemId().getBudgetId()));
		
		entity.setBudgetEntity(Objects.nonNull(item.getBudget()) ? budgetConverter.parseEntity(item.getBudget(), null, null) : null);
		
		if (Objects.nonNull(item.getService())) {
			entity.setServiceId(item.getService().getId().getId());
			entity.setWorkshopId(item.getService().getId().getWorkshopId());			
			entity.setServiceEntity(serviceConverter.parseEntity(item.getService()));
		}
		
		entity.setQuantity(item.getQuantity());
		entity.setCost(item.getService().getCost());
		entity.setDiscount(item.getDiscount());
		
		return entity;
	}
	
	public List<BudgetItemServiceEntity> parseEntityList(List<BudgetItemService> itemList) {
		List<BudgetItemServiceEntity> entityList = new ArrayList<>();
		
		if(Objects.nonNull(itemList) && !itemList.isEmpty()) {
			for(BudgetItemService item : itemList) {
				entityList.add(this.parseEntity(item));
			}
		}
		
		return entityList;
	}
	
	public List<BudgetItemService> parseDomainList(List<BudgetItemServiceEntity> entityList) {
		List<BudgetItemService> itemList = new ArrayList<>();
		
		if(Objects.nonNull(entityList) && !entityList.isEmpty()) {
			for(BudgetItemServiceEntity entity : entityList) {
				itemList.add(this.parseDomain(entity));
			}
		}
		
		return itemList;
	}
	
	public BudgetItemService parseDomain(BudgetItemServiceRequestDTO requestDTO) {
		if(Objects.isNull(requestDTO)) {
			throw new IKException("Null object.");
		}
		
		BudgetItemService item = new BudgetItemService();
		
		item.setItemId(new BudgetItemServiceId(requestDTO.getItemId(), requestDTO.getBudgetId()));
		
		Service service = new Service();
		service.setId(new ServiceId(requestDTO.getServiceId(), requestDTO.getWorkshopId()));
		service.setCost(requestDTO.getCost());
		item.setService(service);
		
		item.setQuantity(requestDTO.getQuantity());
		
		item.setDiscount(requestDTO.getDiscount());
		
		return item;
	}
	
	public BudgetItemServiceResponseDTO parseItemToDTO(BudgetItemService item) {
		if(Objects.isNull(item)) {
			throw new IKException("Null Object.");
		}
		
		BudgetItemServiceResponseDTO responseDTO = new BudgetItemServiceResponseDTO();
		responseDTO.setItemId(item.getItemId().getId());
		responseDTO.setBudgetId(item.getItemId().getBudgetId());
		if (Objects.nonNull(item.getService())) {
			responseDTO.setService(serviceConverter.parseDTO(item.getService()));
			responseDTO.setCost(item.getService().getCost());
		}
		responseDTO.setDiscount(item.getDiscount());
		responseDTO.setQuantity(item.getQuantity());
		responseDTO.setTotalAmount(item.getTotal());

		
		return responseDTO;
	}
	
	public List<BudgetItemService> parseRequestToDomainList(List<BudgetItemServiceRequestDTO> requestDTOList) {
		List<BudgetItemService> itemList = new ArrayList<>();
		
		if(Objects.nonNull(requestDTOList) && !requestDTOList.isEmpty()) {
			for(BudgetItemServiceRequestDTO requestDTO : requestDTOList) {
				itemList.add(this.parseDomain(requestDTO));
			}
		}
		
		return itemList;
	}
	
	public List<BudgetItemServiceResponseDTO> parseItemListToResponseList(List<BudgetItemService> itemList) {
		List<BudgetItemServiceResponseDTO> responseList = new ArrayList<>();
		
		if(Objects.nonNull(itemList) && !itemList.isEmpty()) {
			for(BudgetItemService item : itemList) {
				responseList.add(this.parseItemToDTO(item));
			}
		}
		
		return responseList;
	}

	public WorkOrderServiceItemEntity parseBudgetServiceItemToWorkOrderServiceItem(BudgetItemServiceEntity source, Long workOrderId) {
		if (Objects.isNull(source)) {
			throw new IKException("Null objects");
		}
		WorkOrderServiceItemEntity target = new WorkOrderServiceItemEntity();
		target.setId(new WorkOrderServiceItemEntityId(source.getId().getId(), workOrderId, source.getId().getBudgetId()));
		target.setServiceId(source.getServiceId());
		target.setWorkshopId(source.getWorkshopId());
		target.setService(source.getServiceEntity());
		target.setQuantity(source.getQuantity());
		target.setItemValue(source.getCost());
		target.setDiscount(source.getDiscount());
		return target;
	}

	public List<WorkOrderServiceItemEntity> parseToWorkOrderServiceItemList(List<BudgetItemServiceEntity> sourceList, Long workOrderId) {
		List<WorkOrderServiceItemEntity> targetList = new ArrayList<>();
		if (Objects.nonNull(sourceList) && !sourceList.isEmpty()) {
			for (BudgetItemServiceEntity source : sourceList) {
				targetList.add(this.parseBudgetServiceItemToWorkOrderServiceItem(source, workOrderId));
			}
		}
		return targetList;
	}
}

