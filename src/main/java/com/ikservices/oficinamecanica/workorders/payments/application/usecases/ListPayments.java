package com.ikservices.oficinamecanica.workorders.payments.application.usecases;

import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.payments.application.gateways.PaymentRepository;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;

import java.util.List;

public class ListPayments {
    private final PaymentRepository repository;

    public ListPayments(PaymentRepository repository) {
        this.repository = repository;
    }

    public List<Payment> execute(WorkOrderId workOrderId) {
        return this.repository.listPayments(workOrderId);
    }
}
