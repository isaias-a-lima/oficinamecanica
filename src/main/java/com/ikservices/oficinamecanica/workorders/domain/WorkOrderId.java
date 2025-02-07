package com.ikservices.oficinamecanica.workorders.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class WorkOrderId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long workOrderId;
	private Long budgetId;
}
