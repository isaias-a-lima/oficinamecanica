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
    private final Integer coutryCode;
    private final Integer stateCode;
    private final Integer phoneNumber;

    public PhoneVO(Integer coutryCode, Integer stateCode, Integer phoneNumber) {
        if (Objects.isNull(stateCode) || Objects.isNull(phoneNumber)) {
            throw new IKException("Telefone inválido.");
        }
        this.coutryCode = coutryCode;
        this.stateCode = stateCode;
        this.phoneNumber = phoneNumber;
    }

    public String getFullPhone() {
        String phone = "";

        if (Objects.nonNull(coutryCode)) {
            phone = "+" + coutryCode + " ";
        }

        phone += stateCode;

        int phoneLength = String.valueOf(phoneNumber).length();
        phone += phoneLength == 9 ? String.valueOf(phoneNumber).substring(0, 5) + "-" : String.valueOf(phoneNumber).substring(0, 4) + "-";
        phone += phoneLength == 9 ? String.valueOf(phoneNumber).substring(5, phoneLength) : String.valueOf(phoneNumber).substring(4, phoneLength);

        return phone;
    }
}
