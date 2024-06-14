package com.ikservices.oficinamecanica.commons.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
    NONE(0),
    SUCCESS(1),
    WARNING(2),
    ERROR(3);

    private final Integer code;

    public MessageType getByCode(Integer code) {
        MessageType[] values = MessageType.values();
        for (MessageType value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return MessageType.NONE;
    }
}
