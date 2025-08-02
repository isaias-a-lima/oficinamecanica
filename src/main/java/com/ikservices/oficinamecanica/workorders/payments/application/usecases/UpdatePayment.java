package com.ikservices.oficinamecanica.workorders.payments.application.usecases;

import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.payments.application.gateways.PaymentRepository;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;

import java.util.List;

public class UpdatePayment {
    private final PaymentRepository repository;

    public UpdatePayment(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment execute(Payment payment) {
        return repository.updatePayment(payment);
    }

    public List<Payment> execute(List<Payment> paymentList, WorkOrderId workOrderId) {
        return repository.updatePaymentList(paymentList, workOrderId);
    }
}
