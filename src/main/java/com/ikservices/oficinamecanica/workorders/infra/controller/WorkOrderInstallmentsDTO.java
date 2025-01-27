package com.ikservices.oficinamecanica.workorders.infra.controller;

import java.math.BigDecimal;
import java.util.List;

import com.ikservices.oficinamecanica.workorders.domain.WorkOrderInstallment;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WorkOrderInstallmentsDTO {
	private Long number;
	private Long workOrderId;
	private Long budgetId;
	private String installmentType;
	private String dueDate;
	private BigDecimal payValue;
	private String payDate;
	private String note;
}
