package com.ikservices.oficinamecanica.reports.suppliers.application;

import com.ikservices.oficinamecanica.commons.interfaces.IKBusiness;
import com.ikservices.oficinamecanica.payables.application.usecases.ListPayableBySupplier;
import com.ikservices.oficinamecanica.payables.domain.Payable;
import com.ikservices.oficinamecanica.receivables.application.usecases.ListReceivableBySupplier;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;
import com.ikservices.oficinamecanica.reports.suppliers.domain.SupplierBalanceReport;
import com.ikservices.oficinamecanica.reports.suppliers.domain.SupplierBalanceReportId;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetSupplierList;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.workorders.payments.application.usecases.ListPaymentsBySupplier;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListSupplierBalanceReport {

    private final GetSupplierList suppliers;
    private final ListPayableBySupplier payables;
    private final ListPaymentsBySupplier payments;
    private final ListReceivableBySupplier receivables;
    private final List<IKBusiness<SupplierBalanceReport>> businessRules;

    public ListSupplierBalanceReport(GetSupplierList suppliers, ListPayableBySupplier payables, ListPaymentsBySupplier payments, ListReceivableBySupplier receivables) {
        this.suppliers = suppliers;
        this.payables = payables;
        this.payments = payments;
        this.receivables = receivables;
        this.businessRules = new ArrayList<>();

    }

    public List<SupplierBalanceReport> execute(Long workshopId, LocalDate startDate, LocalDate endDate) {

        List<SupplierBalanceReport> report = new ArrayList<>();

        List<Supplier> supplierList = this.suppliers.execute(workshopId, "");

        for (Supplier supplier : supplierList) {

            //TODO change supplierId from Long to Integer.
            List<Payable> payableList = new ArrayList<>(this.payables.execute(workshopId, supplier.getSupplierId().getId().intValue(), startDate, endDate));
            List<Payment> paymentList = new ArrayList<>(this.payments.execute(workshopId, supplier.getSupplierId().getId().intValue(), startDate, endDate));
            List<Receivable> receivableList = new ArrayList<>(this.receivables.execute(workshopId, supplier.getSupplierId().getId().intValue(), startDate, endDate));

            BigDecimal payableSum = BigDecimal.ZERO;
            BigDecimal paymentSum = BigDecimal.ZERO;
            BigDecimal receivableSum = BigDecimal.ZERO;

            for (Payable payable : payableList) {
                payableSum = payableSum.add(payable.getPayValue());
            }

            for (Payment payment : paymentList) {
                paymentSum = paymentSum.add(payment.getPaymentValue());
            }

            for (Receivable receivable : receivableList) {
                receivableSum = receivableSum.add(receivable.getPaymentValue());
            }

            SupplierBalanceReportId id = new SupplierBalanceReportId(supplier.getSupplierId().getId(), supplier.getSupplierId().getWorkshopid());
            report.add(new SupplierBalanceReport(id, supplier.getName(), payableSum, paymentSum.add(receivableSum)));
        }

        return report;
    }
}
