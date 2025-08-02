package com.ikservices.oficinamecanica.budgets.items.parts.infra.persistence;

import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntity;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "BUDGET_ITEM_PART")
public class BudgetItemPartEntity {

    @EmbeddedId
    private BudgetItemPartEntityId id;

    @ManyToOne
    @JoinColumn(name = "BUDGETID", referencedColumnName = "BUDGETID", updatable = false, insertable = false)
    private BudgetEntity budgetEntity;

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

    @Column(name = "COST")
    private BigDecimal cost;

    @Column(name = "SERVICE_COST")
    private BigDecimal serviceCost;

    @Column(name = "DISCOUNT")
    private BigDecimal discount;

    public void update(BudgetItemPartEntity entity) {
        if(Objects.nonNull(entity.getPartId())) {
            this.partId = entity.getPartId();
            this.part.getId().setId(entity.getPartId());
        }
        if(Objects.nonNull(entity.getQuantity())) {
            this.quantity = entity.getQuantity();
        }
        if(Objects.nonNull(entity.getCost())) {
            this.cost = entity.getCost();
        }
        if(Objects.nonNull(entity.getDiscount())) {
            this.discount = entity.getDiscount();
        }
        if(Objects.nonNull(entity.getServiceCost())) {
            this.serviceCost = entity.getServiceCost();
        }
    }

    public BigDecimal getTotal() {
        return NumberUtil.calcPrice(quantity, cost, serviceCost, discount);
    }
}
