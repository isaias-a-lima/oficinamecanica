package com.ikservices.oficinamecanica.workorders.infra.controller;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.budgets.infra.controller.BudgetDTO;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.PayFormEnum;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;

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
	private Long workOrderId;
	private BudgetDTO budgetDTO;
	private String openingDate;
	private Long km;
	private Integer WOStatus;
	private BigDecimal amount;
	private Integer payForm;
	private Integer payQty;
	private boolean paid;
	
	//TODO Add here the WorkOrderInstallmentsDTO list as a field 
}
