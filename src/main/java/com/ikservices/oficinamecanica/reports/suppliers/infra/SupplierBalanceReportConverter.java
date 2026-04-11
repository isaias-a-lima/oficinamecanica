package com.ikservices.oficinamecanica.reports.suppliers.infra;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.generics.IKConverter;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.reports.suppliers.domain.SupplierBalanceReport;
import com.ikservices.oficinamecanica.reports.suppliers.domain.SupplierBalanceReportId;

import java.util.Objects;

public class SupplierBalanceReportConverter extends IKConverter<SupplierBalanceReportDTO, SupplierBalanceReport, SupplierBalanceReport, SupplierBalanceReportDTO> {

    @Override
    public SupplierBalanceReport parseRequestToDomain(SupplierBalanceReportDTO request) {
        if (Objects.isNull(request)) {
            throw new IKException("Null object");
        }

        SupplierBalanceReport domain = new SupplierBalanceReport();
        if (null != request.getSupplierId() && null != request.getWorkshopId()) {
            domain.setId(new SupplierBalanceReportId(request.getSupplierId(), request.getWorkshopId()));
        }
        domain.setName(request.getName());
        domain.setPayables(NumberUtil.parseBigDecimal(request.getPayables()));
        domain.setReceivables(NumberUtil.parseBigDecimal(request.getReceivables()));

        return domain;
    }

    @Override
    public SupplierBalanceReport parseDomainToEntity(SupplierBalanceReport domain) {
        return null;
    }

    @Override
    public SupplierBalanceReport parseEntityToDomain(SupplierBalanceReport entity) {
        return null;
    }

    @Override
    public SupplierBalanceReportDTO parseDomainToResponse(SupplierBalanceReport domain) {
        if (Objects.isNull(domain)) {
            throw new IKException("Null object");
        }

        SupplierBalanceReportDTO dto = new SupplierBalanceReportDTO();
        dto.setSupplierId(domain.getId().getSupplierId());
        dto.setWorkshopId(domain.getId().getWorkshopId());
        dto.setName(domain.getName());
        dto.setPayables(NumberUtil.parseStringMoney(domain.getPayables()));
        dto.setReceivables(NumberUtil.parseStringMoney(domain.getReceivables()));
        dto.setBalance(NumberUtil.parseStringMoneyWithOperatorSymbol(domain.getBalance()));

        return dto;
    }
}
