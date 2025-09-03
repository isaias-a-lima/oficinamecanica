package com.ikservices.oficinamecanica.receivables.infra.controller;

import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.receivables.application.ReceivableStatusEnum;
import com.ikservices.oficinamecanica.receivables.application.usecases.*;
import com.ikservices.oficinamecanica.receivables.domain.Receivable;
import com.ikservices.oficinamecanica.receivables.domain.ReceivableId;
import com.ikservices.oficinamecanica.receivables.infra.ReceivableConstant;
import com.ikservices.oficinamecanica.receivables.infra.ReceivableConverter;
import com.ikservices.oficinamecanica.workorders.payments.application.enumerates.PaymentStateEnum;
import com.ikservices.oficinamecanica.workorders.payments.application.usecases.ListOutsourcePayments;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;
import org.slf4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("receivable")
public class ReceivableController {

    private static final Logger LOGGER = IKLoggerUtil.getLogger(ReceivableController.class);

    private final ReceivableConverter converter;
    private final SaveReceivable saveReceivable;
    private final GetReceivable getReceivable;
    private final UpdateReceivable updateReceivable;
    private final ListReceivable listReceivable;
    private final ListOutsourceReceivables listOutsourceReceivables;
    private final ListOutsourcePayments listOutsourcePayments;

    public ReceivableController(ReceivableConverter converter, SaveReceivable saveReceivable, GetReceivable getReceivable, UpdateReceivable updateReceivable, ListReceivable listReceivable, ListOutsourceReceivables listOutsourceReceivables, ListOutsourcePayments listOutsourcePayments) {
        this.converter = converter;
        this.saveReceivable = saveReceivable;
        this.getReceivable = getReceivable;
        this.updateReceivable = updateReceivable;
        this.listReceivable = listReceivable;
        this.listOutsourceReceivables = listOutsourceReceivables;
        this.listOutsourcePayments = listOutsourcePayments;
    }

    @GetMapping("get/{workshopId}/{receivableId}")
    public ResponseEntity<IKResponse<ReceivableDTO>> getReceivable(@PathVariable Long workshopId, @PathVariable Long receivableId) {

        try {
            Receivable receivable = getReceivable.execute(new ReceivableId(receivableId, workshopId));
            return ResponseEntity.ok(IKResponse.<ReceivableDTO>build().body(converter.parseDomainToResponse(receivable)));
        } catch (IKException ike) {
            LOGGER.error(ike.getMessage(), ike.getCause());
            int cod = Integer.parseInt(ike.getIkMessage().getCode()) > 99 ? Integer.parseInt(ike.getIkMessage().getCode()) : HttpStatus.EXPECTATION_FAILED.value();
            return ResponseEntity.status(HttpStatus.valueOf(cod)).body(
                    IKResponse.<ReceivableDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage())
            );
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    IKResponse.<ReceivableDTO>build().addMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, ReceivableConstant.RECEIVABLE_GET_ERROR_MESSAGE)
            );
        }
    }

    @PostMapping
    public ResponseEntity<IKResponse<ReceivableDTO>> saveReceivable(@RequestBody ReceivableDTO dto, UriComponentsBuilder uriBuilder) {
        try {
            Receivable receivable = saveReceivable.execute(converter.parseRequestToDomain(dto));
            URI uri = uriBuilder.path("receivable/get/{workshopId}/{receivableId}").buildAndExpand(receivable.getId().getWorkshopId(), receivable.getId().getOrderNumber()).toUri();
            return ResponseEntity.created(uri).body(
                    IKResponse.<ReceivableDTO>build().body(converter.parseDomainToResponse(receivable)).addMessage(IKConstants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, ReceivableConstant.RECEIVABLE_SAVE_SUCCESS_MESSAGE)
            );
        } catch (IKException ike) {
            LOGGER.error(ike.getMessage(), ike.getCause());
            int cod = Integer.parseInt(ike.getIkMessage().getCode()) > 99 ? Integer.parseInt(ike.getIkMessage().getCode()) : HttpStatus.EXPECTATION_FAILED.value();
            return ResponseEntity.status(HttpStatus.valueOf(cod)).body(
                    IKResponse.<ReceivableDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage())
            );
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    IKResponse.<ReceivableDTO>build().addMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, ReceivableConstant.RECEIVABLE_SAVE_ERROR_MESSAGE)
            );
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<IKResponse<ReceivableDTO>> updateReceivable(@RequestBody ReceivableDTO dto) {
        try {
            Receivable receivable = updateReceivable.execute(converter.parseRequestToDomain(dto));
            return ResponseEntity.ok(
                    IKResponse.<ReceivableDTO>build().body(converter.parseDomainToResponse(receivable)).addMessage(IKConstants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, ReceivableConstant.RECEIVABLE_UPDATE_SUCCESS_MESSAGE)
            );
        } catch (IKException ike) {
            LOGGER.error(ike.getMessage(), ike.getCause());
            int cod = Integer.parseInt(ike.getIkMessage().getCode()) > 99 ? Integer.parseInt(ike.getIkMessage().getCode()) : HttpStatus.EXPECTATION_FAILED.value();
            return ResponseEntity.status(HttpStatus.valueOf(cod)).body(
                    IKResponse.<ReceivableDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage())
            );
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    IKResponse.<ReceivableDTO>build().addMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, ReceivableConstant.RECEIVABLE_SAVE_ERROR_MESSAGE)
            );
        }
    }

    @GetMapping("list/{workshopId}")
    public ResponseEntity<IKResponse<ReceivableDTO>> listReceivable(
            @PathVariable Long workshopId, @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, @RequestParam(name = "status") Integer status) {

        try {
            List<Receivable> receivableList = listReceivable.execute(workshopId, startDate, endDate, ReceivableStatusEnum.findByIndex(status));
            return ResponseEntity.ok(IKResponse.<ReceivableDTO>build().body(converter.parseDomainToResponseList(receivableList)));
        } catch (IKException ike) {
            LOGGER.error(ike.getMessage(), ike.getCause());
            int cod = Integer.parseInt(ike.getIkMessage().getCode()) > 99 ? Integer.parseInt(ike.getIkMessage().getCode()) : HttpStatus.EXPECTATION_FAILED.value();
            return ResponseEntity.status(HttpStatus.valueOf(cod)).body(
                    IKResponse.<ReceivableDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage())
            );
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    IKResponse.<ReceivableDTO>build().addMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, ReceivableConstant.RECEIVABLE_LIST_ERROR_MESSAGE)
            );
        }
    }

    @GetMapping("list/outsource/{workshopId}")
    public ResponseEntity<IKResponse<ReceivableDTO>> listOutsourceReceivable(
            @PathVariable Long workshopId, @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, @RequestParam(name = "status") Integer status) {

        try {
            List<Receivable> receivableList = listOutsourceReceivables.execute(workshopId, startDate, endDate, ReceivableStatusEnum.findByIndex(status));
            return ResponseEntity.ok(IKResponse.<ReceivableDTO>build().body(converter.parseDomainToResponseList(receivableList)));
        } catch (IKException ike) {
            LOGGER.error(ike.getMessage(), ike.getCause());
            int cod = Integer.parseInt(ike.getIkMessage().getCode()) > 99 ? Integer.parseInt(ike.getIkMessage().getCode()) : HttpStatus.EXPECTATION_FAILED.value();
            return ResponseEntity.status(HttpStatus.valueOf(cod)).body(
                    IKResponse.<ReceivableDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage())
            );
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    IKResponse.<ReceivableDTO>build().addMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, ReceivableConstant.RECEIVABLE_LIST_ERROR_MESSAGE)
            );
        }
    }

    @GetMapping("list/all-outsources/{workshopId}")
    public ResponseEntity<IKResponse<ReceivableDTO>> listOutsourceReceivableAndPayments(
            @PathVariable Long workshopId, @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, @RequestParam(name = "status") Integer status) {

        try {
            List<ReceivableDTO> list1 = converter.parseDomainToResponseList(listOutsourceReceivables.execute(workshopId, startDate, endDate, ReceivableStatusEnum.findByIndex(status)));
            List<ReceivableDTO> list2 = converter.parsePaymentToReceivableList(listOutsourcePayments.execute(workshopId, startDate, endDate, PaymentStateEnum.findByIndex(status)));

            List<ReceivableDTO> finalList = new ArrayList<>();
            finalList.addAll(list1);
            finalList.addAll(list2);
            finalList.sort(Comparator.comparing(ReceivableDTO::getDueDate));

            return ResponseEntity.ok(IKResponse.<ReceivableDTO>build().body(finalList));
        } catch (IKException ike) {
            LOGGER.error(ike.getMessage(), ike.getCause());
            int cod = Integer.parseInt(ike.getIkMessage().getCode()) > 99 ? Integer.parseInt(ike.getIkMessage().getCode()) : HttpStatus.EXPECTATION_FAILED.value();
            return ResponseEntity.status(HttpStatus.valueOf(cod)).body(
                    IKResponse.<ReceivableDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage())
            );
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    IKResponse.<ReceivableDTO>build().addMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, ReceivableConstant.RECEIVABLE_LIST_ERROR_MESSAGE)
            );
        }
    }
}
