package com.ikservices.oficinamecanica.workorders.payments.application.usecases;

import com.ikservices.oficinamecanica.workorders.payments.application.gateways.PaymentRepository;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;

import java.time.LocalDate;
import java.util.List;

public class ListPaymentsBySupplier {
    private final PaymentRepository repository;

    public ListPaymentsBySupplier(PaymentRepository repository) {
        this.repository = repository;
    }

    public List<Payment> execute(Long workshopId, Integer supplierId, LocalDate startDate, LocalDate endDate) {
        return this.repository.listPaymentsBySupplierAndPayDate(workshopId, supplierId, startDate, endDate);
    }
}
