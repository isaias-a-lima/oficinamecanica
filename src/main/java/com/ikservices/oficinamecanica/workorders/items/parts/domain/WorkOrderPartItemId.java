package com.ikservices.oficinamecanica.workorders.items.parts.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WorkOrderPartItemId implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long itemId;
    private Long workOrderId;
    private Long budgetId;
}
