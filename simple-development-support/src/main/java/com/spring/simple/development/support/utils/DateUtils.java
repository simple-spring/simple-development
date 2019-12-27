package com.spring.simple.development.support.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * desc:    时间相关处理工具类
 */
public class DateUtils {

    /**
     * 根据时间判断星期
     *
     * @param time
     * @return
     */
    public static int getWeek(long time) {
        int[] weekOfDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 获取当前时间在一天中的第几分钟
     *
     * @return
     */
    public static int getCurrentMin() {
        int min = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        min = h * 60 + m;
        return min;
    }

    /**
     * 获取当前时间的小时
     *
     * @return
     */
    public static String getCurrentHour() {
        SimpleDateFormat hourSdf = new SimpleDateFormat("HH");
        String hour = hourSdf.format(new Date(System.currentTimeMillis()));
        return hour;
    }

    /**
     * 根据日期获取小时
     *
     * @param time
     * @return
     */
    public static int getHour(long time) {
        SimpleDateFormat hourSdf = new SimpleDateFormat("HH");
        String hour = hourSdf.format(new Date(time));
        return Integer.parseInt(hour);
    }

    public static String getMin(long time) {
        SimpleDateFormat hourSdf = new SimpleDateFormat("HH:mm");
        return hourSdf.format(new Date(time));
    }

    public static String getDate(long time) {
        SimpleDateFormat dateDf = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateDf.format(new Date(time));
        return date;
    }

    public static String getStringByDate(Date date) {
        SimpleDateFormat dateDf = new SimpleDateFormat("yyyyMMdd");
        String dateString = dateDf.format(date);
        return dateString;
    }

    public static String getStrByDate(Date date) {
        SimpleDateFormat dateDf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateDf.format(date);
        return dateString;
    }

    public static String getDateManilaFormate(Date date) {
        SimpleDateFormat dateDf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateDf.format(date);
        return dateString;
    }

    public static String getStrFormateDate(Date date) {
        SimpleDateFormat dateDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateDf.format(date);
        return dateString;
    }

    public static String getDateFormate(long time, String format) {
        SimpleDateFormat dateDf = new SimpleDateFormat(format);
        String date = dateDf.format(new Date(time));
        return date;
    }


    public static String getCurrentTime() {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateSdf.format(new Date());
    }

    public static String getCurrentTimestamp() {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateSdf.format(new Date());
    }

    public static String parseTime(long time) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateSdf.format(new Date(time));
    }

    public static String getDay(String orderTime) {
        orderTime = orderTime.replace("-", "");
        return orderTime.substring(0, 8);
    }

    public static Date getDateByTime(Long time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        return format.parse(d);
    }

    public static Date getDateBydays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();

    }

    public static String getSimpleDay(String orderTime) {
        return orderTime.substring(0, 10);
    }

    public static String getHour(String orderTime) {
        orderTime = orderTime.replace("-", "");
        return orderTime.substring(9, 11);
    }

    /**
     * 判断与当前时间相比 是否在24小时之内
     *
     * @param time
     * @return
     */
    public static boolean isIn24Hour(long time) {
        long current = System.currentTimeMillis();
        if (current - time > 1000 * 60 * 60 * 24) {
            return false;
        }
        return true;
    }

    /**
     * 判断于当前时间相比,是否在指定小时之内
     *
     * @param time
     * @param hour
     * @return
     */
    public static boolean isInHours(long time, int hour) {
        long current = System.currentTimeMillis();
        if (current - time > 1000 * 60 * 60 * hour) {
            return false;
        }
        return true;
    }

    public static long parseLong(String orderTime) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateSdf.parse(orderTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static Date SimpleParseDate(String orderTime) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateSdf.parse(orderTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseDate(String orderTime) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateSdf.parse(orderTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseDateTemp(String orderTime) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = dateSdf.parse(orderTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取指定日期的差值日期
     *
     * @param timeStr
     * @param day
     * @return
     */
    public static String getOneDay(String timeStr, int day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long oneDay = 1000 * 60 * 60 * 24;
        Date time = null;
        try {
            time = df.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String reportTime = df.format(new Date(time.getTime() + oneDay * day));
        return reportTime;
    }

    /**
     * 获取上一个月
     */
    public static String getBeforeMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return df.format(c.getTime());
    }

    /**
     * 获取昨天日期
     */
    public static String getBeforeDay() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        return df.format(c.getTime());
    }

    /**
     * 获取昨天日期
     */
    public static String getYesterdayDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        return df.format(c.getTime());
    }

    /**
     * 获取指定天数之前或之后的日期
     */
    public static String getBeforeDate(int day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, day);
        return df.format(c.getTime());
    }

    /**
     * 获取?后的日期
     *
     * @return
     */
    public static String getBeforeDayString(int days) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, +days);
        return df.format(c.getTime());
    }

    /**
     * 获取?后的日期
     *
     * @return
     */
    public static Date getBeforeDayDate(int days) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, +days);
        return c.getTime();
    }

    public static String getSimpleBeforeDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        return df.format(c.getTime());
    }

    /**
     * 获取当天零点的时间
     *
     * @return
     */
    public static long getCurrentLongTime() {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date date = new Date();
        String str = dateSdf.format(date);
        return parseLong(str);
    }

    /**
     * 将当天日期设置为凌晨
     *
     * @return
     */
    public static Date getMorningDate(Date day) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String strDay = dateSdf.format(day);
        try {
            day = dateSdf.parse(strDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static String getDay() {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return dateSdf.format(date);
    }

    public static String getDayFromDate(String dateStr) throws ParseException {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMdd");
        Date parse = dateSdf.parse(dateStr);
        return dateSdf.format(parse);
    }


    public static String getDateFromDate(Date date) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
        return dateSdf.format(date);
    }

    public static String getDateFromDateTem(Date date) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy/MM/dd");
        return dateSdf.format(date);
    }

    /**
     * 判断当前时间日期是否是今天
     *
     * @param time
     */
    public static boolean isToday(String time) {
        boolean flag = false;
        time = getDay(time);
        String today = getDay();
        flag = time.equals(today);
        return flag;
    }

    /**
     * 判断当前时间日期是否是今天
     *
     * @param time
     */
    public static boolean isYesterday(String time) {
        boolean flag = false;
        time = getDay(time);
        String yesterday = getBeforeDayString(0);
        flag = time.equals(yesterday);
        return flag;
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateSdf.format(new Date());
    }

    public static String getTimeAfterMonth(String time, Integer month) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateSdf.parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, month);
            Date resultDay = c.getTime();
            time = dateSdf.format(resultDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获得当前日期
     *
     * @return
     */
    public static String getCurTime() {
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        return df.format(new Date());
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    public static String getCurDateTime(String fromatStr) {
        DateFormat df = new SimpleDateFormat(fromatStr);
        return df.format(System.currentTimeMillis());
    }

    /**
     * 判断该日期属于哪一天
     *
     * @param day
     * @return
     */
    public static int queryWhichDay(String day) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date;
        try {
            date = df.parse(day);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //指定日期
        Calendar cal = df.getCalendar();
        return cal.get(Calendar.DATE);
    }

    /**
     * 获得指定日期的下一个月
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedMonthAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, month + 1);

        String dayAfter = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return dayAfter;
    }

    /**
     * 计算一个日期与今日，相差多少天
     *
     * @param specifiedDay
     * @return
     */
    public static String differentDaysByMillisecond(String specifiedDay) {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        int days = 0;
        try {
            Date date = format.parse(specifiedDay);
            days = (int) ((today.getTime() - date.getTime()) / (1000 * 3600 * 24));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(days);
    }

    public static Date SimpleParseMMDDYYYY(String orderTime) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateSdf.parse(orderTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getStrMMDDYYYYFormateDate(Date date) {
        SimpleDateFormat dateDf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = dateDf.format(date);
        return dateString;
    }

    public static String getStrMMDDFormateDate(Date date) {
        SimpleDateFormat dateDf = new SimpleDateFormat("MM-dd");
        String dateString = dateDf.format(date);
        return dateString;
    }

    /**
     * 计算一个日期与今日相差天数
     * @param date
     * @return
     */
    public static int getIntervalDaysByToday(Date date){
    	return getIntervalDays(new Date(), date);
    }
    
    /**
     * 计算两个日期相差天数
     * @param date
     * @param otherDate
     * @return
     */
    public static int getIntervalDays(Date date, Date otherDate){
    	Date dateTmp = DateUtils.SimpleParseDate(DateUtils.getStrByDate(date));
    	Date otherDateTmp = DateUtils.SimpleParseDate(DateUtils.getStrByDate(otherDate));
    	int days = 0;
    	try {
            days = (int) ((dateTmp.getTime() - otherDateTmp.getTime()) / (1000 * 3600 * 24));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 根据当前时间，添加或减去指定的时间量。例如，要从当前日历时间减去 5 天，可以通过调用以下方法做到这一点：
     * add(Calendar.DAY_OF_MONTH, -5)。
     * @param date 指定时间
     * @param num  为时间添加或减去的时间天数
     * @return
     */
    public static Date getBeforeOrAfterDate(Date date, int num) {
        Calendar calendar = Calendar.getInstance();//获取日历
        calendar.setTime(date);//当date的值是当前时间，则可以不用写这段代码。
        calendar.add(Calendar.DATE, num);
        Date d = calendar.getTime();//把日历转换为Date
        return d;
    }

    /**
     * 获取一周前日期 yyyy-MM-dd
     * @return
     */
    public static String getMostRecentWeekDate() {
        Date firstDay = getBeforeOrAfterDate(new Date(),-6);
        return getDateFromDate(firstDay);
    }

    /**
     * 获取过去7天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static List<String> getDays(int intervals) {
        List<String> pastDaysList = new ArrayList<>();
        for (int i = intervals -1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }
    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    private static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 得到本日的上月时间 如果当日为2007-9-1,那么获得2007-8-1
     */
    public static Date getMonthStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * 得到本日的上周时间 如果当日为2007-9-1,那么获得2007-8-1
     */
    public static Date getWeekStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        return cal.getTime();
    }

    /**
     * 获取昨天日期
     */
    public static Date getMonthEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    /**
     * 获取下一天日期
     * @param date
     * @return
     */
    public static Date getNext(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    
}

