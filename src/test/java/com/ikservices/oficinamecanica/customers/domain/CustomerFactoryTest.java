package com.ikservices.oficinamecanica.customers.domain;

import com.ikservices.oficinamecanica.commons.vo.AddressVO;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerFactoryTest {

    private CustomerFactory subject;
    private Long customerIdDoc;
    private String customerName;
    private CustomerType customerType;

    @BeforeEach
    public void setup() {
        subject = CustomerFactory.begin();
        customerIdDoc = 77L;
        customerName = "Messiah";
        customerType = CustomerType.PHYSICAL_PERSON;
    }

    @Test
    public void testCustomerFactory() {

        Customer customer = subject.setCustomerDatas(customerIdDoc, customerName, customerType)
                .setLandline(getLandline().getCountryCode(), getLandline().getStateCode(), getLandline().getPhoneNumber())
                .setMobilePhone(getMobilePhone().getCountryCode(), getMobilePhone().getStateCode(), getMobilePhone().getPhoneNumber())
                .setEmail(getEmail())
                .setPostalCode(getAddress().getPostalCode())
                .setFirstAddressPart(getAddress().getStreet(), getAddress().getNumber(), getAddress().getComplement(), getAddress().getNeighborhood())
                .setSecondAddressPart(getAddress().getCity(), getAddress().getState(), getAddress().getCountry())
                .build();

        Assertions.assertEquals(customerIdDoc, customer.getIdDoc());
        Assertions.assertEquals(customerName, customer.getName());
        Assertions.assertEquals(customerType, customer.getType());
        Assertions.assertEquals(getLandline(), customer.getLandline());
        Assertions.assertEquals(getMobilePhone(), customer.getMobilePhone());
        Assertions.assertEquals(getAddress(), customer.getAddress());
        Assertions.assertEquals(getEmail(), customer.getEmail());
    }

    private static EmailVO getEmail() {
        return new EmailVO("isaias@teste.com");
    }

    private static PhoneVO getMobilePhone() {
        return new PhoneVO(55, 11, 944445555);
    }

    private static PhoneVO getLandline() {
        return new PhoneVO(55, 13, 69522959);
    }

    private static AddressVO getAddress() {
        AddressVO address = new AddressVO();
        address.setPostalCode("02323000");
        address.setStreet("Rua dos Bois");
        address.setNumber(10);
        address.setComplement("Apto 13");
        address.setNeighborhood("Vila G");
        address.setCity("Guarulhos");
        address.setState("Sao Paulo");
        address.setCountry("Brasil");
        return address;
    }
}
