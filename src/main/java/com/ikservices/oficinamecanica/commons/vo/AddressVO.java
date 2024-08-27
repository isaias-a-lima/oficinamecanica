package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class AddressVO {
    private String postalCode;
    @Setter
    private String street;
    @Setter
    private Integer number;
    @Setter
    private String complement;
    @Setter
    private String neighborhood;
    @Setter
    private String city;
    @Setter
    private String state;
    @Setter
    private String country;

    public void setPostalCode(String code) {

        if (Objects.nonNull(code) && !code.isEmpty() && code.length() < 8) {
            throw new IKException(HttpStatus.BAD_REQUEST.value(), IKMessageType.WARNING, "Invalid postal code.");
        }
        this.postalCode = code.replaceAll("[^0-9]", "");
    }

    public String getFormattedPostalCode() {
        String codeAux;

        if (Objects.isNull(postalCode) || postalCode.isEmpty()) {
            return "";
        }

        if (postalCode.length() < 8) {
            throw new IKException(HttpStatus.BAD_REQUEST.value(), IKMessageType.WARNING, "Invalid postal code.");
        }

        codeAux = postalCode.replaceAll("[^0-9]", "");
        codeAux = codeAux.substring(0, 5);
        codeAux += "-";
        codeAux += postalCode.replaceAll("[^0-9]", "").substring(5);

        this.postalCode = codeAux;

        return codeAux;
    }

    public String getPartialAddress() {
        if (Objects.isNull(street) || street.isEmpty()) {
            throw new IKException(HttpStatus.BAD_REQUEST.value(), IKMessageType.WARNING, "Invalid street.");
        }

        String addressAux = street;
        addressAux += Objects.nonNull(number) ? ", " + number : "";
        addressAux += Objects.nonNull(complement) ? ", " + complement : "";
        addressAux += Objects.nonNull(neighborhood) ? ", " + neighborhood : "";

        return addressAux;
    }

    public String getFullAddress() {
        String addressAux = getPartialAddress();

        addressAux += Objects.nonNull(city) ? ", " + city : "";
        addressAux += Objects.nonNull(state) ? ", " + state : "";
        addressAux += Objects.nonNull(country) ? ", " + country : "";

        return addressAux;
    }

    public static AddressVOFactory getFactory() {
        return new AddressVOFactory();
    }
}
