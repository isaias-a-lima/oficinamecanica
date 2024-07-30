package com.ikservices.oficinamecanica.customers.domain;

import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerId implements Serializable {
    private Long workshopId;
    private IdentificationDocumentVO docId;
}
