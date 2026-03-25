package com.ikservices.oficinamecanica.reports.suppliers.infra;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = {"supplierId", "workshopId"})
public class SupplierBalanceReportDTO {
    private Long supplierId;
    private Long workshopId;
    private String name;
    private String payables;
    private String receivables;
    private String balance;
}
