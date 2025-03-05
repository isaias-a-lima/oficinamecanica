package com.ikservices.oficinamecanica.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class IKLoggerUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(IKLoggerUtil.class);

    public static Logger getLogger(Class clazz) {
        return LoggerFactory.getLogger(clazz.getName());
    }

    public static String getLoggerID() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + "-" + UUID.randomUUID().toString();
    }

    public static String parseJSON(Object object) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "json_object_not_created: " + e.getMessage();
        }
    }

    //TODO To finish this method
    public static String generateInParamsMessage(List<Object> objects) {
        String type = "";
        String name = "";
        for (Object object : objects) {
            type = objects.getClass().getTypeName();
            name = objects.getClass().getName();
        }
        return "Testing";
    }
}
