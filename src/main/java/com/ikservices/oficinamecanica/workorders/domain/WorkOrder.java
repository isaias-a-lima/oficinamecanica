package com.ikservices.oficinamecanica.workorders.domain;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItem;
import com.ikservices.oficinamecanica.workorders.items.services.domain.WorkOrderServiceItem;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
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
	private Integer payQty;
	@Setter
	private Boolean paid;
	private List<WorkOrderServiceItem> serviceItems;
	private List<WorkOrderPartItem> partItems;
	@Setter
    private List<Payment> payments;

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
		return NumberUtil.parseStringMoney(sum);
	}

	public String sumServiceItems() {
		BigDecimal sum = BigDecimal.ZERO;
		if (Objects.nonNull(serviceItems) && !serviceItems.isEmpty()) {
			for (WorkOrderServiceItem serviceItem : serviceItems) {
				sum = sum.add(serviceItem.getTotal());
			}
		}
		return NumberUtil.parseStringMoney(sum);
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
}