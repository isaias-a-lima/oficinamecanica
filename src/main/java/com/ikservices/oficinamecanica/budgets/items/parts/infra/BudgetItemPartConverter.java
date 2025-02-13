package com.ikservices.oficinamecanica.budgets.items.parts.infra;

import com.ikservices.oficinamecanica.budgets.items.parts.domain.BudgetItemPart;
import com.ikservices.oficinamecanica.budgets.items.parts.domain.BudgetItemPartId;
import com.ikservices.oficinamecanica.budgets.items.parts.infra.dto.BudgetItemPartRequestDTO;
import com.ikservices.oficinamecanica.budgets.items.parts.infra.dto.BudgetItemPartResponseDTO;
import com.ikservices.oficinamecanica.budgets.items.parts.infra.persistence.BudgetItemPartEntity;
import com.ikservices.oficinamecanica.budgets.items.parts.infra.persistence.BudgetItemPartEntityId;
import com.ikservices.oficinamecanica.commons.generics.IKConverter;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.parts.domain.Part;
import com.ikservices.oficinamecanica.parts.domain.PartId;
import com.ikservices.oficinamecanica.parts.infra.PartConverter;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartEntity;
import com.ikservices.oficinamecanica.workorders.items.parts.infra.persistence.WorkOrderPartItemEntity;
import com.ikservices.oficinamecanica.workorders.items.parts.infra.persistence.WorkOrderPartItemEntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BudgetItemPartConverter extends IKConverter<BudgetItemPartRequestDTO, BudgetItemPart, BudgetItemPartEntity, BudgetItemPartResponseDTO> {

    @Autowired
    @Lazy
    private PartConverter partConverter;

    @Override
    public BudgetItemPart parseRequestToDomain(BudgetItemPartRequestDTO request) {
        BudgetItemPart domain = new BudgetItemPart();

        domain.setId(
                new BudgetItemPartId(
                        Objects.nonNull(request.getItemId()) ? request.getItemId() : null,
                        Objects.nonNull(request.getBudgetId()) ? request.getBudgetId() : null
                )
        );
        domain.setPart(new Part(new PartId(request.getPartId(), request.getWorkshopId())));
        domain.setQuantity(request.getQuantity());
        domain.getPart().setCost(request.getCost());
        domain.setDiscount(request.getDiscount());

        return domain;
    }

    @Override
    public BudgetItemPartEntity parseDomainToEntity(BudgetItemPart domain) {
        BudgetItemPartEntity entity = new BudgetItemPartEntity();
        entity.setId(new BudgetItemPartEntityId(domain.getId().getItemId(), domain.getId().getBudgetId()));
        PartEntity part = partConverter.parseEntity(domain.getPart());
        entity.setPartId(part.getId().getId());
        entity.setWorkshopId(part.getId().getWorkshopId());
        entity.setPart(part);
        entity.setQuantity(domain.getQuantity());
        entity.setCost(domain.getPart().getCost());
        entity.setDiscount(domain.getDiscount());
        return entity;
    }

    @Override
    public BudgetItemPart parseEntityToDomain(BudgetItemPartEntity entity) {
        BudgetItemPart domain = new BudgetItemPart();
        domain.setId(new BudgetItemPartId(entity.getId().getItemId(), entity.getId().getBudgetId()));
        Part part = partConverter.parsePart(entity.getPart());
        domain.setPart(part);
        domain.setQuantity(entity.getQuantity());
        domain.getPart().setCost(part.getCost());
        domain.setDiscount(entity.getDiscount());
        return domain;
    }

    @Override
    public BudgetItemPartResponseDTO parseDomainToResponse(BudgetItemPart domain) {
        BudgetItemPartResponseDTO dto = new BudgetItemPartResponseDTO();
        dto.setItemId(domain.getId().getItemId());
        dto.setBudgetId(domain.getId().getBudgetId());
        dto.setPart(partConverter.parseDTO(domain.getPart()));
        dto.setQuantity(domain.getQuantity());
        dto.setCost(NumberUtil.parseStringMoney(domain.getPart().getCost()));
        dto.setDiscount(NumberUtil.parseStringPercent(domain.getDiscount()));
        dto.setAmount(NumberUtil.parseStringMoney(domain.getTotal()));
        return dto;
    }

    public WorkOrderPartItemEntity parseToWorkOrderPartItemEntity(BudgetItemPartEntity source, Long workOrderId) {
        WorkOrderPartItemEntity target = new WorkOrderPartItemEntity();
        target.setId(new WorkOrderPartItemEntityId(source.getId().getItemId(), workOrderId, source.getId().getBudgetId()));
        target.setPartId(source.getPartId());
        target.setWorkshopId(source.getWorkshopId());
        target.setPart(source.getPart());
        target.setQuantity(source.getQuantity());
        target.setItemValue(source.getCost());
        target.setDiscount(source.getDiscount());
        return target;
    }

    public List<WorkOrderPartItemEntity> parseToWorkOrderPartItemEntityList(List<BudgetItemPartEntity> sourceList, Long workOrderId) {
        List<WorkOrderPartItemEntity> targetList = new ArrayList<>();
        if (Objects.nonNull(sourceList) && !sourceList.isEmpty()) {
            for (BudgetItemPartEntity source : sourceList) {
                targetList.add(this.parseToWorkOrderPartItemEntity(source, workOrderId));
            }
        }
        return targetList;
    }
}
