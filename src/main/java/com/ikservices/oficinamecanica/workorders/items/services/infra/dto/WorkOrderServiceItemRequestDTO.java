package com.ikservices.oficinamecanica.workorders.items.services.infra.dto;

import java.math.BigDecimal;

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
public class WorkOrderServiceItemRequestDTO {
	private Long itemId;
	private Long workOrderId;
	private Long budgetId;
	private Long serviceId;
	private Long workshopId;
	private Integer quantity;
	private BigDecimal itemValue;
	private BigDecimal discount;
}
