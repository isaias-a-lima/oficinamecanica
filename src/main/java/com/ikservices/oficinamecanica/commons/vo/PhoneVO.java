package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
public class PhoneVO {
    private final Integer countryCode;
    private final Integer stateCode;
    private final Integer phoneNumber;

    public PhoneVO(Integer countryCode, Integer stateCode, Integer phoneNumber) {
        if (Objects.isNull(stateCode) || Objects.isNull(phoneNumber)) {
            throw new IKException("Telefone inválido.");
        }
        this.countryCode = countryCode;
        this.stateCode = stateCode;
        this.phoneNumber = phoneNumber;
    }

    public String getFullPhone() {
        String phone = "";

        if (Objects.nonNull(countryCode)) {
            phone = "+" + countryCode + " ";
        }

        phone += stateCode + " ";

        int phoneLength = String.valueOf(phoneNumber).length();
        phone += phoneLength == 9 ? String.valueOf(phoneNumber).substring(0, 5) + "-" : String.valueOf(phoneNumber).substring(0, 4) + "-";
        phone += phoneLength == 9 ? String.valueOf(phoneNumber).substring(5, phoneLength) : String.valueOf(phoneNumber).substring(4, phoneLength);

        return phone;
    }

    /**
     * <p>Parse String to PhoneVO</p>
     * The parameter fullPhone must be Formatted like it: <br>
     * <strong style='color:yellow'>+00 11 92222-3333</strong> for Mobile phone, and, <br>
     * <strong style='color:yellow'>+00 11 2222-3333</strong> for landline.
     * @param fullPhone String
     * @return PhoneVO
     */
    public static PhoneVO parsePhoneVO(String fullPhone) {

        if (Objects.nonNull(fullPhone) && fullPhone.length() >= 16) {

            boolean valid = fullPhone.contains("+") && fullPhone.contains(" ") && fullPhone.contains("-");

            if (valid) {
                String[] phoneArray = fullPhone.split(" ");
                Integer countryCode = Integer.parseInt(phoneArray[0].replace("+", ""));
                Integer stateCode = Integer.parseInt(phoneArray[1]);
                Integer phoneNumber = Integer.parseInt(phoneArray[2].replace("-", ""));
                return new PhoneVO(countryCode, stateCode, phoneNumber);
            }
        }
        return null;
    }
}
