package com.ikservices.oficinamecanica.receivables.application.usecases;

import com.ikservices.oficinamecanica.receivables.application.ReceivableStatusEnum;
import com.ikservices.oficinamecanica.receivables.application.gatways.ReceivableRepository;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;

import java.time.LocalDate;
import java.util.List;

public class ListReceivableBySupplier {
    private final ReceivableRepository repository;

    public ListReceivableBySupplier(ReceivableRepository repository) {
        this.repository = repository;
    }

    public List<Receivable> execute(Long workshopId, Integer supplierId, LocalDate startDate, LocalDate endDate) {
        return repository.listReceivableBySupplierAndPayDate(workshopId, supplierId, startDate, endDate);
    }
}
