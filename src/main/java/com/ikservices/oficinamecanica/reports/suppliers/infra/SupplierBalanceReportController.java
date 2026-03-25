package com.ikservices.oficinamecanica.reports.suppliers.infra;

import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.receivables.infra.controller.ReceivableDTO;
import com.ikservices.oficinamecanica.reports.suppliers.application.ListSupplierBalanceReport;
import org.slf4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("reports/supplier")
public class SupplierBalanceReportController {

    private static final Logger LOGGER = IKLoggerUtil.getLogger(SupplierBalanceReportController.class);

    private final ListSupplierBalanceReport listSupplierBalanceReport;
    private final SupplierBalanceReportConverter converter;

    public SupplierBalanceReportController(ListSupplierBalanceReport listSupplierBalanceReport, SupplierBalanceReportConverter converter) {
        this.listSupplierBalanceReport = listSupplierBalanceReport;
        this.converter = converter;
    }

    @GetMapping("{workshopId}")
    public ResponseEntity<IKResponse<SupplierBalanceReportDTO>> listSupplierBalanceReport(@PathVariable Long workshopId,
                                                                                          @PathParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                                          @PathParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        try {
            List<SupplierBalanceReportDTO> reposts = converter.parseDomainToResponseList(listSupplierBalanceReport.execute(workshopId, startDate, endDate));

            return ResponseEntity.ok(IKResponse.<SupplierBalanceReportDTO>build().body(reposts));

        } catch (IKException ike) {
            LOGGER.error(ike.getMessage(), ike.getCause());
            int cod = Integer.parseInt(ike.getIkMessage().getCode()) > 99 ? Integer.parseInt(ike.getIkMessage().getCode()) : HttpStatus.EXPECTATION_FAILED.value();
            return ResponseEntity.status(HttpStatus.valueOf(cod)).body(
                    IKResponse.<SupplierBalanceReportDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage())
            );

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return  ResponseEntity.internalServerError().body(IKResponse.<SupplierBalanceReportDTO>build().addMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, ""));
        }
    }
}
