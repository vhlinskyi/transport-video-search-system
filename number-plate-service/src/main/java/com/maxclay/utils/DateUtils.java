package com.maxclay.utils;

import java.text.ParseException;
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

    /**
     * Converts string representation of date to instance of {@link java.util.Date} class.
     *
     * @param stringDate string representation of date.
     * @param dateFormat date format, such as "dd/MM/yyyy".
     * @return instance of {@link java.util.Date} class corresponding to the given string representation
     * of date and date format.
     * @throws ParseException signals that an error has been reached unexpectedly while parsing.
     */
    public static Date valueOf(String stringDate, String dateFormat) throws ParseException {

        if (stringDate == null || stringDate.isEmpty()) {
            throw new IllegalArgumentException("Date can not be empty");
        }

        if (dateFormat == null || dateFormat.isEmpty()) {
            throw new IllegalArgumentException("Date format can not be empty");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setLenient(false);

        Date date = simpleDateFormat.parse(stringDate);

        return date;
    }
}
