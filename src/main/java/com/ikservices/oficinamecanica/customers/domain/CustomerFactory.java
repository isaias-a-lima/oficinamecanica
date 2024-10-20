package com.ikservices.oficinamecanica.customers.domain;

import com.ikservices.oficinamecanica.commons.enumerates.TaxPayerEnum;
import com.ikservices.oficinamecanica.commons.vo.AddressVO;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;

import java.util.Objects;

public class CustomerFactory {

    private final Customer customer;

    private CustomerFactory(Long workshopId, IdentificationDocumentVO docId, String name, TaxPayerEnum type) {
        this.customer = new Customer();
        this.customer.setId(new CustomerId(workshopId, docId));
        this.customer.setName(name);
        this.customer.setType(type);
    }

    public static CustomerFactory startBuild(Long workshopId, IdentificationDocumentVO docId, String name, TaxPayerEnum type) {
        return new CustomerFactory(workshopId, docId, name, type);
    }

    public CustomerSetPhone setLandline(String countryCode, String stateCode, String phoneNumber) {
        customer.setLandline(new PhoneVO(countryCode, stateCode, phoneNumber));
        return new CustomerSetPhone();
    }

    public class CustomerSetPhone {
        public CustomerSetEmail setMobilePhone(String countryCode, String stateCode, String phoneNumber) {
            customer.setMobilePhone(new PhoneVO(countryCode, stateCode, phoneNumber));
            return new CustomerSetEmail();
        }
    }

    public class CustomerSetEmail {
        public CustomerSetAddress setEmail(EmailVO email) {
            customer.setEmail(email);
            return new CustomerSetAddress();
        }
    }

    public class CustomerSetAddress {
        public CustomerSetAddress setPostalCode(String postalCode) {
            if (Objects.isNull(customer.getAddress())) {
                customer.setAddress(new AddressVO());
            }
            customer.getAddress().setPostalCode(postalCode);
            return this;
        }
        public CustomerSetAddress setFirstAddressPart(String street, Integer number,String complement, String neighborhood) {
            if (Objects.isNull(customer.getAddress())) {
                customer.setAddress(new AddressVO());
            }
            customer.getAddress().setStreet(street);
            customer.getAddress().setNumber(number);
            customer.getAddress().setComplement(complement);
            customer.getAddress().setNeighborhood(neighborhood);
            return this;
        }
        public Builder setSecondAddressPart(String city, String state, String country) {
            if (Objects.isNull(customer.getAddress())) {
                customer.setAddress(new AddressVO());
            }
            customer.getAddress().setCity(city);
            customer.getAddress().setState(state);
            customer.getAddress().setCountry(country);
            return new Builder();
        }
    }

    public class Builder {
        public Customer build() {
            return customer;
        }
    }
}