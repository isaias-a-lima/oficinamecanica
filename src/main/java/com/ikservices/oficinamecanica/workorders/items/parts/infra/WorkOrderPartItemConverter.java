package com.ikservices.oficinamecanica.workorders.items.parts.infra;

import com.ikservices.oficinamecanica.commons.generics.IKConverter;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.parts.domain.Part;
import com.ikservices.oficinamecanica.parts.domain.PartId;
import com.ikservices.oficinamecanica.parts.infra.PartConverter;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartEntity;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItem;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItemId;
import com.ikservices.oficinamecanica.workorders.items.parts.infra.dto.WorkOrderPartItemRequestDTO;
import com.ikservices.oficinamecanica.workorders.items.parts.infra.dto.WorkOrderPartItemResponseDTO;
import com.ikservices.oficinamecanica.workorders.items.parts.infra.persistence.WorkOrderPartItemEntity;
import com.ikservices.oficinamecanica.workorders.items.parts.infra.persistence.WorkOrderPartItemEntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WorkOrderPartItemConverter extends IKConverter<WorkOrderPartItemRequestDTO, WorkOrderPartItem, WorkOrderPartItemEntity, WorkOrderPartItemResponseDTO> {

    @Autowired
    @Lazy
    private PartConverter partConverter;

    @Override
    public WorkOrderPartItem parseRequestToDomain(WorkOrderPartItemRequestDTO request) {
        WorkOrderPartItem domain = null;

        if (Objects.nonNull(request)) {
            domain = new WorkOrderPartItem();
            domain.setId(new WorkOrderPartItemId(request.getItemId(), request.getWorkOrderId(), request.getBudgetId()));
            domain.setPart(new Part(new PartId(request.getPartId(), request.getWorkshopId())));
            domain.setQuantity(request.getQuantity());
            domain.setItemValue(request.getItemValue());
            domain.setDiscount(request.getDiscount());
        }

        return domain;
    }

    @Override
    public WorkOrderPartItemEntity parseDomainToEntity(WorkOrderPartItem domain) {
        WorkOrderPartItemEntity entity = null;

        if (Objects.nonNull(domain)) {
            entity = new WorkOrderPartItemEntity();
            entity.setId(new WorkOrderPartItemEntityId(domain.getId().getItemId(), domain.getId().getWorkOrderId(), domain.getId().getBudgetId()));
            PartEntity partEntity = partConverter.parseEntity(domain.getPart());
            entity.setPartId(partEntity.getId().getId());
            entity.setWorkshopId(partEntity.getId().getWorkshopId());
            entity.setPart(partEntity);
            entity.setQuantity(domain.getQuantity());
            entity.setItemValue(domain.getItemValue());
            entity.setDiscount(domain.getDiscount());
        }

        return entity;
    }

    @Override
    public WorkOrderPartItem parseEntityToDomain(WorkOrderPartItemEntity entity) {
        WorkOrderPartItem domain = null;

        if (Objects.nonNull(entity)) {
            domain = new WorkOrderPartItem();
            domain.setId(new WorkOrderPartItemId(entity.getId().getItemId(), entity.getId().getWorkOrderId(), entity.getId().getBudgetId()));
            domain.setPart(new Part(new PartId(entity.getPartId(), entity.getWorkshopId())));
            domain.setQuantity(entity.getQuantity());
            domain.setItemValue(entity.getItemValue());
            domain.setDiscount(entity.getDiscount());
        }

        return domain;
    }

    @Override
    public WorkOrderPartItemResponseDTO parseDomainToResponse(WorkOrderPartItem domain) {
        WorkOrderPartItemResponseDTO response = null;

        if (Objects.nonNull(domain)) {
            response = new WorkOrderPartItemResponseDTO();
            response.setItemId(domain.getId().getItemId());
            response.setWorkOrderId(domain.getId().getWorkOrderId());
            response.setBudgetId(domain.getId().getBudgetId());
            response.setPart(Objects.nonNull(domain.getPart()) ? partConverter.parseDTO(domain.getPart()): null);
            response.setQuantity(domain.getQuantity());
            response.setItemValue(NumberUtil.parseStringMoney(domain.getItemValue()));
            response.setDiscount(NumberUtil.parseStringPercent(domain.getDiscount()));
            response.setAmount(NumberUtil.parseStringMoney(domain.getTotal()));
        }

        return response;
    }
}
