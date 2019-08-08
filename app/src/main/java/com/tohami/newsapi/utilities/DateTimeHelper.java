package com.tohami.newsapi.utilities;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateTimeHelper {
    /**
     * parses date from old format, and returns a date formatted with the new format
     * uses default locale
     *
     * @param oldFormat
     * @param newFormat
     * @param dateStr
     * @return
     */
    public static String formatDate(String oldFormat, String newFormat, String dateStr) {
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        SimpleDateFormat fmt = new SimpleDateFormat(oldFormat, Locale.ENGLISH);
        Date date = null;
        try {
            date = fmt.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }

        SimpleDateFormat fmtOut = new SimpleDateFormat(newFormat, Locale.ENGLISH);
        return fmtOut.format(date);
    }

    public static String formatDate(String oldFormat, String newFormat, String dateStr, Locale oldLocale, Locale newLocale) {
        if (oldLocale == null) {
            return formatDate(oldFormat, newFormat, dateStr);
        }

        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        SimpleDateFormat fmt = new SimpleDateFormat(oldFormat, oldLocale);
        Date date = null;
        try {
            date = fmt.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }

        SimpleDateFormat fmtOut = new SimpleDateFormat(newFormat, newLocale);
        return fmtOut.format(date);
    }

    /**
     * Converts The date object to String
     *
     * @param date   The date object
     * @param format The format to display the date String
     */
    public static String convertDateToString(Date date, String format) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormatter.format(date);
    }

    public static String convertDateToStringForServer(Date date, String format) {
        Locale localeObject = Locale.ENGLISH;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format, localeObject);
        return dateFormatter.format(date);
    }

    /**
     * Converts the date String returned from server to Date object
     *
     * @param msJsonDateTime date String
     * @return Date Object
     */
    public static Date parseMsTimestampToDateWithFormat(String msJsonDateTime) {
        if (msJsonDateTime == null)
            return null;
        int startIndex = msJsonDateTime.indexOf("Date(") + 5;
        int endIndex = msJsonDateTime.indexOf(")");
        long ts = 0;
        int timeIndex = 0;
        // int sign = 0;
        if (msJsonDateTime.indexOf("+") > 0) {
            // sign = 1;
            timeIndex = msJsonDateTime.indexOf("+");
        } else if (msJsonDateTime.indexOf("-") > 0) {
            // sign = -1;
            timeIndex = msJsonDateTime.indexOf("-");
        }
        if (timeIndex > 0) {
            // int hours = Integer.parseInt(msJsonDateTime.substring(timeIndex +
            // 1, timeIndex + 3));
            // int minutes = Integer.parseInt(msJsonDateTime.substring(timeIndex
            // + 3, timeIndex + 5));
            ts = Long
                    .parseLong(msJsonDateTime.substring(startIndex, timeIndex));// -
            // (hours
            // *
            // 3600000
            // *
            // sign)
            // -
            // (minutes
            // *
            // 60000
            // *
            // sign);
        } else {
            ts = Long.parseLong(msJsonDateTime.substring(startIndex, endIndex));
        }
        return new Date(ts);
    }

    public static String serializeDateToMsTimestamp(final Date date) {
        Long ticks = date.getTime();
        return "/Date(" + ticks.toString() + ")/";
    }

    public static String timestampToDateString(long milliSeconds, String dateFormat) {
        try {
            // Create a DateFormatter object for displaying date in specified format.
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return formatter.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
