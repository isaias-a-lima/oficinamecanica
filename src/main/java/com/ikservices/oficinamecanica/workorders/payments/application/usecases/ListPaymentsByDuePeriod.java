package com.ikservices.oficinamecanica.workorders.payments.application.usecases;

import com.ikservices.oficinamecanica.workorders.payments.application.gateways.PaymentRepository;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;

import java.time.LocalDate;
import java.util.List;

public class ListPaymentsByDuePeriod {
    private final PaymentRepository repository;

    public ListPaymentsByDuePeriod(PaymentRepository repository) {
        this.repository = repository;
    }

    public List<Payment> execute(Long workshopId, LocalDate dueDateBegin, LocalDate dueDateEnd) {
        return  this.repository.listPaymentsByDuePeriod(workshopId, dueDateBegin, dueDateEnd);
    }
}
