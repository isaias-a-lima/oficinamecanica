package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@EqualsAndHashCode
public class EmailVO {
    private final String mailAddress;

    public EmailVO(String mailAddress) {
        if (Objects.nonNull(mailAddress) && !mailAddress.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IKException("E-mail inválido.");
        }
        this.mailAddress = mailAddress;
    }
}
