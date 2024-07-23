package com.ikservices.oficinamecanica.commons.vo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PhoneVOTest {

    private PhoneVO subject;

    @Test
    public void testCreatePhone() {
        Integer countryCode = 55;
        Integer stateCode = 11;
        Integer phoneNumber = 988887777;

        subject = new PhoneVO(countryCode, stateCode, phoneNumber);

        Assertions.assertEquals(countryCode, subject.getCountryCode());
        Assertions.assertEquals(stateCode, subject.getStateCode());
        Assertions.assertEquals(phoneNumber, subject.getPhoneNumber());
    }

    @Test
    public void testGetFullMobilePhone() {
        subject = new PhoneVO(55, 11, 988887777);
        String fullPhone = subject.getFullPhone(); // +55 11 98888-7777
        Assertions.assertEquals(subject, PhoneVO.parsePhoneVO(fullPhone));
    }

    @Test
    public void testGetFullLandline() {
        subject = new PhoneVO(55, 13, 22223333);
        String fullPhone = subject.getFullPhone(); // +55 11 2222-3333
        Assertions.assertEquals(subject, PhoneVO.parsePhoneVO(fullPhone));
    }
}
