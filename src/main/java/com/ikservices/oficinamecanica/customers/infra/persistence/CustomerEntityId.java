package com.ikservices.oficinamecanica.customers.infra.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CustomerEntityId implements Serializable {
    @Column(name = "WORKSHOPID")
    private Long workshopId;
    @Column(name = "DOCID")
    private String docId;
}
