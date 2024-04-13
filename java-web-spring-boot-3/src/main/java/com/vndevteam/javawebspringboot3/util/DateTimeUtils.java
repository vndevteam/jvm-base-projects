package com.vndevteam.javawebspringboot3.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateTimeUtils {
    public static ZonedDateTime getNow() {
        return ZonedDateTime.now(TimeZone.getDefault().toZoneId());
    }

    public static String getNow(String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(getNow());
    }

    public static LocalDateTime toZone(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.atZone(zoneId).toLocalDateTime();
    }
}
