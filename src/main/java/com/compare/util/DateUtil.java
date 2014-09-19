package com.compare.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cxw
 * Date: 11-7-21
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {

    public static final String FMT_YMD_HMS_MS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String FMT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    public static final String FMT_YMD = "yyyy-MM-dd";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String FMT_DOT_YMD = "yyyy.MM.dd";

    public static final String FMT_YMD2 = "yyyy-MM";

    public static Date getDate(String timeStr, String reg) {
        SimpleDateFormat format = new SimpleDateFormat(reg);
        Date date = null;
        try {
            date = format.parse(timeStr);
        } catch (ParseException e) {
        }
        return date;
    }

    public static Date getDate(String timeStr) {
        return getDate(timeStr, FMT_YMD);
    }

    public static DateTime getDateTime(String dateName) {
        return getDateTime(dateName, FMT_YMD_HMS);
    }

    public static DateTime getDateTimeShort(String dateName) {
        return getDateTime(dateName, FMT_YMD);
    }

    public static DateTime getTomorrow() {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(1);
        dateTime = dateTime.withHourOfDay(0);
        dateTime = dateTime.withMinuteOfHour(0);
        dateTime = dateTime.withSecondOfMinute(0);
        return dateTime;
    }

    public static DateTime getLastDate(DateTime dateTime) {
        dateTime = dateTime.withHourOfDay(23);
        dateTime = dateTime.withMinuteOfHour(59);
        dateTime = dateTime.withSecondOfMinute(59);
        return dateTime;
    }

    public static DateTime getDateTimeShort2(String dateName) {
        return getDateTime(dateName, FMT_YMD2);
    }

    public static DateTime getDateTime(String dateName, String reg) {
        DateTimeFormatter dateTimeFormatter = org.joda.time.format.DateTimeFormat.forPattern(reg);
        return dateTimeFormatter.parseDateTime(dateName);
    }

    public static DateTime getMonth(String date) {
        if (date == null)
            return null;
        else {
            String[] dates = date.split("-");
            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            DateTime oldDateTime = new DateTime(year, month, 1, 0, 0, 0, 0);
            DateTime startDateTime = oldDateTime.minusMonths(1);
            startDateTime = startDateTime.dayOfMonth().withMaximumValue();

            DateTime endDateTime = oldDateTime.plusMonths(1);
            endDateTime = endDateTime.dayOfMonth().withMinimumValue();

            System.out.println(startDateTime);
            System.out.println(endDateTime);

            return null;
        }
    }

    public static Boolean checkDate(String date) {
        try {
            if (StringUtils.isNotBlank(date)) {
                new SimpleDateFormat(FMT_YMD).parse(date);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String dateToString(DateTime date) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String dateStr = df.format(date);
        return date.toString(FMT_YMD);
    }

    public static void main(String[] args) {
//        System.out.print(new DateTime(Long.parseLong("1332444564000")));
//        HandleCpsInForm form = new HandleCpsInForm();
//        HandleCpsController handleCpsController = new HandleCpsController();
//        form.setSd(Long.parseLong("1332444564"));
//        handleCpsController.handleError(form,"ss");
//        System.out.print(form.getSd());
    }
}
