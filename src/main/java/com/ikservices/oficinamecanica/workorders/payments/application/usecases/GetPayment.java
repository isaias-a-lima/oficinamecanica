package com.ikservices.oficinamecanica.workorders.payments.application.usecases;

import com.ikservices.oficinamecanica.workorders.payments.application.gateways.PaymentRepository;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentId;

import java.util.List;

public class GetPayment {
    private final PaymentRepository repository;

    public GetPayment(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment execute(PaymentId id) {
        return this.repository.getPayment(id);
    }
}
