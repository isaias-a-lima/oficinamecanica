package com.ikservices.oficinamecanica.receivables.infra;

import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.generics.IKConverter;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;
import com.ikservices.oficinamecanica.receivables.domain.ReceivableId;
import com.ikservices.oficinamecanica.receivables.domain.ReceivableTypeEnum;
import com.ikservices.oficinamecanica.receivables.infra.controller.ReceivableDTO;
import com.ikservices.oficinamecanica.receivables.infra.persistence.ReceivableEntity;
import com.ikservices.oficinamecanica.receivables.infra.persistence.ReceivableEntityId;

import java.util.Objects;

public class ReceivableConverter extends IKConverter<ReceivableDTO, Receivable, ReceivableEntity, ReceivableDTO> {
    @Override
    public Receivable parseRequestToDomain(ReceivableDTO request) {
        if (Objects.isNull(request)) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.NULL_OBJECT_MESSAGE));
        }

        Receivable receivable = new Receivable();
        receivable.setId(new ReceivableId(request.getReceivableId(), request.getWorkshopId()));
        receivable.setDueDate(request.getDueDate());
        receivable.setPaymentValue(NumberUtil.parseBigDecimal(request.getPaymentValue()));
        receivable.setReceivableType(ReceivableTypeEnum.findByIndex(request.getReceivableType()));
        receivable.setPaymentDate(request.getPaymentDate());
        receivable.setOutsourcePayment(request.getOutsourcePayment());
        receivable.setSupplierId(request.getSupplierId());
        receivable.setNote(request.getNote());

        return receivable;
    }

    @Override
    public ReceivableEntity parseDomainToEntity(Receivable domain) {
        if (Objects.isNull(domain)) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.NULL_OBJECT_MESSAGE));
        }

        ReceivableEntity entity = new ReceivableEntity();
        entity.setId(new ReceivableEntityId(domain.getId().getOrderNumber(), domain.getId().getWorkshopId()));
        entity.setDueDate(domain.getDueDate());
        entity.setPayValue(domain.getPaymentValue());
        entity.setPaymentType(domain.getReceivableType());
        entity.setPayDate(domain.getPaymentDate());
        entity.setIsOutsourcePay(domain.getOutsourcePayment());
        entity.setSupplierId(domain.getSupplierId());
        entity.setNote(domain.getNote());

        return entity;
    }

    @Override
    public Receivable parseEntityToDomain(ReceivableEntity entity) {
        if (Objects.isNull(entity)) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.NULL_OBJECT_MESSAGE));
        }

        Receivable receivable = new Receivable();
        receivable.setId(new ReceivableId(entity.getId().getOrderNumber(), entity.getId().getWorkshopId()));
        receivable.setDueDate(entity.getDueDate());
        receivable.setPaymentValue(entity.getPayValue());
        receivable.setReceivableType(entity.getPaymentType());
        receivable.setPaymentDate(entity.getPayDate());
        receivable.setOutsourcePayment(entity.getIsOutsourcePay());
        receivable.setSupplierId(entity.getSupplierId());
        receivable.setNote(entity.getNote());

        return receivable;
    }

    @Override
    public ReceivableDTO parseDomainToResponse(Receivable domain) {
        if (Objects.isNull(domain)) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.NULL_OBJECT_MESSAGE));
        }

        ReceivableDTO dto = new ReceivableDTO();
        dto.setReceivableId(domain.getId().getOrderNumber());
        dto.setWorkshopId(domain.getId().getWorkshopId());
        dto.setDueDate(domain.getDueDate());
        dto.setPaymentValue(NumberUtil.parseStringMoney(domain.getPaymentValue()));
        dto.setReceivableType(domain.getReceivableType().ordinal());
        dto.setPaymentDate(domain.getPaymentDate());
        dto.setOutsourcePayment(domain.getOutsourcePayment());
        dto.setSupplierId(domain.getSupplierId());
        dto.setNote(domain.getNote());

        return dto;
    }
}
