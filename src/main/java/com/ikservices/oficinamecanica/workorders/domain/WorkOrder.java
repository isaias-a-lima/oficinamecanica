package com.ikservices.oficinamecanica.workorders.domain;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItem;
import com.ikservices.oficinamecanica.workorders.items.services.domain.WorkOrderServiceItem;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public class WorkOrder {
	@Setter
	private WorkOrderId id;
	/**
	 * First Map key: Long vehicleId
	 * Second Map key: Long budgetId
	 */
	@Setter
	private Map<Long, Map<Long, Budget>> budget;
	@Setter
	private LocalDate openingDate;
	@Setter
	private Long km;
	@Setter
	private WorkOrderStatusEnum workOrderStatus;

	private BigDecimal amount;
	@Setter
	private BigDecimal discount;
	@Setter
	private Boolean isFinalValueRounded;
	@Setter
	private Integer payQty;
	@Setter
	private Boolean paid;
	private List<WorkOrderServiceItem> serviceItems;
	private List<WorkOrderPartItem> partItems;
	@Setter
    private List<Payment> payments;

	public WorkOrder() {
		this.isFinalValueRounded = false;
	}

	public void setServiceItems(List<WorkOrderServiceItem> serviceItems) {
		this.serviceItems = serviceItems;
		this.updateAmount();
	}

	public void setPartItems(List<WorkOrderPartItem> partItems) {
		this.partItems = partItems;
		this.updateAmount();
	}

	public String sumPartItems() {
		BigDecimal sum = BigDecimal.ZERO;
		if (Objects.nonNull(partItems) && !partItems.isEmpty()) {
			for (WorkOrderPartItem partItem : partItems) {
				sum = sum.add(partItem.getTotal());
			}
		}
		return NumberUtil.parseStringLocalMoney(sum);
	}

	public String sumServiceItems() {
		BigDecimal sum = BigDecimal.ZERO;
		if (Objects.nonNull(serviceItems) && !serviceItems.isEmpty()) {
			for (WorkOrderServiceItem serviceItem : serviceItems) {
				sum = sum.add(serviceItem.getTotal());
			}
		}
		return NumberUtil.parseStringLocalMoney(sum);
	}

	private void updateAmount() {
		BigDecimal sum = BigDecimal.ZERO;
		if (Objects.nonNull(serviceItems) && !serviceItems.isEmpty()) {
			for (WorkOrderServiceItem serviceItem : serviceItems) {
				sum = sum.add(serviceItem.getTotal());
			}
		}
		if (Objects.nonNull(partItems) && !partItems.isEmpty()) {
			for (WorkOrderPartItem partItem : partItems) {
				sum = sum.add(partItem.getTotal());
			}
		}
		amount = sum;
	}

	public BigDecimal getFinalValue() {
		this.discount = Objects.isNull(this.discount) ? BigDecimal.ZERO : this.discount;
		BigDecimal discountValue = this.discount.divide(BigDecimal.valueOf(100.0));
		int scale = this.isFinalValueRounded ? 0 : 2;
		BigDecimal value = this.amount.subtract(this.amount.multiply(discountValue)).setScale(scale, RoundingMode.HALF_UP);
		if (this.discount.compareTo(new BigDecimal("100")) > 0) {
			throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), "Excessive discount"));
		}
		return value.setScale(2, RoundingMode.HALF_UP);
	}

	public static BigDecimal calculateFinalValue(BigDecimal discount, BigDecimal amount, boolean isFinalValueRounded) {
		BigDecimal discountValue = discount.divide(BigDecimal.valueOf(100.0));
		int scale = isFinalValueRounded ? 0 : 2;
		BigDecimal value = amount.subtract(amount.multiply(discountValue)).setScale(scale, RoundingMode.HALF_UP);
		if (discount.compareTo(new BigDecimal("100")) > 0) {
			throw new IKException(new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), "Excessive discount"));
		}
		return value.setScale(2, RoundingMode.HALF_UP);
	}
}