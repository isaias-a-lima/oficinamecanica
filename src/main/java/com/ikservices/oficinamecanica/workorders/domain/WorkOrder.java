package com.ikservices.oficinamecanica.workorders.domain;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.PayFormEnum;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.installments.domain.WorkOrderInstallment;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItem;
import com.ikservices.oficinamecanica.workorders.items.services.domain.WorkOrderServiceItem;
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
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WorkOrder {
	private WorkOrderId id;
	/**
	 * First Map key: Long vehicleId
	 * Second Map key: Long budgetId
	 */
	private Map<Long, Map<Long, Budget>> budget;
	private LocalDate openingDate;
	private Long km;
	private WorkOrderStatusEnum workOrderStatus;
	private BigDecimal amount;
	private Integer payQty;
	private List<WorkOrderInstallment> installments;
	private Boolean paid;
	private List<WorkOrderServiceItem> serviceItems;
	private List<WorkOrderPartItem> partItems;

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
}