package com.maxclay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Vlad Glinskiy
 */
public final class DateUtils {

    public static final String DEFAULT_TO_DAY_FORMAT = "dd-MM-yyyy";
    public static final String DEFAULT_TO_SECOND_FORMAT = "dd-MM-yy:HH:mm:SS";


    private DateUtils() {
    }

    public static String format(Date date, String dateFormat) {
        if (date == null || dateFormat == null || dateFormat.isEmpty()) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }

    public static String formatToDay(Date date) {
        return format(date, DEFAULT_TO_DAY_FORMAT);
    }

    public static String formatToSecond(Date date) {
        return format(date, DEFAULT_TO_SECOND_FORMAT);
    }
}
