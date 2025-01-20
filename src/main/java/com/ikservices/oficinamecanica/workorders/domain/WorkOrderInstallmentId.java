package com.ikservices.oficinamecanica.workorders.domain;

import java.io.Serializable;

import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.vehicles.domain.FuelEnum;
import com.ikservices.oficinamecanica.vehicles.domain.TransmissionEnum;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;

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
	private Integer installmentType;

}
