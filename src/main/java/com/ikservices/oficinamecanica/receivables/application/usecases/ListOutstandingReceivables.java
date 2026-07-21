package com.ikservices.oficinamecanica.receivables.application.usecases;

import com.ikservices.oficinamecanica.receivables.application.gatways.ReceivableRepository;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;

import java.util.List;

public class ListOutstandingReceivables {

    private final ReceivableRepository repository;

    public ListOutstandingReceivables(ReceivableRepository repository) {
        this.repository = repository;
    }

    public List<Receivable> execute(Long workshopId) {
        return repository.listOutstandingReceivables(workshopId);
    }
}
