package com.ikservices.oficinamecanica.receivables.infra.gateways;

import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.receivables.application.ReceivableStatusEnum;
import com.ikservices.oficinamecanica.receivables.application.gatways.ReceivableRepository;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;
import com.ikservices.oficinamecanica.receivables.domain.ReceivableId;
import com.ikservices.oficinamecanica.receivables.infra.ReceivableConverter;
import com.ikservices.oficinamecanica.receivables.infra.persistence.ReceivableEntity;
import com.ikservices.oficinamecanica.receivables.infra.persistence.ReceivableEntityId;
import com.ikservices.oficinamecanica.receivables.infra.persistence.ReceivableRepositoryJPA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ReceivableRepositoryImpl implements ReceivableRepository {

    private final ReceivableRepositoryJPA jpa;
    private final ReceivableConverter converter;

    public ReceivableRepositoryImpl(ReceivableRepositoryJPA jpa, ReceivableConverter converter) {
        this.jpa = jpa;
        this.converter = converter;
    }

    @Override
    public Receivable saveReceivable(Receivable receivable) {
        if (Objects.isNull(receivable)) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.NULL_OBJECT_MESSAGE));
        }

        Long nextId = jpa.getNextId(receivable.getId().getWorkshopId());
        ReceivableEntity receivableEntity = converter.parseDomainToEntity(receivable);
        receivableEntity.getId().setOrderNumber(nextId);

        ReceivableEntity entity = jpa.save(receivableEntity);

        return converter.parseEntityToDomain(entity);
    }

    @Override
    public Receivable getReceivable(ReceivableId id) {
        if (Objects.isNull(id)) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.NULL_OBJECT_MESSAGE));
        }

        Optional<ReceivableEntity> optional = jpa.findById(new ReceivableEntityId(id));

        if (!optional.isPresent()) {
            throw new IKException(new IKMessage(IKConstants.IK_HTTP_NOT_FOUND_CODE, IKMessageType.WARNING.getCode(), IKConstants.RESOURCE_NOT_FOUND_MESSAGE));
        }

        ReceivableEntity entity = optional.get();

        return converter.parseEntityToDomain(entity);
    }

    @Override
    public Receivable updateReceivable(Receivable receivable) {
        if (Objects.isNull(receivable) || Objects.isNull(receivable.getId())) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.NULL_OBJECT_MESSAGE));
        }

        Optional<ReceivableEntity> optional = jpa.findById(new ReceivableEntityId(receivable.getId()));

        if (!optional.isPresent()) {
            throw new IKException(new IKMessage(IKConstants.IK_HTTP_NOT_FOUND_CODE, IKMessageType.WARNING.getCode(), IKConstants.RESOURCE_NOT_FOUND_MESSAGE));
        }

        ReceivableEntity entity = optional.get();

        entity.update(converter.parseDomainToEntity(receivable));

        return converter.parseEntityToDomain(entity);
    }

    @Override
    public List<Receivable> listReceivables(Long workshopId, LocalDate startDate, LocalDate endDate, ReceivableStatusEnum status) {
        if (Objects.isNull(workshopId) || Objects.isNull(startDate) || Objects.isNull(endDate) || Objects.isNull(status)) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.NULL_OBJECT_MESSAGE));
        }
        List<ReceivableEntity> entityList = jpa.listReceivableByDuePeriod(workshopId, startDate, endDate, status.toString());
        return new ArrayList<>(converter.parseEntityToDomainList(entityList));
    }

    @Override
    public List<Receivable> listOutsourceReceivables(Long workshopId, LocalDate startDate, LocalDate endDate, ReceivableStatusEnum status) {
        if (Objects.isNull(workshopId) || Objects.isNull(startDate) || Objects.isNull(endDate) || Objects.isNull(status)) {
            throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.NULL_OBJECT_MESSAGE));
        }
        List<ReceivableEntity> entityList = jpa.listOutsourceReceivables(workshopId, startDate, endDate, status.toString());
        return new ArrayList<>(converter.parseEntityToDomainList(entityList));
    }

    @Override
    public List<Receivable> listReceivableBySupplierAndPayDate(Long workshopId, Integer supplierId, LocalDate startDate, LocalDate endDate) {
        return converter.parseEntityToDomainList(jpa.listReceivableBySupplierAndPayDate(workshopId, supplierId, startDate, endDate));
    }
}
