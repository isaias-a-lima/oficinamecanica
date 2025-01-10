package com.ikservices.oficinamecanica.workorders.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;

public class WorkOrder {
	WorkOrderId id;
	Vehicle vehicle;
	Budget budget;
	LocalDate openingDate;
	Long km;
	WorkOrderStatusEnum workOrderStatus;
	Boolean paid;
	PayFormEnum payForm;
	Integer installments;
	BigDecimal amount;
}