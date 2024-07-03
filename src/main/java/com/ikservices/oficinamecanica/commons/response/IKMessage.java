package com.ikservices.oficinamecanica.commons.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IKMessage {
    private Integer code;
    private IKMessageType IKMessageType;
    private String message;
}
