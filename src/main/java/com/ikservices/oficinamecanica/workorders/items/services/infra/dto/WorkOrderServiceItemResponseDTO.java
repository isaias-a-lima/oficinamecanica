package com.ikservices.oficinamecanica.workorders.items.services.infra.dto;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.services.infra.controller.ServiceDTO;

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
public class WorkOrderServiceItemResponseDTO {
	private Long itemId;
	private Long workOrderId;
	private Long budgetId;
	private ServiceDTO serviceDTO;
	private Integer quantity;
	private BigDecimal itemValue;
	private BigDecimal discount; 
}
