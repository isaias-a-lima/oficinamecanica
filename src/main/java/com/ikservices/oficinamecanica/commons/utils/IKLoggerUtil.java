package com.ikservices.oficinamecanica.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class IKLoggerUtil {

    public static Logger getLogger(Class clazz) {
        return LoggerFactory.getLogger(clazz.getName());
    }

    public static String getLoggerID() {
        return UUID.randomUUID().toString();
    }
}
