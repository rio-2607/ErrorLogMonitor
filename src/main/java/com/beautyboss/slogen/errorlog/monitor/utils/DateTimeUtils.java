package com.beautyboss.slogen.errorlog.monitor.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author : Slogen
 * Date   : 2020-02-17 16:19
 */
public class DateTimeUtils {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static String localDateTime2String(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);
        return time.format(formatter);
    }

    public static String now() {
        return localDateTime2String(LocalDateTime.now());
    }

}
