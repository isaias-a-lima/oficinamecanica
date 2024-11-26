package com.ikservices.oficinamecanica.budgets.items.services.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ikservices.oficinamecanica.budgets.infra.controller.BudgetDTO;
import org.springframework.stereotype.Component;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
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
	private final BudgetConverter budgetConverter;
	private final ServiceConverter serviceConverter;
	
	public BudgetItemServiceConverter(BudgetConverter budgetConverter, ServiceConverter serviceConverter) {
		this.budgetConverter = budgetConverter;
		this.serviceConverter = serviceConverter;
	}
	
	public BudgetItemService parseDomain(BudgetItemServiceEntity entity) {
		if(Objects.isNull(entity)) {
			throw new IKException("Null object.");
		}
		
		BudgetItemService item = new BudgetItemService();
		item.setItemId(new BudgetItemServiceId(entity.getId().getId(), entity.getId().getBudgetId()));
		item.setService(serviceConverter.parseService(entity.getServiceEntity()));
		item.setBudget(budgetConverter.parseBudget(entity.getBudgetEntity()));
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
		entity.setBudgetEntity(Objects.nonNull(item.getBudget()) ? budgetConverter.parseEntity(item.getBudget()) : null);
		entity.setServiceEntity(Objects.nonNull(item.getService()) ? serviceConverter.parseEntity(item.getService()) : null);
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
	
	public BudgetItemService parseRequestDTO(BudgetItemServiceRequestDTO requestDTO) {
		if(Objects.isNull(requestDTO)) {
			throw new IKException("Null object.");
		}
		
		BudgetItemService item = new BudgetItemService();
		
		item.setItemId(new BudgetItemServiceId(requestDTO.getItemId(), requestDTO.getBudgetId()));
		
		Service service = new Service();
		ServiceId serviceId = new ServiceId();
		serviceId.setId(requestDTO.getServiceId());
		service.setId(serviceId);
		
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
		responseDTO.setBudget(new BudgetDTO(item.getBudget(), item.getItemId().getBudgetId(), null));
		responseDTO.setCost(item.getService().getCost());
		responseDTO.setDiscount(item.getDiscount());
		responseDTO.setQuantity(item.getQuantity());
		responseDTO.setService(serviceConverter.parseDTO(item.getService()));

		
		return responseDTO;
	}
	
	public List<BudgetItemService> parseRequestDTOList(List<BudgetItemServiceRequestDTO> requestDTOList) {
		List<BudgetItemService> itemList = new ArrayList<>();
		
		if(Objects.nonNull(requestDTOList) && !requestDTOList.isEmpty()) {
			for(BudgetItemServiceRequestDTO requestDTO : requestDTOList) {
				itemList.add(this.parseRequestDTO(requestDTO));
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
}

