package com.ikservices.oficinamecanica.receivables.infra.persistence;

import com.ikservices.oficinamecanica.receivables.domain.ReceivableId;
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
public class ReceivableEntityId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "RECEIVABLEID")
    private Long orderNumber;
    @Column(name = "WORKSHOPID")
    private Long workshopId;

    public ReceivableEntityId(ReceivableId id) {
        this. orderNumber = id.getOrderNumber();
        this.workshopId = id.getWorkshopId();
    }
}
