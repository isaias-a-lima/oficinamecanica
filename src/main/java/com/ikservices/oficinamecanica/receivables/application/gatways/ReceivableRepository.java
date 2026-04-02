package com.ikservices.oficinamecanica.receivables.application.gatways;

import com.ikservices.oficinamecanica.receivables.application.ReceivableStatusEnum;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;
import com.ikservices.oficinamecanica.receivables.domain.ReceivableId;

import java.time.LocalDate;
import java.util.List;

public interface ReceivableRepository {
    Receivable saveReceivable(Receivable receivable);
    Receivable getReceivable(ReceivableId id);
    Receivable updateReceivable(Receivable receivable);
    List<Receivable> listReceivables(Long workshopId, LocalDate startDate, LocalDate endDate, ReceivableStatusEnum status);
    List<Receivable> listOutsourceReceivables(Long workshopId, LocalDate startDate, LocalDate endDate, ReceivableStatusEnum status);
    List<Receivable> listReceivableBySupplierAndPayDate(Long workshopId, Integer supplierId, LocalDate startDate, LocalDate endDate);
}
