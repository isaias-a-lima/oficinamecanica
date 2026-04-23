package com.ikservices.oficinamecanica.workorders.items.parts.infra.persistence;

import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartEntity;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

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
    @JoinColumns({
            @JoinColumn(name = "WORKORDER_ID", referencedColumnName = "WORKORDERID", updatable = false, insertable = false),
            @JoinColumn(name = "BUDGET_ID", referencedColumnName = "BUDGETID", updatable = false, insertable = false)
    })
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

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "ITEMVALUE")
    private BigDecimal itemValue;

    @Column(name = "DISCOUNTVALUE")
    private BigDecimal discountValue;

    @Column(name = "DISCOUNT")
    private BigDecimal discount;

    @Column(name = "SERVICE_COST")
    private BigDecimal serviceCost;

    public void update(WorkOrderPartItemEntity newItem) {
        if (Objects.nonNull(newItem.getPartId())) {
            this.partId = newItem.getPartId();
            this.part.getId().setId(newItem.getPartId());
        }

        this.quantity = Objects.isNull(newItem.getQuantity()) ? 0 : newItem.getQuantity();
        this.serviceCost = Objects.isNull(newItem.getServiceCost()) ? BigDecimal.ZERO : newItem.getServiceCost();
        this.itemValue = Objects.isNull(newItem.getItemValue()) ? BigDecimal.ZERO : newItem.getItemValue();
        this.discountValue = Objects.isNull(newItem.getDiscountValue()) ? BigDecimal.ZERO : newItem.getDiscountValue();
        //This field has been deprecated and will be replaced for the discountValue field
        this.discount = Objects.isNull(newItem.getDiscount()) ? BigDecimal.ZERO : newItem.getDiscount();
    }

    public BigDecimal getTotal() {
        return NumberUtil.calcPartPrice(quantity, itemValue, serviceCost, discountValue, discount);
    }
}
