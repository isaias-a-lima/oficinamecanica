package com.ikservices.oficinamecanica.workorders.installments.infra.dto;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.workorders.installments.domain.PayTypeEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"number", "workOrderId", "budgetId", "installmentType"})
public class WorkOrderInstallmentsDTO {
	private Long number;
	private Long workOrderId;
	private Long budgetId;
	private Integer installmentType;
	private String dueDate;
	private String payValue;
	private String payDate;
	private String note;
}
