package com.ikservices.oficinamecanica.budgets.items.services.infra.persistence;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import javax.persistence.*;

import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.services.infra.persistence.ServiceEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "BUDG_ITEM_SERV")
public class BudgetItemServiceEntity {
	@EmbeddedId
	private BudgetItemServiceEntityId id;
	
	@ManyToOne
	@JoinColumn(name = "BUDGETID", referencedColumnName = "BUDGETID", updatable = false, insertable = false)
	private BudgetEntity budgetEntity;
	
	@Column(name = "SERVICEID_PART1")
	private Long serviceId;
	@Column(name = "SERVICEID_PART2")
	private Long workshopId;
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "SERVICEID_PART1", referencedColumnName = "SERVICEID", updatable = false, insertable = false),
			@JoinColumn(name = "SERVICEID_PART2", referencedColumnName = "WORKSHOPID", updatable = false, insertable = false)
	})
	private ServiceEntity serviceEntity;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Column(name = "COST")
	private BigDecimal cost;
	
	@Column(name = "DISCOUNT")
	private BigDecimal discount;
	
	public void update(BudgetItemServiceEntity entity) {
		if(Objects.nonNull(entity.getServiceId())) {
			this.serviceId = entity.getServiceId();
			this.serviceEntity.getId().setId(entity.getServiceId());
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
	}

	public BigDecimal getTotal() {
		return NumberUtil.calcPrice(quantity, cost, discount).setScale(2, RoundingMode.HALF_UP);
	}
}
