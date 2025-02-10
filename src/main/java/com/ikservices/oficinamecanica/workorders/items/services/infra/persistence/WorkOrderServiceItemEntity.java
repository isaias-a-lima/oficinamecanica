package com.ikservices.oficinamecanica.workorders.items.services.infra.persistence;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.services.infra.persistence.ServiceEntity;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "WO_SERVICE_ITEMS")
public class WorkOrderServiceItemEntity {
	@EmbeddedId
	private WorkOrderServiceItemEntityId id;
	
	@ManyToOne
	@JoinColumn(name = "WORKORDERID", referencedColumnName = "WORKORDERID", updatable = false, insertable = false)
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
		if(Objects.nonNull(entity)) {
			this.quantity = entity.getQuantity();
		}
		if(Objects.nonNull(entity)) {
			this.itemValue = entity.getItemValue();
		}
		if(Objects.nonNull(entity)) {
			this.discount = entity.getDiscount();
		}
	}
	
	public BigDecimal getTotal() {
		return NumberUtil.calcPrice(quantity, itemValue, discount).setScale(2, RoundingMode.HALF_UP);
	}
}
