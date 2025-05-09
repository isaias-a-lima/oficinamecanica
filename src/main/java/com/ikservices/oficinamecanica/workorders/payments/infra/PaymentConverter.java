package com.ikservices.oficinamecanica.workorders.payments.infra;

import com.ikservices.oficinamecanica.commons.generics.IKConverter;
import com.ikservices.oficinamecanica.commons.utils.DateUtil;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.workorders.infra.WorkOrderConverter;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentId;
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentTypeEnum;
import com.ikservices.oficinamecanica.workorders.payments.infra.dto.PaymentDTO;
import com.ikservices.oficinamecanica.workorders.payments.infra.persistence.PaymentEntity;
import com.ikservices.oficinamecanica.workorders.payments.infra.persistence.PaymentEntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PaymentConverter extends IKConverter<PaymentDTO, Payment, PaymentEntity, PaymentDTO> {

    @Autowired
    @Lazy
    private WorkOrderConverter workOrderConverter;

    @Override
    public Payment parseRequestToDomain(PaymentDTO request) {
        Payment domain = null;
        if (Objects.nonNull(request)) {
            domain = new Payment();
            domain.setId(new PaymentId(request.getNumber(), request.getWorkOrderId(), request.getBudgetId()));
            domain.setDueDate(DateUtil.parseToLocalDate(request.getDueDate()));
            domain.setPaymentValue(NumberUtil.parseBigDecimal(request.getPayValue()));
            domain.setPaymentType(Objects.nonNull(request.getPaymentType()) ? PaymentTypeEnum.findByIndex(request.getPaymentType()) : PaymentTypeEnum.NONE);
            domain.setNote(request.getNote());
            domain.setPayDate(DateUtil.parseToLocalDate(request.getPayDate()));
        }
        return domain;
    }

    @Override
    public PaymentEntity parseDomainToEntity(Payment domain) {
        PaymentEntity entity = null;
        if (Objects.nonNull(domain)) {
            entity = new PaymentEntity();
            entity.setId(new PaymentEntityId(domain.getId().getNumber(), domain.getId().getWorkOrderId(), domain.getId().getBudgetId()));
            entity.setDueDate(domain.getDueDate());
            entity.setPayValue(domain.getPaymentValue());
            entity.setPaymentType(Objects.nonNull(domain.getPaymentType()) ? domain.getPaymentType() : PaymentTypeEnum.NONE);
            entity.setNote(domain.getNote());
            entity.setPayDate(domain.getPayDate());

        }
        return entity;
    }

    @Override
    public Payment parseEntityToDomain(PaymentEntity entity) {
        Payment domain = null;
        if (Objects.nonNull(entity)) {
            domain = new Payment();
            domain.setId(new PaymentId(entity.getId().getNumber(), entity.getId().getWorkOrderId(), entity.getId().getBudgetId()));
            if (Objects.nonNull(entity.getWorkOrder())) {
                //avoid stackOverFlowError
                entity.getWorkOrder().setPayments(null);
                domain.setWorkOrder(workOrderConverter.parseWorkOrder(entity.getWorkOrder()));
            }
            domain.setDueDate(entity.getDueDate());
            domain.setPaymentValue(entity.getPayValue());
            domain.setPaymentType(Objects.nonNull(entity.getPaymentType()) ? entity.getPaymentType() : PaymentTypeEnum.NONE);
            domain.setNote(entity.getNote());
            domain.setPayDate(entity.getPayDate());
        }
        return domain;
    }

    @Override
    public PaymentDTO parseDomainToResponse(Payment domain) {
        PaymentDTO dto = null;
        if (Objects.nonNull(domain)) {
            dto = new PaymentDTO();
            dto.setNumber(domain.getId().getNumber());
            dto.setWorkOrderId(domain.getId().getWorkOrderId());
            dto.setWorkOrder(Objects.nonNull(domain.getWorkOrder()) ? workOrderConverter.parseResponseDTO(domain.getWorkOrder()) : null);
            dto.setBudgetId(domain.getId().getBudgetId());
            dto.setDueDate(DateUtil.parseToString(domain.getDueDate()));
            dto.setPayValue(NumberUtil.parseStringMoney(domain.getPaymentValue()));
            dto.setPaymentType(Objects.nonNull(domain.getPaymentType()) ? domain.getPaymentType().ordinal() : PaymentTypeEnum.NONE.ordinal());
            dto.setNote(domain.getNote());
            dto.setPayDate(DateUtil.parseToString(domain.getPayDate()));
        }
        return dto;
    }
}
