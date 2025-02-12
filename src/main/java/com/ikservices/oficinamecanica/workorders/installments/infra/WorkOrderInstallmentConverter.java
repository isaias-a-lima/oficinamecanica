package com.ikservices.oficinamecanica.workorders.installments.infra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.workorders.installments.domain.WorkOrderInstallment;
import com.ikservices.oficinamecanica.workorders.installments.domain.WorkOrderInstallmentId;
import com.ikservices.oficinamecanica.workorders.installments.infra.dto.WorkOrderInstallmentsDTO;
import com.ikservices.oficinamecanica.workorders.installments.infra.persistence.WorkOrderInstallmentEntity;
import com.ikservices.oficinamecanica.workorders.installments.infra.persistence.WorkOrderInstallmentEntityId;

public class WorkOrderInstallmentConverter {
	
	public WorkOrderInstallment parseInstallment(WorkOrderInstallmentEntity entity) {
		 if(Objects.isNull(entity)) {
			 throw new IKException("Null Object");
		 }
		 
		 WorkOrderInstallment installment = new WorkOrderInstallment();
		 installment.setId(new WorkOrderInstallmentId(entity.getId().getNumber(), 
				 entity.getId().getWorkOrderId(), 
				 entity.getId().getBudgetId(), 
				 entity.getId().getInstallmentType()));
		 
		 installment.setDueDate(entity.getDueDate());
		 installment.setPayValue(entity.getPayValue());
		 installment.setPayDate(entity.getPayDate());
		 installment.setNote(entity.getNote());
		 
		 return installment;
	}
	
	public WorkOrderInstallmentEntity parseEntity(WorkOrderInstallment installment) {
		if(Objects.isNull(installment)) {
			throw new IKException("Null Object");
		}
		
		WorkOrderInstallmentEntity entity = new WorkOrderInstallmentEntity();
		entity.setId(new WorkOrderInstallmentEntityId(installment.getId().getNumber(), 
				installment.getId().getWorkOrderId(), installment.getId().getBudgetId(),
				installment.getId().getInstallmentType()));
		entity.setDueDate(installment.getDueDate());
		entity.setPayValue(installment.getPayValue());
		entity.setPayDate(installment.getPayDate());
		entity.setNote(installment.getNote());
		
		return entity;
	}
	
	public List<WorkOrderInstallment> parseInstallmentList(List<WorkOrderInstallmentEntity> entityList) {
		List<WorkOrderInstallment> domainList = new ArrayList<>();
		if (Objects.nonNull(entityList) && !entityList.isEmpty()) {
			for (WorkOrderInstallmentEntity entity : entityList) {
				domainList.add(this.parseInstallment(entity));
			}
		}
		return domainList;
	}
	
	public List<WorkOrderInstallmentEntity> parseEntityList(List<WorkOrderInstallment> domainList) {
		List<WorkOrderInstallmentEntity> entityList = new ArrayList<>();
		if(Objects.nonNull(domainList) && !domainList.isEmpty()) {
			for(WorkOrderInstallment domain : domainList) {
				entityList.add(this.parseEntity(domain));
			}
		}		
		return entityList;
	}
	
	public WorkOrderInstallment parseInstallment(WorkOrderInstallmentsDTO dto) {
		if(Objects.isNull(dto)) {
			throw new IKException("Null Object");
		}
		
		WorkOrderInstallment installment = new WorkOrderInstallment();
		installment.setId(new WorkOrderInstallmentId(
				dto.getNumber(),
				dto.getWorkOrderId(), 
				dto.getBudgetId(), 
				dto.getInstallmentType()));
		
		installment.setDueDate(LocalDate.parse(dto.getDueDate()));
		installment.setPayValue(dto.getPayValue());
		installment.setPayDate(LocalDate.parse(dto.getPayDate()));
		installment.setNote(dto.getNote());
		
		return installment;
	}
	
	public WorkOrderInstallmentsDTO parseDTO(WorkOrderInstallment installment) {
		if(Objects.isNull(installment)) {
			throw new IKException("Null Object");
		}
		
		WorkOrderInstallmentsDTO dto = new WorkOrderInstallmentsDTO();
		//compound key
		dto.setNumber(installment.getId().getNumber());
		dto.setWorkOrderId(installment.getId().getWorkOrderId());
		dto.setBudgetId(installment.getId().getBudgetId());
		dto.setInstallmentType(installment.getId().getInstallmentType());
		
		dto.setDueDate(Objects.nonNull(installment.getDueDate()) ? installment.getDueDate().toString() : "");
		dto.setPayValue(installment.getPayValue());
		dto.setPayDate(Objects.nonNull(installment.getPayDate()) ? installment.getPayDate().toString() : "");
		dto.setNote(Objects.nonNull(installment.getNote()) ? installment.getNote() : "");
		
		return dto;
	}

	public List<WorkOrderInstallmentsDTO> parseDomainToDTO(List<WorkOrderInstallment> installments) {
		List<WorkOrderInstallmentsDTO> dtoList = new ArrayList<>();
		if (Objects.nonNull(installments) && !installments.isEmpty()) {
			for (WorkOrderInstallment installment : installments) {
				dtoList.add(this.parseDTO(installment));
			}
		}
		return dtoList;
	}
}
