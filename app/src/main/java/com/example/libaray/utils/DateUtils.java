package com.example.libaray.utils;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtils {
    private static SimpleDateFormat todayFormate = new SimpleDateFormat("HH:mm", Locale.CHINA);
    private static SimpleDateFormat yesterdayFormate = new SimpleDateFormat("昨天HH:mm", Locale.CHINA);
    private static SimpleDateFormat dateFormate = new SimpleDateFormat("M月d日", Locale.CHINA);
    private static SimpleDateFormat normalMonthFormate = new SimpleDateFormat("MM/dd", Locale.CHINA);

    public static final long ONE_DAY = 24 * 60 * 60 * 1000;

    /**
     * 得到该时间戳对于现在已经过去了几天
     */
    public static int getDayOffset(long time) {
        if (time > 0) {
            time *= 1000;
            long timeOffset = System.currentTimeMillis() - time;
            if (timeOffset > 0) {
                return (int) ((timeOffset / ONE_DAY) + 1);
            }
        }
        return -1;
    }

    public static long getTodayStart() {
        return getCalendar().getTimeInMillis();//今天的开始时间
    }
    public static long getTodayEnd() {
        return getCalendar().getTimeInMillis() + ONE_DAY;//今天的结束时间
    }

    public static String getComplexTimeString(long time) {
        if (time <= 0) {
            return "error time";
        }
        time *= 1000;

        long hourMill = 3600 * 1000; //一小时的毫秒数
        long dayMill = 24 * hourMill; //一天的毫秒值
        long currentTime = System.currentTimeMillis();

        Calendar cal = getCalendar();
        long todayStart = cal.getTimeInMillis();//今天的开始时间
        long lastDayStart = todayStart - dayMill;//昨天的开始时间

        long intervalMill = currentTime - time; //当前时间和发布时间差值毫秒数
        if (intervalMill < hourMill) {//如果差值时间小于一小时，显示多少分钟
            long minute = (intervalMill % hourMill) / 1000 / 60;
            minute = minute > 0 ? minute : 1; //一分钟内显示一分钟
            return minute + "分钟前";
        }else if(time > todayStart && time < todayStart+dayMill){//今天且超过一小时
            long hour = (intervalMill % dayMill) / hourMill;
            hour = hour > 0 ? hour : 1;
            return hour + "小时前";
        }else if (time >= lastDayStart && time < todayStart) {
            return "昨天";
        }  else {//直接显示日期
            return dateFormate.format(new Date(time));
        }
    }

    /**
     * @return 格式：MM/dd HH:mm
     */
    public static String getPluginInnerTime(long time){
        if(time < 0 ) {
            return "";
        }
        time *= 1000 ;
        return getAllTime(time);
    }

    public static String getAllTime(long time){
        return dateFormate.format(time)+" "+todayFormate.format(time);
    }

    /**
     * 判断该时间戳是不是属于今天
     */
    public static boolean isToday(long time){
        long dayMill = 24 * 3600 * 1000; //一天的毫秒值
        Calendar cal = getCalendar();
        long todayStart = cal.getTimeInMillis();
        long todayEnd = todayStart + dayMill;
        return time > todayStart && time < todayEnd ;
    }

    public static String getGardenFormatTime(String time) {
        Calendar c = Calendar.getInstance();
        try {
            Date date1 = new SimpleDateFormat("yyyyMMdd", Locale.CHINA).parse(time);
            c.setTime(date1);
            Date date2 = new Date(c.getTimeInMillis() + 6 * ONE_DAY);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日", Locale.CHINESE);
            return dateFormat.format(date1) + "-" + dateFormat.format(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  "";
    }

    public static String getFormatRetainTime(long time) {
        if(time > 0) {
            long day = time / (24 * 60 * 60);
            long hour = (time / (60 * 60) - day * 24);
            long min = ((time / 60) - day * 24 * 60 - hour * 60);
            long s = (time - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            StringBuilder sBuffer = new StringBuilder();
            if (day > 0) {
                sBuffer.append(day).append("天");
                sBuffer.append(hour).append("小时");
            } else if (hour > 0) {
                sBuffer.append("0天");
                sBuffer.append(hour).append("小时");
            } else if (min > 0) {
                sBuffer.append("0天");
                sBuffer.append("1小时");
            } else if (s > 0) {
                sBuffer.append("0天");
                sBuffer.append("1小时");
            }
            return sBuffer.toString();
        } else {
            return "";
        }
    }

    /**
     * 两时间相距是否在 一个分钟之内
     * 传进来是 毫秒
     */
    public static boolean isOutOfOneMinute(long fromSecond, long toSecond){
        return Math.abs(toSecond - fromSecond) > 1000*60;
    }

    /**
     * 两时间相距是否在 30天之内
     * 传进来是s
     */
    public static boolean isOutOfOneMonth(long fromSecond, long toSecond) {
        return Math.abs(toSecond - fromSecond) > 60*60*24*30;
    }

    private static SimpleDateFormat allDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA);
    private static SimpleDateFormat allDateFormatExtra = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);

    public static String getAllFormatTime(long time) {
        return allDateFormat.format(time);
    }

    public static String getAllFormatTimeExtra(long time) {
        return allDateFormatExtra.format(time);
    }

    /**
     * 格式化时间为 MM 月 DD 日
     * @param time 时间戳
     */
    public static String formatDateWithMMDD(long time){
        return dateFormate.format(new Date(time));
    }

    /**
     * 根据时间戳来获取，该时间戳对应为星期几
     */
    public static String getWeekDayOfTime(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        return getWeekDayOfTime(calendar);
    }

    /**
     * 得到当前时间为星期几
     */
    private static String getWeekDay() {
        Calendar c = Calendar.getInstance();
        return getWeekDayOfTime(c);
    }

    /**
     * 微信聊天中的时间显示格式
     */
    public static String getTimeString(long time) {
        if (time <= 0) {
            return "error time";
        }
        time *= 1000;
        Calendar cal = getCalendar();
        long todayStart = cal.getTimeInMillis();
        long lastDayStart = todayStart - ONE_DAY;
        long tomorrowStart = todayStart + ONE_DAY;
        long weekStart = todayStart - 7 * ONE_DAY;

        //若是今天就显示：{HH:mm}
        if (time >= todayStart && time < tomorrowStart) {
            return todayFormate.format(new Date(time));
        }
        //昨天，显示：{昨天HH:mm}
        else if (time >= lastDayStart && time < todayStart) {
            return yesterdayFormate.format(new Date(time));
        }
        //前7天，显示：{星期几}
        else if (time >= weekStart) {
            return getWeekDay();
        }
        //7天后，显示{M月d日}
        else {
            return dateFormate.format(new Date(time));
        }
    }

    @NonNull
    private static String getWeekDayOfTime(Calendar c) {
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
                return "星期六";
            case Calendar.SUNDAY:
                return "星期日";
            default:
                return "未知星期";
        }
    }

    private static Calendar getCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
}
