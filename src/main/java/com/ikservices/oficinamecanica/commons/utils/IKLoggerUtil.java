package com.ikservices.oficinamecanica.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class IKLoggerUtil {

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
            return "json_object_not_created";
        }
    }
}
