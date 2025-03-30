package com.ikservices.oficinamecanica.workorders.items.parts.infra;

import com.ikservices.oficinamecanica.commons.exception.IKException;
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
import com.ikservices.oficinamecanica.workorders.items.services.infra.dto.WorkOrderServiceItemRequestDTO;
import com.ikservices.oficinamecanica.workorders.items.services.infra.dto.WorkOrderServiceItemResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
            entity.setPartId(domain.getPart().getPartId().getId());
            entity.setWorkshopId(domain.getPart().getPartId().getWorkshopId());
            entity.setPart(partConverter.parseEntity(domain.getPart()));
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
            domain.setPart(partConverter.parsePart(entity.getPart()));
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

    public List<WorkOrderPartItemRequestDTO> createRequestList(List<WorkOrderPartItemResponseDTO> responseList) {
        List<WorkOrderPartItemRequestDTO> requestList = new ArrayList<>();
        if (Objects.nonNull(responseList) && !responseList.isEmpty()) {
            for (WorkOrderPartItemResponseDTO response : responseList) {
                requestList.add(this.createRequest(response));
            }
        }
        return requestList;
    }

    private WorkOrderPartItemRequestDTO createRequest(WorkOrderPartItemResponseDTO response) {
        if (Objects.isNull(response)) {
            throw new IKException("Null object.");
        }
        WorkOrderPartItemRequestDTO request = new WorkOrderPartItemRequestDTO();
        request.setItemId(response.getItemId());
        request.setWorkOrderId(response.getWorkOrderId());
        request.setBudgetId(response.getBudgetId());
        request.setPartId(response.getPart().getPartId());
        request.setWorkshopId(response.getPart().getWorkshopId());
        request.setQuantity(response.getQuantity());
        request.setItemValue(NumberUtil.parseBigDecimal(response.getItemValue()));
        request.setDiscount(NumberUtil.parseBigDecimal(response.getDiscount()));
        return request;
    }
}
