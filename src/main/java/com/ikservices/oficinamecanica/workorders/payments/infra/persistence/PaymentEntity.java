package com.ikservices.oficinamecanica.workorders.payments.infra.persistence;

import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntity;
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "PAYMENTS")
public class PaymentEntity {
    @EmbeddedId
    private PaymentEntityId id;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "WORKORDERID", referencedColumnName = "WORKORDERID", updatable = false, insertable = false),
            @JoinColumn(name = "BUDGETID", referencedColumnName = "BUDGETID", updatable = false, insertable = false)
    })
    private WorkOrderEntity workOrder;
    @Column(name = "DUEDATE")
    private LocalDate dueDate;
    @Column(name = "PAYVALUE")
    private BigDecimal payValue;
    @Column(name = "PAYTYPE")
    private PaymentTypeEnum paymentType;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "PAYDATE")
    private LocalDate payDate;

    public void update(PaymentEntity entity) {
        if(Objects.nonNull(entity.getDueDate())) {
            this.dueDate = entity.getDueDate();
        }

        if(Objects.nonNull(entity.getPayValue())) {
            this.payValue = entity.getPayValue();
        }

        if(Objects.nonNull(entity.getPaymentType())) {
            this.paymentType = entity.getPaymentType();
        }

        this.payDate = entity.getPayDate();

        if(Objects.nonNull(entity.getNote())) {
            this.note = entity.getNote();
        }
    }

}
