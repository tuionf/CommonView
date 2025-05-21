package com.example.myapplication.utils; // Adjust package if necessary

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

    private static final String TAG = "DateTimeUtils";

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * Formats a Date object into a string using the default date-time pattern (yyyy-MM-dd HH:mm:ss).
     * Uses UTC timezone for formatting.
     *
     * @param date The Date object to format.
     * @return The formatted date string, or null if the input date is null.
     */
    public static String formatDateTimeUTC(Date date) {
        return formatDate(date, DEFAULT_DATETIME_FORMAT, TimeZone.getTimeZone("UTC"));
    }

    /**
     * Formats a Date object into a string using the default date-time pattern (yyyy-MM-dd HH:mm:ss).
     * Uses the device's default timezone for formatting.
     *
     * @param date The Date object to format.
     * @return The formatted date string, or null if the input date is null.
     */
    public static String formatDateTimeDefaultTZ(Date date) {
        return formatDate(date, DEFAULT_DATETIME_FORMAT, TimeZone.getDefault());
    }
    
    /**
     * Formats a Date object into a string using a specified pattern and timezone.
     *
     * @param date     The Date object to format.
     * @param pattern  The pattern to use for formatting (e.g., "yyyy-MM-dd HH:mm:ss").
     * @param timeZone The TimeZone to use for formatting.
     * @return The formatted date string, or null if the input date or pattern is null.
     */
    public static String formatDate(Date date, String pattern, TimeZone timeZone) {
        if (date == null || pattern == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
            if (timeZone != null) {
                sdf.setTimeZone(timeZone);
            }
            return sdf.format(date);
        } catch (Exception e) {
            Log.e(TAG, "Error formatting date: " + date + " with pattern: " + pattern, e);
            return null;
        }
    }

    /**
     * Parses a date string using the default date-time pattern (yyyy-MM-dd HH:mm:ss) into a Date object.
     * Assumes the string is in UTC.
     *
     * @param dateString The date string to parse.
     * @return The parsed Date object, or null if parsing fails.
     */
    public static Date parseDateTimeUTC(String dateString) {
        return parseDate(dateString, DEFAULT_DATETIME_FORMAT, TimeZone.getTimeZone("UTC"));
    }
    
    /**
     * Parses a date string using the default date-time pattern (yyyy-MM-dd HH:mm:ss) into a Date object.
     * Assumes the string is in the device's default timezone.
     *
     * @param dateString The date string to parse.
     * @return The parsed Date object, or null if parsing fails.
     */
    public static Date parseDateTimeDefaultTZ(String dateString) {
        return parseDate(dateString, DEFAULT_DATETIME_FORMAT, TimeZone.getDefault());
    }

    /**
     * Parses a date string using a specified pattern and timezone into a Date object.
     *
     * @param dateString The date string to parse.
     * @param pattern    The pattern to use for parsing.
     * @param timeZone   The TimeZone the dateString is in.
     * @return The parsed Date object, or null if the input string or pattern is null, or if parsing fails.
     */
    public static Date parseDate(String dateString, String pattern, TimeZone timeZone) {
        if (dateString == null || pattern == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
            if (timeZone != null) {
                sdf.setTimeZone(timeZone);
            }
            return sdf.parse(dateString);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date string: " + dateString + " with pattern: " + pattern, e);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error parsing date string: " + dateString, e);
            return null;
        }
    }
}
