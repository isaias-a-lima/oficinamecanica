package com.ikservices.oficinamecanica.receivables.application.usecases;

import com.ikservices.oficinamecanica.receivables.application.gatways.ReceivableRepository;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;

public class SaveReceivable {
    private final ReceivableRepository repository;

    public SaveReceivable(ReceivableRepository repository) {
        this.repository = repository;
    }

    public Receivable execute(Receivable receivable) {
        return repository.saveReceivable(receivable);
    }
}
