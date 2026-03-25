package com.ikservices.oficinamecanica.reports.suppliers.infra;

import com.ikservices.oficinamecanica.payables.application.usecases.ListPayableBySupplier;
import com.ikservices.oficinamecanica.receivables.application.usecases.ListReceivableBySupplier;
import com.ikservices.oficinamecanica.reports.suppliers.application.ListSupplierBalanceReport;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetSupplierList;
import com.ikservices.oficinamecanica.workorders.payments.application.usecases.ListPaymentsBySupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(name = "supplier_balance_report.properties", value = "classpath:supplier_balance_report.properties", encoding = "UTF-8")
public class SupplierBalanceReportConfig {

    @Bean
    public ListSupplierBalanceReport getListSupplierBalanceReport(GetSupplierList suppliers, ListPayableBySupplier payables, ListPaymentsBySupplier payments, ListReceivableBySupplier receivables) {
        return new ListSupplierBalanceReport(suppliers, payables, payments, receivables);
    }

    @Bean
    public SupplierBalanceReportConverter getSupplierBalanceReportConverter() {
        return new SupplierBalanceReportConverter();
    }

}
