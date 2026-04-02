package com.ikservices.oficinamecanica.receivables.application.usecases;

import com.ikservices.oficinamecanica.receivables.application.ReceivableStatusEnum;
import com.ikservices.oficinamecanica.receivables.application.gatways.ReceivableRepository;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;
import com.ikservices.oficinamecanica.workorders.payments.application.enumerates.PaymentStateEnum;

import java.time.LocalDate;
import java.util.List;

public class ListOutsourceReceivables {

    private final ReceivableRepository repository;

    public ListOutsourceReceivables(ReceivableRepository repository) {
        this.repository = repository;
    }

    public List<Receivable> execute(Long workshopId, LocalDate dueDateBegin, LocalDate dueDateEnd, ReceivableStatusEnum receivableStatus) {
        return this.repository.listOutsourceReceivables(workshopId, dueDateBegin, dueDateEnd, receivableStatus);
    }
}
