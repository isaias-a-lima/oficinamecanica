package com.ikservices.oficinamecanica.commons.vo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressVOTest {

    @Test
    public void testAddressFactory() {
        String postaCode = "02323-000";
        String street = "Rua do Auto Peças";
        Integer number = 123;
        String complement = "Térreo";
        String neighborhood = "Vila Zilda";
        String city = "São Paulo";
        String state = "SP";
        String country = "Brasil";

        AddressVO expectedObject = new AddressVO();
        expectedObject.setPostalCode(postaCode);
        expectedObject.setStreet(street);
        expectedObject.setNumber(number);
        expectedObject.setComplement(complement);
        expectedObject.setNeighborhood(neighborhood);
        expectedObject.setCity(city);
        expectedObject.setState(state);
        expectedObject.setCountry(country);

        AddressVO subject = AddressVO.getFactory().setPostalCode(postaCode)
                .setStreetAndNumberAndComplement(street, number, complement)
                .setNeighborhood(neighborhood)
                .setCityAndStateAndCountry(city, state, country)
                .build();

        Assertions.assertEquals(expectedObject, subject);
    }
}
