package com.ikservices.oficinamecanica.customers.domain;

import com.ikservices.oficinamecanica.commons.enumerates.TaxPayerEnum;
import com.ikservices.oficinamecanica.commons.vo.AddressVO;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.workshops.domain.Workshop;
import lombok.*;

import java.util.List;
import java.util.Map;

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
    private TaxPayerEnum type;
    private List<Map<Long, Vehicle>> vehicles;
}
