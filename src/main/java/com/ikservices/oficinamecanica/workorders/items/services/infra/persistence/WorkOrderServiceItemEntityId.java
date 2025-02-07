package com.ikservices.oficinamecanica.workorders.items.services.infra.persistence;

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
public class WorkOrderServiceItemEntityId implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long itemId;
	private Long workOrderItemId;
	private Long budgetId;
}
