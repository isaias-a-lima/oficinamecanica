package com.ikservices.oficinamecanica.workorders.payments.application.usecases;

import com.ikservices.oficinamecanica.workorders.payments.application.enumerates.PaymentStateEnum;
import com.ikservices.oficinamecanica.workorders.payments.application.gateways.PaymentRepository;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;

import java.time.LocalDate;
import java.util.List;

public class ListOutsourcePayments {

    private final PaymentRepository repository;

    public ListOutsourcePayments(PaymentRepository repository) {
        this.repository = repository;
    }

    public List<Payment> execute(Long workshopId, LocalDate dueDateBegin, LocalDate dueDateEnd, PaymentStateEnum paymentState) {
        return this.repository.listOutsourcePayments(workshopId, dueDateBegin, dueDateEnd, paymentState);
    }
}
