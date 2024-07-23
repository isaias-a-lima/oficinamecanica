package com.ikservices.oficinamecanica.customers.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerId implements Serializable {
    private Long workshopId;
    private String docId;
}
