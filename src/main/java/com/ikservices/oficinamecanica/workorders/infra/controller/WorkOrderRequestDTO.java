package com.ikservices.oficinamecanica.workorders.infra.controller;

import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.items.parts.infra.dto.WorkOrderPartItemRequestDTO;
import com.ikservices.oficinamecanica.workorders.items.services.infra.dto.WorkOrderServiceItemRequestDTO;
import com.ikservices.oficinamecanica.workorders.payments.infra.dto.PaymentDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WorkOrderRequestDTO {
	private Long workOrderId;
	private Long budgetId;
	private String openingDate;
	private Long km;
	private WorkOrderStatusEnum workOrderStatus;
	private BigDecimal amount;
	private BigDecimal discount;
	private Integer payQty;
	private boolean paid;
	private List<WorkOrderServiceItemRequestDTO> serviceItems;
	private List<WorkOrderPartItemRequestDTO> partItems;
	private List<PaymentDTO> payments;
}
