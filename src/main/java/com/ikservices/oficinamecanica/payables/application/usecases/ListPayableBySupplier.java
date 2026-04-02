package com.ikservices.oficinamecanica.payables.application.usecases;

import com.ikservices.oficinamecanica.payables.application.gateways.PayableRepository;
import com.ikservices.oficinamecanica.payables.domain.Payable;

import java.time.LocalDate;
import java.util.List;

public class ListPayableBySupplier {
    private final PayableRepository repository;

    public ListPayableBySupplier(PayableRepository repository) {
        this.repository = repository;
    }

    public List<Payable> execute(Long workshopId, Integer supplierId, LocalDate startDate, LocalDate endDate) {
        return repository.listPayableBySuppliers(workshopId, supplierId, startDate, endDate);
    }
}
