package com.ikservices.oficinamecanica.workorders.items.services.infra.persistence;

import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.services.infra.persistence.ServiceEntity;
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
@Table(name = "WO_SERVICE_ITEMS")
public class WorkOrderServiceItemEntity {
	@EmbeddedId
	private WorkOrderServiceItemEntityId id;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "WORKORDER_ID", referencedColumnName = "WORKORDERID", updatable = false, insertable = false),
		@JoinColumn(name = "BUDGET_ID",  referencedColumnName = "BUDGETID", updatable = false, insertable = false)
	})
	private WorkOrderEntity workOrder;
	
	@Column(name = "SERVICE_ID")
	private Long serviceId;
	
	@Column(name = "WORKSHOP_ID")
	private Long workshopId;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERVICEID", updatable = false, insertable = false),
		@JoinColumn(name = "WORKSHOP_ID", referencedColumnName = "WORKSHOPID", updatable = false, insertable = false)
	})
	private ServiceEntity service;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Column(name = "ITEMVALUE")
	private BigDecimal itemValue;
	
	@Column(name = "DISCOUNT")
	private BigDecimal discount;
	
	public void update(WorkOrderServiceItemEntity entity) {
		if(Objects.nonNull(entity.getServiceId())) {
			this.serviceId = entity.getServiceId();
			this.service.getId().setId(entity.getServiceId());
		}
		if(Objects.nonNull(entity.getQuantity())) {
			this.quantity = entity.getQuantity();
		}
		if(Objects.nonNull(entity.getItemValue())) {
			this.itemValue = entity.getItemValue();
		}
		if(Objects.nonNull(entity.getDiscount())) {
			this.discount = entity.getDiscount();
		}
	}
	
	public BigDecimal getTotal() {
		return NumberUtil.calcPrice(quantity, itemValue, discount).setScale(2, RoundingMode.HALF_UP);
	}
}
