//package com.ikservices.oficinamecanica.customers.domain;
//
//import com.ikservices.oficinamecanica.commons.vo.EmailVO;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class CustomerTest {
//    private Customer subject;
//
//    @BeforeEach
//    public void setup() {
//        subject = CustomerFactory.begin()
//                .setCustomerDatas(1L,1L, "John", CustomerType.PHYSICAL_PERSON)
//                .setLandline(55, 11, 11112222)
//                .setMobilePhone(55, 11, 933334444)
//                .setEmail(new EmailVO("john@teste.com"))
//                .setFirstAddressPart("Rua Abc", 11, "apto. 1", "Vilage")
//                .setSecondAddressPart("Sao Paulo", "Sao Paulo", "Brasil")
//                .build();
//    }
//
//    @Test
//    public void testEqualsAndHash() {
//        Assertions.assertEquals(subject, getCustomer());
//    }
//
//    public static Customer getCustomer() {
//        return CustomerFactory.begin()
//                .setCustomerDatas(1L,1L, "John", CustomerType.PHYSICAL_PERSON)
//                .setLandline(55, 11, 11112222)
//                .setMobilePhone(55, 11, 933334444)
//                .setEmail(new EmailVO("john@teste.com"))
//                .setFirstAddressPart("Rua Abc", 11, "apto. 1", "Vilage")
//                .setSecondAddressPart("Sao Paulo", "Sao Paulo", "Brasil")
//                .build();
//    }
//}
