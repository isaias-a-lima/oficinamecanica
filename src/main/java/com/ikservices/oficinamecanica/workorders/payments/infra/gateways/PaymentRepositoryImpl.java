package com.ikservices.oficinamecanica.workorders.payments.infra.gateways;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.infra.WorkOrderConverter;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntity;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntityId;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderRepositoryJPA;
import com.ikservices.oficinamecanica.workorders.payments.application.enumerates.PaymentStateEnum;
import com.ikservices.oficinamecanica.workorders.payments.application.gateways.PaymentRepository;
import com.ikservices.oficinamecanica.workorders.payments.application.usecases.business.PaymentUpdateValidator;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentId;
import com.ikservices.oficinamecanica.workorders.payments.infra.PaymentConstant;
import com.ikservices.oficinamecanica.workorders.payments.infra.PaymentConverter;
import com.ikservices.oficinamecanica.workorders.payments.infra.persistence.PaymentEntity;
import com.ikservices.oficinamecanica.workorders.payments.infra.persistence.PaymentEntityId;
import com.ikservices.oficinamecanica.workorders.payments.infra.persistence.PaymentRepositoryJPA;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PaymentRepositoryImpl implements PaymentRepository {
	private final PaymentConverter converter;
	private final PaymentRepositoryJPA repositoryJPA;
	private final WorkOrderRepositoryJPA workOrderRepositoryJPA;
	private final WorkOrderConverter workOrderConverter;
	
	public PaymentRepositoryImpl(PaymentConverter converter,
                                 PaymentRepositoryJPA repositoryJPA, WorkOrderRepositoryJPA workOrderRepositoryJPA, WorkOrderConverter workOrderConverter) {
		this.converter = converter;
		this.repositoryJPA = repositoryJPA;
		this.workOrderRepositoryJPA = workOrderRepositoryJPA;
        this.workOrderConverter = workOrderConverter;
    }
	
	@Override
	public List<Payment> listPayments(WorkOrderId workOrderId) {
		return this.converter.parseEntityToDomainList(repositoryJPA.findAllByWorkOrderId(new WorkOrderEntityId(workOrderId.getWorkOrderId(),
				workOrderId.getBudgetId())));
	}

	@Override
	public List<Payment> listOverduePayments(Long workshopId) {
		return this.converter.parseEntityToDomainList(repositoryJPA.listOverduePaymentsByWorkshopId(workshopId));
	}

	@Override
	public List<Payment> listPaymentsByDuePeriod(Long workshopId, LocalDate dueDateBegin, LocalDate dueDateEnd, PaymentStateEnum paymentState) {
		return this.converter.parseEntityToDomainList(repositoryJPA.listPaymentsByDuePeriod(workshopId, dueDateBegin, dueDateEnd, paymentState.toString()));
	}

	@Override
	public Payment getPayment(PaymentId id) {

		Optional<PaymentEntity> optional = repositoryJPA.findById(new PaymentEntityId(id.getNumber(), id.getWorkOrderId(), id.getBudgetId()));

		PaymentEntity entity = optional.orElseThrow(() -> new IKException(new IKMessage(IKConstants.IK_HTTP_NOT_FOUND_CODE, IKMessageType.WARNING.getCode(), PaymentConstant.GET_NOT_FOUND_MESSAGE)));

		return this.converter.parseEntityToDomain(entity);
	}

	@Override
	public Payment updatePayment(Payment payment) {

		Optional<PaymentEntity> optional = repositoryJPA.findById(new PaymentEntityId(payment.getId().getNumber(), payment.getId().getWorkOrderId(), payment.getId().getBudgetId()));

		if (!optional.isPresent()) {
			throw new IKException(new IKMessage(IKConstants.IK_HTTP_NOT_FOUND_CODE, IKMessageType.WARNING.getCode(), IKConstants.RESOURCE_TO_UPDATE_NOT_FOUND_MESSAGE));
		}

		PaymentEntity entity = optional.get();
		entity.update(converter.parseDomainToEntity(payment));

		return converter.parseEntityToDomain(entity);
	}

	@Override
	public List<Payment> updatePaymentList(List<Payment> newPaymentList, WorkOrderId workOrderId) {

		if (Objects.isNull(newPaymentList) || Objects.isNull(workOrderId)) {
			throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.NULL_OBJECT_MESSAGE));
		}

		List<PaymentEntity> oldPaymentEntityList = repositoryJPA.findAllByWorkOrderId(new WorkOrderEntityId(workOrderId));

		WorkOrder workOrder = null;

		if (Objects.isNull(oldPaymentEntityList) || oldPaymentEntityList.isEmpty() || oldPaymentEntityList.get(0).getWorkOrder() == null) {
			Optional<WorkOrderEntity> optional = workOrderRepositoryJPA.findById(new WorkOrderEntityId(workOrderId));
			workOrder = workOrderConverter.parseWorkOrder(optional.orElseThrow(()-> new IKException(new IKMessage(IKConstants.IK_HTTP_NOT_FOUND_CODE, IKMessageType.WARNING.getCode(), IKConstants.RESOURCE_TO_UPDATE_NOT_FOUND_MESSAGE))), true);
		} else {
			workOrder = workOrderConverter.parseWorkOrder(oldPaymentEntityList.get(0).getWorkOrder(), true);
		}

		if (!newPaymentList.isEmpty()) {
			newPaymentList.get(0).setWorkOrder(workOrder);
		}

		PaymentUpdateValidator.execute(newPaymentList);

		List<PaymentEntity> newPaymentEntityList = converter.parseDomainToEntityList(newPaymentList);

		List<PaymentEntity> toRemoveList = new ArrayList<>();

		for (PaymentEntity oldEntity : oldPaymentEntityList) {
			int index = newPaymentEntityList.indexOf(oldEntity);
			if (index >= 0) {
				Optional<PaymentEntity> optional = repositoryJPA.findById(oldEntity.getId());
                optional.ifPresent(paymentEntity -> paymentEntity.update(newPaymentEntityList.get(index)));
				oldEntity.update(newPaymentEntityList.get(index));
			} else {
				if (Objects.nonNull(oldEntity.getPayDate()) && oldEntity.getPayDate().isBefore(LocalDate.now())) {
					throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.EXPIRED_DELETE_DATE));
				}
				repositoryJPA.delete(oldEntity);
			}
		}


		for (PaymentEntity newEntity : newPaymentEntityList) {
			boolean notExists = !oldPaymentEntityList.contains(newEntity);
			if (notExists) {
				repositoryJPA.save(newEntity);
			}
		}

		List<PaymentEntity> result = repositoryJPA.findAllByWorkOrderId(new WorkOrderEntityId(workOrderId));

		BigDecimal paidValue = result.stream().filter(p -> p.getPayDate() != null).map(PaymentEntity::getPayValue).reduce(BigDecimal.ZERO, BigDecimal::add);

		Optional<WorkOrderEntity> optional = workOrderRepositoryJPA.findById(new WorkOrderEntityId(workOrderId));
		optional.ifPresent(w -> {
			BigDecimal finalValue = WorkOrder.calculateFinalValue(w.getDiscount(), w.getAmount());
			if (paidValue.compareTo(finalValue) >= 0) {
				w.setPaid(true);
			}
		});

		return converter.parseEntityToDomainList(result);
	}
}
