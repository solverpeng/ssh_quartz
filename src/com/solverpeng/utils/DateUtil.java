package com.solverpeng.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static Date stringToDate(String strDate) {
        return stringToDate(strDate, DATE_FORMAT_YYYY_MM_DD);
    }

    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) return null;
        try {
            return new SimpleDateFormat(pattern).parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date date) {
        return dateToString(date, DATE_FORMAT_YYYY_MM_DD);
    }

    public static String dateToString(Date date, String pattern) {
        if (date == null) return null;
        try {
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
