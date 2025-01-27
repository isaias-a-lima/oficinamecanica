package com.ikservices.oficinamecanica.workorders.infra.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.workorders.domain.WorkOrderInstallmentId;

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
@Embeddable
public class WorkOrderInstallmentEntityId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "NUMBER")
	private Long number;
	@Column(name = "WORKORDERID")
	private Long workOrderId;
	@Column(name = "BUDGETID")
	private Long budgetId;
	@Column(name = "PAYTYPE")
	private Integer installmentType;
}
