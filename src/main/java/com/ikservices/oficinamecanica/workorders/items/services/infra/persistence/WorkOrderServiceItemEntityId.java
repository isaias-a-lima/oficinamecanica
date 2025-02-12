package com.ikservices.oficinamecanica.workorders.items.services.infra.persistence;

import java.io.Serializable;

import javax.persistence.Column;

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
	
	@Column(name = "ITEMID")
	private Long itemId;
	@Column(name = "WORKORDER_ID")
	private Long workOrderItemId;
	@Column(name = "BUDGET_ID")
	private Long budgetId;
}
