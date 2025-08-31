package com.ikservices.oficinamecanica.receivables.infra.controller;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = {"receivableId", "workshopId"})
public class ReceivableDTO implements Serializable {
    private Long receivableId;
    private Long workshopId;
    private LocalDate dueDate;
    private String paymentValue;
    private Integer receivableType;
    private LocalDate paymentDate;
    private Boolean outsourcePayment;
    private Integer supplierId;
    private String note;
}
