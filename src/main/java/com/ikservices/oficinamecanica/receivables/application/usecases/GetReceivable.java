package com.ikservices.oficinamecanica.receivables.application.usecases;

import com.ikservices.oficinamecanica.receivables.application.gatways.ReceivableRepository;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;
import com.ikservices.oficinamecanica.receivables.domain.ReceivableId;

public class GetReceivable {

    private final ReceivableRepository repository;

    public GetReceivable(ReceivableRepository repository) {
        this.repository = repository;
    }

    public Receivable execute(ReceivableId receivableId) {
        return repository.getReceivable(receivableId);
    }
}
