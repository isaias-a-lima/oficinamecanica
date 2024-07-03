package com.ikservices.oficinamecanica.customers.domain;

import com.ikservices.oficinamecanica.commons.vo.AddressVO;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;

import java.util.Objects;

public class CustomerFactory {

    private final Customer customer;

    private CustomerFactory() {
        this.customer = new Customer();
    }

    public static CustomerFactory begin() {
        return new CustomerFactory();
    }

    public CustomerSetPhone setCustomerDatas(Long workshopId, Long DocId, String name, CustomerType type) {
        this.customer.setWorkshopId(workshopId);
        this.customer.setIdDoc(DocId);
        this.customer.setName(name);
        this.customer.setType(type);
        return new CustomerSetPhone();
    }

    public class CustomerSetPhone {
        public CustomerSetPhone setLandline(Integer countryCode, Integer stateCode, Integer phoneNumber) {
            customer.setLandline(new PhoneVO(countryCode, stateCode, phoneNumber));
            return this;
        }

        public CustomerSetEmail setMobilePhone(Integer countryCode, Integer stateCode, Integer phoneNumber) {
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