package com.ikservices.oficinamecanica.workorders.payments.infra.gateways;

import java.time.LocalDate;
import java.util.List;

import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.infra.WorkOrderConverter;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntityId;
import com.ikservices.oficinamecanica.workorders.payments.application.gateways.PaymentRepository;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentId;
import com.ikservices.oficinamecanica.workorders.payments.infra.PaymentConverter;
import com.ikservices.oficinamecanica.workorders.payments.infra.persistence.PaymentEntityId;
import com.ikservices.oficinamecanica.workorders.payments.infra.persistence.PaymentRepositoryJPA;

public class PaymentRepositoryImpl implements PaymentRepository {
	PaymentConverter converter;
	PaymentRepositoryJPA repositoryJPA;
	
	public PaymentRepositoryImpl(PaymentConverter converter, 
			PaymentRepositoryJPA repositoryJPA) {
		this.converter = converter;
		this.repositoryJPA = repositoryJPA;
	}
	
	@Override
	public List<Payment> listPayments(WorkOrderId workOrderId) {
		return this.converter.parseEntityToDomainList(repositoryJPA.findAllByworkOrderId(new WorkOrderEntityId(workOrderId.getWorkOrderId(),
				workOrderId.getBudgetId())));
	}

	@Override
	public List<Payment> listOverduePayments(Long workshopId) {
		return this.converter.parseEntityToDomainList(repositoryJPA.listOverduePaymentsByWorkshopId(workshopId));
	}

	@Override
	public List<Payment> listPaymentsByDuePeriod(Long workshopId, LocalDate dueDateBegin, LocalDate dueDateEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Payment getPayment(PaymentId id) {
		return this.converter.parseEntityToDomain(repositoryJPA.getById(new PaymentEntityId(id.getNumber(),
				id.getWorkOrderId(), id.getBudgetId())));
	}
}
