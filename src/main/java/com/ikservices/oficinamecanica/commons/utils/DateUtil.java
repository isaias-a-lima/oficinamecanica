package com.ikservices.oficinamecanica.commons.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateUtil {

    public static LocalDate parseToLocalDate(String date) {
        return Objects.nonNull(date) && !date.isEmpty() ? LocalDate.parse(date) : null;
    }

    public static String parseToString(LocalDateTime date) {
        return Objects.nonNull(date) ? parseToString(date, "yyyy-MM-dd HH:mm:ss") : null;
    }

    public static String parseToString(LocalDateTime date, String pattern) {
        return Objects.nonNull(date) && Objects.nonNull(pattern) && !pattern.isEmpty() ? date.format(DateTimeFormatter.ofPattern(pattern)): null;
    }

    public static String parseToString(LocalDate date) {
        return Objects.nonNull(date) ? parseToString(date, "yyyy-MM-dd") : null;
    }

    public static String parseToString(LocalDate date, String pattern) {
        return Objects.nonNull(date) && Objects.nonNull(pattern) && !pattern.isEmpty() ? date.format(DateTimeFormatter.ofPattern(pattern)): null;
    }
}
