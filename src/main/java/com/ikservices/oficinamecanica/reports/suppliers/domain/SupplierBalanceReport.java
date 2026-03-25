package com.ikservices.oficinamecanica.reports.suppliers.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class SupplierBalanceReport {
    private SupplierBalanceReportId id;
    private String name;
    private BigDecimal payables;
    private BigDecimal receivables;

    public SupplierBalanceReport() {
        this.payables = BigDecimal.ZERO;
        this.receivables = BigDecimal.ZERO;
    }

    public SupplierBalanceReport(SupplierBalanceReportId id, String name, BigDecimal payables, BigDecimal receivables) {
        this.id = id;
        this.name = name;
        this.payables = payables;
        this.receivables = receivables;
    }

    public BigDecimal getBalance() {
        return this.payables.subtract(this.receivables);
    }
}
