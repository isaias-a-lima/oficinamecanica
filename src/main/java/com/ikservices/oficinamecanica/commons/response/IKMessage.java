package com.ikservices.oficinamecanica.commons.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IKMessage {
    private String code;
    private Integer type;
    private String message;
}
