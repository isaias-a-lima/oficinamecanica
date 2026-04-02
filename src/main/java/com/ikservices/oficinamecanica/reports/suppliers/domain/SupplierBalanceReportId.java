package com.ikservices.oficinamecanica.reports.suppliers.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SupplierBalanceReportId {
    private Long supplierId;
    private Long workshopId;
}
