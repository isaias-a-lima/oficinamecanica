package com.ikservices.oficinamecanica.receivables.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReceivableId {
    private Long orderNumber;
    private Long workshopId;
}
