package com.ikservices.oficinamecanica.commons.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IKMessageType {
    NONE(0),
    SUCCESS(1),
    WARNING(2),
    ERROR(3);

    private final Integer code;

    public static IKMessageType getByCode(Integer code) {
        IKMessageType[] values = IKMessageType.values();
        for (IKMessageType value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return IKMessageType.NONE;
    }
}
