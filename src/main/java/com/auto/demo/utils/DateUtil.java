package com.auto.demo.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 当前周的周一和周日
     *
     * @param date 日期
     * @return
     */
    public static Date[] days_week(Date date) {
        //DAY_OF_WEEK-1 对应周几
        Date[] weeks = new Date[2];
        int[] week = {7, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        //date是否为周一
        if (week[day - 1] != 1) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - week[day - 1] + 1);
        }
        weeks[0] = calendar.getTime();
        weeks[1] = new Date(calendar.getTime().getTime() + (long) 6 * 24 * 60 * 60 * 1000);
        return weeks;
    }

    /***
     * 当前月的第一天和最后一天
     * @param date
     * @return
     */
    public static Date[] days_month(Date date) {
        Date[] months = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        months[0] = calendar.getTime();
        //下月第一天
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
        months[1] = calendar.getTime();
        return months;
    }

    /***
     * 月份周
     * @param month 月份
     * @return
     */
    public static String[] weeks(String month) {
        StringBuffer stringBuffer = new StringBuffer("");
        //DAY_OF_WEEK-1 对应周几
        int[] week = {7, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance();
        Calendar _calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.toDate(month, "yyyy-MM"));
        _calendar.setTime(DateUtil.toDate(month, "yyyy-MM"));
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int weeks = 4;
        if (week[day - 1] != 1) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - week[day - 1] + 1);
            weeks++;
        }
        for (int i = 1; i <= weeks; i++) {
            stringBuffer.append(DateUtil.toDate(calendar.getTime(), "yyyy-MM-dd") + ",");
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 6);
            stringBuffer.append(DateUtil.toDate(calendar.getTime(), "yyyy-MM-dd") + ",");
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        }
        if (calendar.get(Calendar.MONTH) == _calendar.get(Calendar.MONTH)) {
            stringBuffer.append(DateUtil.toDate(calendar.getTime(), "yyyy-MM-dd") + ",");
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 6);
            stringBuffer.append(DateUtil.toDate(calendar.getTime(), "yyyy-MM-dd") + ",");
        }
        stringBuffer.setLength(stringBuffer.length() - 1);
        return stringBuffer.toString().split(",");
    }

    /***
     * 日期月份对比
     * @param month
     * @param _month
     * @param format
     * @return
     */
    public static int monthCompare(String month, String _month, String format) {
        Date date = DateUtil.toDate(month, format);
        Date _date = DateUtil.toDate(_month, format);
        Calendar calendar = Calendar.getInstance();
        Calendar _calendar = Calendar.getInstance();
        calendar.setTime(date);
        _calendar.setTime(_date);
        return calendar.get(Calendar.MONTH) - _calendar.get(Calendar.MONTH);
    }

    /**
     * 天数差
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Integer days(Date beginDate, Date endDate) {
        int days = (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    public static String strDays_(Date beginDate, Date endDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - beginDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        if (day != 0) {
            return day + "天" + (hour != 0 ? hour + "小时" : "");
        }
        if (min == 0) {
            min = 1;
        }
        return (hour == 0 ? "" : hour + "小时") + min + "分钟";
    }

    public static String strDays(Date beginDate, Date endDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - beginDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        if (day != 0) {
            return day + "天";
        }
        if (hour != 0) {
            return hour + "小时";
        }
        if (min != 0) {
            return min + "分钟";
        }
        return "1分钟";
    }

    /***
     * 当月第一天和最后一天
     * @param format
     * @return
     */
    public static String[] monthDate(String format) {
        String[] date = {"", ""};
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        date[0] = dateFormat.format(c.getTime());
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        //ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        date[1] = dateFormat.format(ca.getTime());
        return date;
    }

    /***
     * 当月第一天和最后一天
     * @param format 日期格式
     * @param month 月份
     * @return
     */
    public static String[] monthDate(String month, String format) {
        String[] date = {"", ""};
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.setTime(toDate(month, "yyyy-MM"));
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        date[0] = dateFormat.format(c.getTime());
        //获取当前月最后一天
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        date[1] = dateFormat.format(c.getTime());
        return date;
    }

    public static String[] dayDate(String format, int day) {
        String[] date = {"", ""};
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.DATE) < day)
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        date[0] = dateFormat.format(c.getTime());
        date[1] = dateFormat.format(new Date());
        return date;
    }

    public static String[] weekDate(String format) {
        String[] date = {"", ""};
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        date[1] = dateFormat.format(c.getTime());
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 7);
        date[0] = dateFormat.format(c.getTime());
        return date;
    }

    public static String toTime(Date date) {
        SimpleDateFormat fdateFormater = new SimpleDateFormat("HH:mm:ss");
        return fdateFormater.format(date);
    }

    public static String toDate(Date date) {
        SimpleDateFormat fdateFormater = new SimpleDateFormat("M月d日");
        return fdateFormater.format(date) + "（" + getWeekOfDate(date) + "）";
    }

    public static String toDate(String date, String originalformat, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(originalformat);
        SimpleDateFormat fdateFormater = new SimpleDateFormat(format);
        Date _date = null;
        try {
            _date = sdf.parse(date);
            return fdateFormater.format(_date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String toDate(Date date, String format) {
        SimpleDateFormat fdateFormater = new SimpleDateFormat(format);
        return fdateFormater.format(date);
    }

    public static String toLastWeek(Date date) {
        SimpleDateFormat fdateFormater = new SimpleDateFormat("M月d日");
        Date _date = new Date(date.getTime() - (long) 6 * 24 * 60 * 60 * 1000);
        return fdateFormater.format(_date) + "-" + fdateFormater.format(date);
    }

    public static Date addDate(Date date, int days) {
        return new Date(date.getTime() + (long) days * 24 * 60 * 60 * 1000);
    }

    public static Date LastWeek(Date date) {
        return new Date(date.getTime() - (long) 6 * 24 * 60 * 60 * 1000);
    }

    public static Date LastYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        return calendar.getTime();
    }

    public static Date LastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar.getTime();
    }

    public static Date Yesterday(Date date) {
        return new Date(date.getTime() - (long) 1 * 24 * 60 * 60 * 1000);
    }

    public static String toNextWeek(Date date) {
        SimpleDateFormat fdateFormater = new SimpleDateFormat("M月d日");
        Date _date = new Date(date.getTime() + (long) 6 * 24 * 60 * 60 * 1000);
        return fdateFormater.format(date) + "-" + fdateFormater.format(_date);
    }

    public static Date NextWeek(Date date) {
        return new Date(date.getTime() + (long) 6 * 24 * 60 * 60 * 1000);
    }

    public static String toNextMonth(Date date) {
        SimpleDateFormat fdateFormater = new SimpleDateFormat("M月d日");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        return fdateFormater.format(date) + "-" + fdateFormater.format(calendar.getTime());
    }

    public static String toLastMonth(Date date) {
        SimpleDateFormat fdateFormater = new SimpleDateFormat("M月d日");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return fdateFormater.format(calendar.getTime()) + "-" + fdateFormater.format(date);
    }

    public static Date NextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        return calendar.getTime();
    }

    public static Date toDate(Date date, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(field, calendar.get(field) + value);
        return calendar.getTime();
    }

    public static boolean firstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE) == 1 ? true : false;
    }

    /**
     * 当天起止时间
     *
     * @return
     */
    public static Date[] timeDay() {
        Date[] time = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);
        time[0] = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        time[1] = calendar.getTime();
        return time;
    }

    public static Date toDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date _date = null;
        try {
            _date = sdf.parse(date);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return _date;
    }

    private static String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    public static Date getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date beginOfDate = cal.getTime();
        return beginOfDate;
    }


    public static String hikTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        sdf = new SimpleDateFormat("HH:mm:ss");
        String timeStr = sdf.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append(dateStr).append("T").append(timeStr).append("+08:00");
        return sb.toString();
    }

    // 2019-07-17T08:00:22+08:00
    public static String hikTime(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        String t = dateStr.replace("T", " ").replace("+08:00", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sdf.parse(t);
            return t;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date hikTime(String dateStr, String formate) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        String t = dateStr.replace("T", " ").replace("+08:00", "");
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        try {
            Date parse = sdf.parse(t);
            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String s = DateUtil.hikTime("2019-07-17T08:00:22+08:00");
        System.out.println(s);
    }
    /**
     * 获取日期年份
     * @param date 日期
     * @return
     */
    public static Integer getYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return Integer.valueOf(sdf.format(date));
    }
    /**
     * 功能描述：返回月
     *
     * @param date
     *            Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日期
     *
     * @param date
     *            Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
        /**
         * 功能描述：返回小时
         *
         * @param date
         *            日期
         * @return 返回小时
         */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

}
