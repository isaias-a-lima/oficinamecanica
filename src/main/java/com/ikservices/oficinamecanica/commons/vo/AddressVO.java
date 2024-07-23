package com.ikservices.oficinamecanica.commons.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class AddressVO {
    //TODO Must be created a class to represent the postal code
    private String postalCode;
    private String street;
    private Integer number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
}
