package com.ikservices.oficinamecanica.workorders.installments.infra.dto;

import java.io.Serializable;

import com.ikservices.oficinamecanica.workorders.installments.domain.PayTypeEnum;
import com.ikservices.oficinamecanica.workorders.installments.domain.WorkOrderInstallmentId;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WorkOrderInstallmentIdDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long number;
	private Long workOrderId;
	private Long budgetId;
	private PayTypeEnum installmentType;
}
