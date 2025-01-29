package com.ikservices.oficinamecanica.workorders.installments.domain;

import java.io.Serializable;

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
public class WorkOrderInstallmentId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long number;
	private Long workOrderId;
	private Long budgetId;
	private PayTypeEnum installmentType;

}
