package com.ikservices.oficinamecanica.commons.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IKMessageType {
    NONE(0, "None"),
    SUCCESS(1, "Success"),
    WARNING(2, "Warning"),
    ERROR(3, "Error");

    private final Integer code;
    private final String description;

    public static IKMessageType getByCode(int code) {
        for (IKMessageType value : IKMessageType.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return IKMessageType.NONE;
    }

    public static IKMessageType getByDescription(String description) {
        for (IKMessageType value : IKMessageType.values()) {
            if (value.description.equalsIgnoreCase(description)) {
                return value;
            }
        }
        return IKMessageType.NONE;
    }
}
