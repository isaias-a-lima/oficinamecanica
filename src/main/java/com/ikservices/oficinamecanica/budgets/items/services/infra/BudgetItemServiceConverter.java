package com.ikservices.oficinamecanica.budgets.items.services.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemServiceId;
import com.ikservices.oficinamecanica.budgets.items.services.infra.controller.BudgetItemServiceRequestDTO;
import com.ikservices.oficinamecanica.budgets.items.services.infra.controller.BudgetItemServiceResponseDTO;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntity;
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
		item.setDiscount(entity.getDiscount());
		item.setQuantity(entity.getQuantity());
		item.setService(serviceConverter.parseService(entity.getServiceEntity()));
		item.setBudget(budgetConverter.parseBudget(entity.getBudgetEntity()));
		
		return item;
	}
	
	public BudgetItemServiceEntity parseEntity(BudgetItemService item) {
		if(Objects.isNull(item)) {
			throw new IKException("Null Object.");
		}
		
		BudgetItemServiceEntity entity = new BudgetItemServiceEntity();
		
		entity.setBudgetEntity(budgetConverter.parseEntity(item.getBudget()));
		entity.setCost(item.getService().getCost());
		entity.setDiscount(item.getDiscount());
		entity.setQuantity(item.getQuantity());
		entity.setServiceEntity(serviceConverter.parseEntity(item.getService()));
		entity.setServiceId(item.getService().getId().getId());
		
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
		if(Objects.nonNull(requestDTO)) {
			throw new IKException("Null object.");
		}
		
		BudgetItemService item = new BudgetItemService();
		item.setDiscount(requestDTO.getDiscount());
		item.setItemId(new BudgetItemServiceId(requestDTO.getItemId(), requestDTO.getBudgetId()));
		item.setQuantity(requestDTO.getQuantity());
		
		Service service = new Service();
		ServiceId serviceId = new ServiceId();
		serviceId.setId(requestDTO.getServiceId());
		
		return item;
	}
	
	public BudgetItemServiceResponseDTO parseItemToDTO(BudgetItemService item) {
		if(Objects.nonNull(item)) {
			throw new IKException("Null Object.");
		}
		
		BudgetItemServiceResponseDTO responseDTO = new BudgetItemServiceResponseDTO();
		responseDTO.setCost(item.getService().getCost());
		responseDTO.setDiscount(item.getDiscount());
		responseDTO.setQuantity(item.getQuantity());
		responseDTO.setServiceDTO(serviceConverter.parseDTO(item.getService()));
		responseDTO.setItemId(item.getItemId().getId());
		
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

