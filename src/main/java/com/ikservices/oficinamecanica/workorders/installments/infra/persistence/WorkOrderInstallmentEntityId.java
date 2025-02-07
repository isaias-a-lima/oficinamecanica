package com.ikservices.oficinamecanica.workorders.installments.infra.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ikservices.oficinamecanica.workorders.installments.domain.PayTypeEnum;

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
	@Enumerated(EnumType.ORDINAL)
	private PayTypeEnum installmentType;
}
