package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
public class PhoneVO {
    private final String countryCode;
    private final String stateCode;
    private final String phoneNumber;

    public PhoneVO(String countryCode, String stateCode, String phoneNumber) {
        this.countryCode = countryCode;
        this.stateCode = stateCode;
        this.phoneNumber = phoneNumber;
    }

    public String getFullPhone() {
        String phone = "";

        if (Objects.isNull(countryCode) || Objects.isNull(stateCode) || Objects.isNull(phoneNumber)) {
            return "";
        }

        phone = "+" + countryCode + " ";


        phone += stateCode + " ";

        int phoneLength = phoneNumber.length();

        if (phoneLength == 0) {
            return "";
        }

        phone += phoneLength == 9 ? phoneNumber.substring(0, 5) + "-" : phoneNumber.substring(0, 4) + "-";
        phone += phoneLength == 9 ? phoneNumber.substring(5, phoneLength) : phoneNumber.substring(4, phoneLength);

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

        if (Objects.isNull(fullPhone) || fullPhone.isEmpty()) {
            return new PhoneVO("", "", "");
        }

        if (fullPhone.length() >= 16) {

            boolean valid = fullPhone.contains("+") && fullPhone.contains(" ") && fullPhone.contains("-");

            if (valid) {
                String[] phoneArray = fullPhone.split(" ");
                String countryCode = phoneArray[0].replace("+", "");
                String stateCode = phoneArray[1];
                String phoneNumber = phoneArray[2].replace("-", "");
                return new PhoneVO(countryCode, stateCode, phoneNumber);
            }
        }
        throw new IKException( new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), Constants.getPHONE_VO_ERROR_MESSAGE()));
    }
}
