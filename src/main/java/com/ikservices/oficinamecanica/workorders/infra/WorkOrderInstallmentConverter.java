package com.ikservices.oficinamecanica.workorders.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderInstallment;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderInstallmentId;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderInstallmentEntity;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderInstallmentEntityId;

public class WorkOrderInstallmentConverter {
	
	public WorkOrderInstallment parseInstallment(WorkOrderInstallmentEntity entity) {
		 if(Objects.isNull(entity)) {
			 throw new IKException("Null Object");
		 }
		 
		 WorkOrderInstallment installment = new WorkOrderInstallment();
		 installment.setId(new WorkOrderInstallmentId(entity.getWorkOrderInstallmentEntityId().getNumber(),
				 entity.getWorkOrderInstallmentEntityId().getWorkOrderId(), 
				 entity.getWorkOrderInstallmentEntityId().getBudgetId(), 
				 entity.getWorkOrderInstallmentEntityId().getInstallmentType()));
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
		entity.setWorkOrderInstallmentEntityId(new WorkOrderInstallmentEntityId(installment.getId().getNumber(), 
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
}
