package com.ikservices.oficinamecanica.commons.vo;

public class AddressVOFactory {

    private final AddressVO addressVO;

    public AddressVOFactory() {
        this.addressVO = new AddressVO();
    }

    public static AddressVOFactory start() {
        return new AddressVOFactory();
    }

    public SetStreetAndNumberAndComplement setPostalCode(String code) {


        addressVO.setPostalCode(code);

        return new SetStreetAndNumberAndComplement();
    }

    public class SetStreetAndNumberAndComplement {
        public SetNeighborhood setStreetAndNumberAndComplement(String street, Integer number, String complement) {
            addressVO.setStreet(street);
            addressVO.setNumber(number);
            addressVO.setComplement(complement);
            return new SetNeighborhood();
        }
    }

    public class SetNeighborhood {
        public SetCityAndStateAndCountry setNeighborhood(String neighborhood) {
            addressVO.setNeighborhood(neighborhood);
            return new SetCityAndStateAndCountry();
        }
    }

    public class SetCityAndStateAndCountry {
        public AddressBuilder setCityAndStateAndCountry(String city, String state, String country) {
            addressVO.setCity(city);
            addressVO.setState(state);
            addressVO.setCountry(country);
            return new AddressBuilder();
        }
    }

    public class AddressBuilder {
        public AddressVO build() {
            return addressVO;
        }
    }
}
