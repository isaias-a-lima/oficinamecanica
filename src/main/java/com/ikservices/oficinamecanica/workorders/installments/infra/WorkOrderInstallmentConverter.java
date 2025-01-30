package com.ikservices.oficinamecanica.workorders.installments.infra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.installments.domain.WorkOrderInstallment;
import com.ikservices.oficinamecanica.workorders.installments.domain.WorkOrderInstallmentId;
import com.ikservices.oficinamecanica.workorders.installments.infra.dto.WorkOrderInstallmentIdDTO;
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
		if(Objects.nonNull(entityList) && !entityList.isEmpty()) {
			List<WorkOrderInstallment> installmentList = new ArrayList<>();
			
			for(WorkOrderInstallmentEntity entity : entityList) {
				this.parseInstallment(entity);
			}
			
			return installmentList;
		}
		
		return null;
	}
	
	public List<WorkOrderInstallmentEntity> parseEntityList(List<WorkOrderInstallment> installmentList) {
		if(Objects.nonNull(installmentList) && !installmentList.isEmpty()) {
			List<WorkOrderInstallmentEntity> entityList = new ArrayList<>();
			
			for(WorkOrderInstallment installment : installmentList) {
				this.parseEntity(installment);
			}
			
			return entityList;
		}
		
		return null;
	}
	
	public WorkOrderInstallment parseInstallment(WorkOrderInstallmentsDTO dto) {
		if(Objects.isNull(dto)) {
			throw new IKException("Null Object");
		}
		
		WorkOrderInstallment installment = new WorkOrderInstallment();
		installment.setId(new WorkOrderInstallmentId(
				dto.getWorkOrderInstallmentIdDTO().getNumber(),
				dto.getWorkOrderInstallmentIdDTO().getWorkOrderId(), 
				dto.getWorkOrderInstallmentIdDTO().getBudgetId(), 
				dto.getWorkOrderInstallmentIdDTO().getInstallmentType()));
		
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
		dto.setWorkOrderInstallmentIdDTO(new WorkOrderInstallmentIdDTO(
				installment.getId().getNumber(),
				installment.getId().getWorkOrderId(), 
				installment.getId().getBudgetId(), 
				installment.getId().getInstallmentType()));
		
		dto.setDueDate(installment.getDueDate().toString());
		dto.setPayValue(installment.getPayValue());
		dto.setPayDate(installment.getPayDate().toString());
		dto.setNote(installment.getNote());
		
		return dto;
	}
}
