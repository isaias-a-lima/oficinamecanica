package com.ikservices.oficinamecanica.receivables.application.usecases;

import com.ikservices.oficinamecanica.receivables.application.ReceivableStatusEnum;
import com.ikservices.oficinamecanica.receivables.application.gatways.ReceivableRepository;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;

import java.time.LocalDate;
import java.util.List;

public class ListReceivable {
    private final ReceivableRepository repository;

    public ListReceivable(ReceivableRepository repository) {
        this.repository = repository;
    }

    public List<Receivable> execute(Long workshopId, LocalDate startDate, LocalDate endDate, ReceivableStatusEnum status) {
        return repository.listReceivables(workshopId, startDate, endDate, status);
    }
}
