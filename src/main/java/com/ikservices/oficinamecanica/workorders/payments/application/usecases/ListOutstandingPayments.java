package com.ikservices.oficinamecanica.workorders.payments.application.usecases;

import com.ikservices.oficinamecanica.workorders.payments.application.gateways.PaymentRepository;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;

import java.util.List;

public class ListOutstandingPayments {

    private PaymentRepository repository;

    public ListOutstandingPayments(PaymentRepository repository) {
        this.repository = repository;
    }

    public List<Payment> execute(Long workShopId) {
        return repository.listOutStandingPayments(workShopId);
    }
}
