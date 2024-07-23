package com.ikservices.oficinamecanica.customers.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTypeTest {
    private CustomerType subject;

    @Test
    public void testPhysicalPerson() {
        subject = CustomerType.PHYSICAL_PERSON;
        Assertions.assertEquals(subject.getType(), CustomerType.PHYSICAL_PERSON.getType());
        Assertions.assertEquals(subject, CustomerType.PHYSICAL_PERSON);
    }

    @Test
    public void testCompanyPerson() {
        subject = CustomerType.COMPANY_PERSON;
        Assertions.assertEquals(subject.getType(), CustomerType.COMPANY_PERSON.getType());
        Assertions.assertEquals(subject, CustomerType.COMPANY_PERSON);
    }
}
