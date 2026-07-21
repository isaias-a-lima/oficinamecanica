package com.ikservices.oficinamecanica.receivables.application.usecases;

import com.ikservices.oficinamecanica.receivables.application.gatways.ReceivableRepository;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;

import java.time.LocalDate;
import java.util.List;

public class ListReceivablesByPaidPeriod {

    private final ReceivableRepository repository;

    public ListReceivablesByPaidPeriod(ReceivableRepository repository) {
        this.repository = repository;
    }

    public List<Receivable> execute(Long workshopId, LocalDate startDate, LocalDate endDate) {
        return repository.listReceivablesByPaidPeriod(workshopId, startDate, endDate);
    }
}
