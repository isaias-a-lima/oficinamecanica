package com.ikservices.oficinamecanica.work.orders.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.work.orders.domain.PayFormEnum;
import com.ikservices.oficinamecanica.work.orders.domain.WorkOrderStatusEnum;

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