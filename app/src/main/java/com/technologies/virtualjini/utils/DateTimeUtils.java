package com.technologies.virtualjini.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by ZOARDER AL MUKTADIR on 11/21/2016.
 */

public class DateTimeUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "hh:mm a";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd, hh:mm a";
    public static final String DATE_TIME_FORMAT_CREATED_AT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_SP = "yyyy-MM-dd HH:mm:ss";
    public static final String DURATION_FORMAT = "y M d H m s";
    public static final String DURATION_FORMAT_CREATED_AT = "y M d H m s";
    private static final String[] DURATION_FORMAT_TOKENS = {" Year", " Month", " Day", " Hour", " Minute", " Second"};
    private static final String[] DURATION_FORMAT_TOKENS_CREATED_AT = {" Year", " Month", " Day", " Hour", " Minute", " Second"};

    public static String getDateTimeFromMilliSeconds(long milliSeconds, String dateTimeFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
        formatter.setLenient(false);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static long getMilliSecondsFromDateTime(String dateTime, String dateTimeFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
        formatter.setLenient(false);

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(formatter.parse(dateTime));
//            calendar.set(Calendar.SECOND, 59);
//            calendar.set(Calendar.MILLISECOND, 999);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    public static void setDateFromDatePicker(final Activity activity, int year, int month, int day, final String dateFormat, final TextView textView) {

        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonthOfYear, int selectedDayOfMonth) {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(selectedYear, selectedMonthOfYear, selectedDayOfMonth);
                        Date date = new Date(calendar.getTimeInMillis());
                        textView.setText(formatter.format(date));
                    }
                }, year, month, day);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }

    public static void setTimeFromTimePicker(final Activity activity, int hour, int minute, final int second, final String timeFormat, final TextView textView) {
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                calendar.set(Calendar.SECOND, second);
                Time time = new Time(calendar.getTimeInMillis());
                textView.setText(formatter.format(time));
            }
        }, hour, minute, false);//Yes 24 hour time
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public static String getDurationFromMilliSeconds1(long milliSeconds, String durationFormat) {
        String durationString = DurationFormatUtils.formatDuration(milliSeconds, durationFormat);
        StringTokenizer stringTokenizer = new StringTokenizer(durationString);

        durationString = "";
        for (int i = 0; stringTokenizer.hasMoreElements(); i++) {
            int n = Integer.parseInt(stringTokenizer.nextToken());
            if (n > 0) {
                if (!durationString.isEmpty()) {
                    durationString += ", ";
                }
                durationString = durationString + n + DURATION_FORMAT_TOKENS[i];
                if (n > 1) {
                    durationString += "s";
                }
            }
        }
        return durationString;
    }

    public static String getDurationFromMilliSeconds2(long milliSeconds, String durationFormat) {
        String durationString = DurationFormatUtils.formatDuration(milliSeconds, durationFormat);
        String[] durationStringToken = durationString.split(" ");
        int day = Integer.parseInt(durationStringToken[2]);
//        durationString = durationString.substring(4, durationString.length() - 1);
        if (day > 0) {
            int month = day / 30;
            day = day % 30;
            if (month > 0) {
                int year = month / 12;
                month = month % 12;
                durationStringToken[0] = year + "";
                durationStringToken[1] = month + "";
                durationStringToken[2] = day + "";
            }
        }

        ShowLog.e("durationString: ", durationString);
//        StringTokenizer stringTokenizer = new StringTokenizer(durationString);

        durationString = "";
        for (int i = 0; i < durationStringToken.length; i++) {
            int n = Integer.parseInt(durationStringToken[i]);
            if (n > 0) {
                if (n > 1) {
                    durationString = n + DURATION_FORMAT_TOKENS_CREATED_AT[i] + "s ago";
                } else {
                    durationString = "1" + DURATION_FORMAT_TOKENS_CREATED_AT[i] + " ago";
                }
                break;
            }
        }
        return durationString;
    }

    public static boolean isValidDateTimeFormat(String dateTime, String dateTimeFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
        formatter.setLenient(false);

        Calendar calendar = Calendar.getInstance();
        Boolean mark = false;
        try {
            calendar.setTime(formatter.parse(dateTime));
            mark = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mark;
    }
}
