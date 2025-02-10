package com.ikservices.oficinamecanica.workorders.infra.controller;

import java.math.BigDecimal;
import java.util.List;

import com.ikservices.oficinamecanica.budgets.infra.controller.BudgetDTO;
import com.ikservices.oficinamecanica.workorders.installments.infra.dto.WorkOrderInstallmentsDTO;

import com.ikservices.oficinamecanica.workorders.items.parts.infra.dto.WorkOrderPartItemResponseDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"workOrderId","budgetId"})
public class WorkOrderResponseDTO {
	private Long workOrderId;
	private Long budgetId;
	private BudgetDTO budgetDTO;
	private String openingDate;
	private Long km;
	private Integer WOStatus;
	private BigDecimal amount;
	private Integer payForm;
	private Integer payQty;
	private boolean paid;
	private List<WorkOrderInstallmentsDTO> installments;
	private List<WorkOrderPartItemResponseDTO> partItems;
}
