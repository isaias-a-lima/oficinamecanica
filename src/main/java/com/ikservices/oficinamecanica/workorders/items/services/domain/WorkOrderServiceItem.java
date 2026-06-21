package com.ikservices.oficinamecanica.workorders.items.services.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
	private BigDecimal discountValue;
	private BigDecimal discount;

    public BigDecimal getTotal() {
		return NumberUtil.calcServicePrice(this.quantity, this.itemValue, this.discountValue, this.discount);
    }

	public BigDecimal getRealDiscount() {
		if (null != this.discount && this.discount.compareTo(BigDecimal.ZERO) > 0) {
			return this.discount.divide(BigDecimal.valueOf(100)).multiply(service.getCost()).setScale(2, RoundingMode.HALF_UP);
		}
		return this.discountValue.setScale(2, RoundingMode.HALF_UP);
	}
}

