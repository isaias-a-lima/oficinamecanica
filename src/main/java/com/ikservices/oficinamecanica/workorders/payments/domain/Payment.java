package com.ikservices.oficinamecanica.workorders.payments.domain;

import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Payment {
    private PaymentId id;
    private WorkOrder workOrder;
    private LocalDate dueDate;
    private BigDecimal paymentValue;
    private PaymentTypeEnum paymentType;
    private String note;
    private LocalDate payDate;
    private Boolean isOutsourcePay;
    private Integer supplierId;

    public Payment() {
        if (null == this.isOutsourcePay) {
            this.isOutsourcePay = false;
        }
    }
}
