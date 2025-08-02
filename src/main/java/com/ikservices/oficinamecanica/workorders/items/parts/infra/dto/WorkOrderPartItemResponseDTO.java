package com.ikservices.oficinamecanica.workorders.items.parts.infra.dto;

import com.ikservices.oficinamecanica.parts.infra.controller.PartDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"itemId", "workOrderId", "budgetId"})
public class WorkOrderPartItemResponseDTO {
    private Long itemId;
    private Long workOrderId;
    private Long budgetId;
    private PartDTO part;
    private Integer quantity;
    private String itemValue;
    private String serviceCost;
    private String partAndServiceValue;
    private String discount;
    private String amount;

}
