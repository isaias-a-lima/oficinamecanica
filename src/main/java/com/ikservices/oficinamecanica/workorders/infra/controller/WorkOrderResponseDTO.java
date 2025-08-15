package com.ikservices.oficinamecanica.workorders.infra.controller;

import com.ikservices.oficinamecanica.budgets.infra.controller.BudgetDTO;
import com.ikservices.oficinamecanica.workorders.items.parts.infra.dto.WorkOrderPartItemResponseDTO;
import com.ikservices.oficinamecanica.workorders.items.services.infra.dto.WorkOrderServiceItemResponseDTO;
import com.ikservices.oficinamecanica.workorders.payments.infra.dto.PaymentDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"workOrderId","budgetId"})
public class WorkOrderResponseDTO {
	private Long workOrderId;
	private Long budgetId;
	private BudgetDTO budget;
	private String openingDate;
	private Long km;
	private Integer workOrderStatus;
	private String amount;
	private String discount;
	private String finalValue;
	private Integer payQty;
	private boolean paid;
	private List<WorkOrderServiceItemResponseDTO> serviceItems;
	private List<WorkOrderPartItemResponseDTO> partItems;
	private List<PaymentDTO> payments;
}
