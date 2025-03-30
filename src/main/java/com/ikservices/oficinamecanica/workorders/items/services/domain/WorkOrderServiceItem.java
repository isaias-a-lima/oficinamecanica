package com.ikservices.oficinamecanica.workorders.items.services.domain;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.services.domain.Service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WorkOrderServiceItem {
	private WorkOrderServiceItemId itemId;
	private Service service;
	private Integer quantity;
	private BigDecimal itemValue;
	private BigDecimal discount;

    public BigDecimal getTotal() {
		return NumberUtil.calcPrice(this.quantity, this.itemValue, this.discount);
    }
}

