package com.ikservices.oficinamecanica.receivables.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Receivable {
    private ReceivableId id;
    private LocalDate dueDate;
    private BigDecimal paymentValue;
    private ReceivableTypeEnum receivableType;
    private LocalDate paymentDate;
    private Boolean outsourcePayment;
    private Integer supplierId;
    private String note;
}
