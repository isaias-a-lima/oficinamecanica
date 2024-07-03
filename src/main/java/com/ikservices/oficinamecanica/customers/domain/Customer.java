package com.ikservices.oficinamecanica.customers.domain;

import com.ikservices.oficinamecanica.commons.vo.AddressVO;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "idDoc")
public class Customer {
    private Long workshopId;
    private Long idDoc;
    private String name;
    private PhoneVO landline;
    private PhoneVO mobilePhone;
    private EmailVO email;
    private AddressVO address;
    private CustomerType type;
}
