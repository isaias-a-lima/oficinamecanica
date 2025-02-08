package com.ikservices.oficinamecanica.workorders.items.parts.infra.persistence;

import com.ikservices.oficinamecanica.parts.infra.persistence.PartEntity;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "WO_PARTS_ITEMS")
public class WorkOrderPartItemEntity {
    @EmbeddedId
    private WorkOrderPartItemEntityId id;

    @ManyToOne
    @JoinColumn(name = "WORKORDERID", referencedColumnName = "WORKORDERID", updatable = false, insertable = false)
    private WorkOrderEntity workOrder;

    @Column(name = "PART_ID")
    private Long partId;
    @Column(name = "WORKSHOP_ID")
    private Long workshopId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PART_ID", referencedColumnName = "PARTID", updatable = false, insertable = false),
            @JoinColumn(name = "WORKSHOP_ID", referencedColumnName = "WORKSHOPID", updatable = false, insertable = false)
    })
    private PartEntity part;

    @Column(name = "ITEMVALUE")
    private BigDecimal itemValue;

    @Column(name = "DISCOUNT")
    private BigDecimal discount;
}
