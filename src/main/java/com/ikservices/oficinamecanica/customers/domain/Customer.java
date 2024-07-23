package com.ikservices.oficinamecanica.customers.domain;

import com.ikservices.oficinamecanica.commons.vo.AddressVO;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import com.ikservices.oficinamecanica.workshops.domain.Workshop;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Customer {
    private CustomerId id;
    private Workshop workshop;
    private String name;
    private PhoneVO landline;
    private PhoneVO mobilePhone;
    private EmailVO email;
    private AddressVO address;
    private CustomerType type;
}
