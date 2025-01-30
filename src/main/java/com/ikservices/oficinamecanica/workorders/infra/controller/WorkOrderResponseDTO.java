package com.ikservices.oficinamecanica.workorders.infra.controller;

import java.math.BigDecimal;
import java.util.List;

import com.ikservices.oficinamecanica.budgets.infra.controller.BudgetDTO;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.PayFormEnum;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.installments.infra.dto.WorkOrderInstallmentsDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WorkOrderResponseDTO {
	//TODO The id must be a compound key similar to DOMAIN and ENTITY
	private WorkOrderId workOrderId;
	private BudgetDTO budgetDTO;
	private String openingDate;
	private Long km;
	private Integer WOStatus;
	private BigDecimal amount;
	private Integer payForm;
	private Integer payQty;
	private boolean paid;
	private List<WorkOrderInstallmentsDTO> installments;
	//TODO Add here the WorkOrderInstallmentsDTO list as a field 
}
