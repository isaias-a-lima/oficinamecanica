package com.ikservices.oficinamecanica.workorders.items.parts.infra.persistence;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class WorkOrderPartItemEntityId implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "ITEMID")
    private Long itemId;
    @Column(name = "WORKORDERID")
    private Long workOrderId;
    @Column(name = "BUDGETID")
    private Long budgetId;
}
