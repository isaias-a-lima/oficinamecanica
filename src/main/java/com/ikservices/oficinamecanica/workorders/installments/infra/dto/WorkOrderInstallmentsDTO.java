package com.ikservices.oficinamecanica.workorders.installments.infra.dto;

import java.math.BigDecimal;
import java.util.List;

import com.ikservices.oficinamecanica.workorders.installments.domain.WorkOrderInstallment;
import com.ikservices.oficinamecanica.workorders.installments.domain.WorkOrderInstallmentId;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WorkOrderInstallmentsDTO {
	private WorkOrderInstallmentIdDTO workOrderInstallmentIdDTO;
	private String dueDate;
	private BigDecimal payValue;
	private String payDate;
	private String note;
}
