package com.ikservices.oficinamecanica.payables.application.usecases;

import com.ikservices.oficinamecanica.payables.application.gateways.PayableRepository;
import com.ikservices.oficinamecanica.payables.domain.Payable;

import java.util.List;

public class ListOutstandingPayables {
    private final PayableRepository repository;

    public ListOutstandingPayables(PayableRepository repository) {
        this.repository = repository;
    }

    public List<Payable> execute(Long workshopId) {
        return this.repository.findOutstandingPayables(workshopId);
    }
}
