package com.ikservices.oficinamecanica.commons.utils;

import java.util.Objects;

public class StringUtil {

    public static final String EMPTY = "";

    public static String validString(String value) {
        return Objects.nonNull(value) ? value : EMPTY;
    }
}
