package com.ikservices.oficinamecanica.workorders.installments.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ikservices.oficinamecanica.workorders.domain.enumarates.PayFormEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class WorkOrderInstallment {
	
	private WorkOrderInstallmentId id;
	private LocalDate dueDate;
	private BigDecimal payValue;
	private PayFormEnum payForm;
	private LocalDate payDate;
	private String note;

}
