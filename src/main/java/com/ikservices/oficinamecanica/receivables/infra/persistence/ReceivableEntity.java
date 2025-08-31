package com.ikservices.oficinamecanica.receivables.infra.persistence;

import com.ikservices.oficinamecanica.receivables.domain.ReceivableTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "RECEIVABLES")
public class ReceivableEntity {
    @EmbeddedId
    private ReceivableEntityId id;
    @Column(name = "DUEDATE")
    private LocalDate dueDate;
    @Column(name = "PAYVALUE")
    private BigDecimal payValue;
    @Column(name = "PAYTYPE")
    private ReceivableTypeEnum paymentType;
    @Column(name = "PAYDATE")
    private LocalDate payDate;
    @Column(name = "OUTSOURCEPAY")
    private Boolean isOutsourcePay;
    @Column(name = "SUPPLIERID")
    private Integer supplierId;
    @Column(name = "NOTE")
    private String note;

    public void update(ReceivableEntity entity) {
        if(Objects.nonNull(entity.getDueDate())) {
            this.dueDate = entity.getDueDate();
        }

        if(Objects.nonNull(entity.getPayValue())) {
            this.payValue = entity.getPayValue();
        }
        
        this.paymentType = entity.getPaymentType();

        this.payDate = entity.getPayDate();

        this.isOutsourcePay = entity.getIsOutsourcePay();

        this.supplierId = entity.getSupplierId();

        this.note = entity.getNote();
    }
}
