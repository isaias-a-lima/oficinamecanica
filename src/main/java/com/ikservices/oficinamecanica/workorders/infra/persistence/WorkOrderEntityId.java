package com.ikservices.oficinamecanica.workorders.infra.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
@Embeddable
public class WorkOrderEntityId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "WORKORDERID")
	private Long workOrderId;
	@Column(name = "BUDGETID")
	private Long budgetId;
}
