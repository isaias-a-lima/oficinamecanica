package com.ikservices.oficinamecanica.workorders.payments.infra.persistence;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PaymentEntityId implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "PAYNUMBER")
    private Integer number;
    @Column(name = "WORKORDERID")
    private Long workOrderId;
    @Column(name = "BUDGETID")
    private Long budgetId;
}
