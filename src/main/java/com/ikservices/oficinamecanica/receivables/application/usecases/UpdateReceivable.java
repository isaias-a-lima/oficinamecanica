package com.ikservices.oficinamecanica.receivables.application.usecases;

import com.ikservices.oficinamecanica.receivables.application.gatways.ReceivableRepository;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;

public class UpdateReceivable {
    private final ReceivableRepository repository;

    public UpdateReceivable(ReceivableRepository repository) {
        this.repository = repository;
    }

    public Receivable execute(Receivable receivable) {
        return repository.updateReceivable(receivable);
    }
}
