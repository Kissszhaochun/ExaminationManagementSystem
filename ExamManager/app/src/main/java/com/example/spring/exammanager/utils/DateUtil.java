package com.example.spring.exammanager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class DateUtil {

    public static String getDateString(long time) {
        Date date = new Date(time * 1000l);
        String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        return dateString;
    }

}
