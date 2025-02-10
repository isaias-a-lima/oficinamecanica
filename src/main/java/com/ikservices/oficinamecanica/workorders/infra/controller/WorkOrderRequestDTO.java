package com.ikservices.oficinamecanica.workorders.infra.controller;

import java.math.BigDecimal;
import java.util.List;

import com.ikservices.oficinamecanica.workorders.domain.enumarates.PayFormEnum;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;

import com.ikservices.oficinamecanica.workorders.items.parts.infra.dto.WorkOrderPartItemRequestDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WorkOrderRequestDTO {
	private Long workOrderId;
	private Long budgetId;
	private String openingDate;
	private Long km;
	private WorkOrderStatusEnum WOStatus;
	private BigDecimal amount;
	private PayFormEnum payForm;
	private Integer payQty;
	private boolean paid;
	private List<WorkOrderPartItemRequestDTO> partItems;
}
