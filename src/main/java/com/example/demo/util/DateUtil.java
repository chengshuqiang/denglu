package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private Calendar calendar;

    public DateUtil(String sourceDate) throws ParseException {
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置日期格式
        Date date = sdf.parse(sourceDate);
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(date);*/
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public static String timestampToDate(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date.setTime(timestamp);
        return simpleDateFormat.format(date);
    }

    /**
     * 时间戳转换成日期时间
     * @param timestamp the milliseconds since January 1, 1970, 00:00:00 GMT.
     * @return 日期时间
     */
    public static String timestampToDateTime(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }

}
