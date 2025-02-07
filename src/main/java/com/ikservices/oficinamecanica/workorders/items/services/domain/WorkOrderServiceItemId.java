package com.ikservices.oficinamecanica.workorders.items.services.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WorkOrderServiceItemId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Long itemId;
	Long workOrder;
	Long budgetId;
}
