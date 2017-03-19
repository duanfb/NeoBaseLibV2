package com.neo.duan.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static String getNiceTime(Long time) {
        Date t = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(t);
    }

    public static String getFullTime(Long time) {
        Date t = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        return dateFormat.format(t);
    }

    public static String getStrTime(long time) {
        Date t = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(t);
    }

    /**
     * 获取时间间距，以天为单位
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getDayInterval(long startTime, long endTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        try {
            Date dateStart = dateFormat.parse(TimeUtils.getFullTime(startTime));
            Date dateEnd = dateFormat.parse(TimeUtils.getFullTime(endTime));

            long interval = dateEnd.getTime() - dateStart.getTime();
            long days = interval / (1000 * 60 * 60 * 24);

            return days;
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
        return 0;
    }

    /**
     * 获取时间间距，以毫秒为单位
     *
     * @param startTime:开始的时间,如2016-06-18 10:00:00
     * @param endTime：结束的时间,如2016-06-18   12:00:00
     * @return
     */
    public static long getMillsecondInterval(String startTime, String endTime) {
        long interval = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            java.util.Date now = df.parse("2004-01-02 11:30:24");
//            java.util.Date date =df.parse("2004-01-02 11:30:20");
            java.util.Date now = df.parse(endTime);
            java.util.Date date = df.parse(startTime);
            long distance = now.getTime() - date.getTime();
            long day = distance / (24 * 60 * 60 * 1000);// * 24 * 60 * 60 * 1000;
            long hour = (distance / (60 * 60 * 1000) - day * 24);// * 60 * 60 * 1000;
            long min = ((distance / (60 * 1000)) - day * 24 * 60 - hour * 60);// * 60 * 1000;
            long s = (distance / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);// * 1000;


            interval = day * 24 * 60 * 60 * 1000;
            interval += hour * 60 * 60 * 1000;
            interval += min * 60 * 1000;
            interval += s * 1000;

//            System.out.println("相隔的时间："+day+"天"+hour+"小时"+min+"分"+s+"秒");
//            System.out.println("相隔的时间："+interval+"豪秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return interval;
    }

    /**
     * 获取分钟，秒，毫秒
     *
     * @param time
     * @return
     */
    public static String getMescTime(Long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SSS");
        String timeStr = dateFormat.format(date);

        String[] strArray = timeStr.split(":");
        String lastElement = strArray[strArray.length - 1].substring(0, 1);
        StringBuffer timeBuffer = new StringBuffer();
        for (int i = 0; i < strArray.length - 1; i++) {
            timeBuffer.append(strArray[i] + ":");
        }
        timeBuffer.append(lastElement);
        return timeBuffer.toString();
    }

    /**
     * 获取分钟,秒
     *
     * @param time
     * @return
     */
    public static String getSecondTime(Long time) {
        String targetTime = "";
        try {
            Date date = new Date(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
            String timeStr = dateFormat.format(date);

            String[] strArray = timeStr.split(":");
            // String lastElement = strArray[strArray.length - 1].substring(0, 1);
            StringBuffer timeBuffer = new StringBuffer();
            for (int i = 0; i < strArray.length; i++) {
                timeBuffer.append(strArray[i] + ":");
            }
            targetTime = timeBuffer.substring(0, timeBuffer.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return targetTime;
    }

    /**
     * 最少看到分钟
     *
     * @param time
     * @return
     */
    public static String getDataTime(Long time) {
        String s = "", day, hour, minute, second;
        long t = time / (24 * 3600 * 1000);
        long lx = time % (24 * 3600 * 1000);
        long x = lx / (3600 * 1000);
        long lf = lx % (3600 * 1000);
        long f = lf / (60 * 1000);
        long lm = lf % (60 * 1000);
        long m = lm / 1000;
        if (t < 1) {

        } else if (t < 10) {
            // day = "0" + t;
            day = "" + t;
            s += day + "天";
        } else {
            day = String.valueOf(t);
            s += day + "天";
        }
        if (x < 1) {

        } else if (x < 10) {
            hour = "0" + x;
            // s += hour + "小时";
            s += hour + ":";
        } else {
            hour = String.valueOf(x);
            // s += hour + "小时";
            s += hour + ":";
        }
        if (f < 1) {
            s += "00:";
        } else if (f < 10) {
            minute = "0" + f;
            // s += minute + "分";
            s += minute + ":";
        } else {
            minute = String.valueOf(f);
            // s += minute + "分";
            s += minute + ":";
        }
        if (m < 1) {
            // s += "00"+"秒";
            s += "00";
        } else if (m < 10) {
            second = "0" + m;
            // s += second + "秒";
            s += second + "";
        } else {
            second = String.valueOf(m);
            // s += second + "秒";
            s += second + "";
        }

        return s;
        // day + "天" + hour + "小时" + minute + "分" + second + "秒";
    }

    /**
     * 最少看到分钟
     *
     * @param time
     * @return
     */
    public static String getDataTime4(Long time) {
        String s = "", day, hour, minute, second;
        long t = time / (24 * 3600 * 1000);
        long lx = time % (24 * 3600 * 1000);
        long x = lx / (3600 * 1000);
        long lf = lx % (3600 * 1000);
        long f = lf / (60 * 1000);
        long lm = lf % (60 * 1000);
        long m = lm / 1000;
        if (t < 1) {

        } else if (t < 10) {
            // day = "0" + t;
            day = "" + t;
            s += day + "天";
        } else {
            day = String.valueOf(t);
            s += day + "天";
        }
        if (x < 1) {

        } else if (x < 10) {
            hour = "0" + x;
            // s += hour + "小时";
            s += hour + "时";
        } else {
            hour = String.valueOf(x);
            // s += hour + "小时";
            s += hour + "时";
        }
        if (f < 1) {
            s += "00分";
        } else if (f < 10) {
            minute = "0" + f;
            // s += minute + "分";
            s += minute + "分";
        } else {
            minute = String.valueOf(f);
            // s += minute + "";
            s += minute + "分";
        }
        if (m < 1) {
            // s += "00"+"秒";
            s += "00秒";
        } else if (m < 10) {
            second = "0" + m;
            // s += second + "秒";
            s += second + "秒";
        } else {
            second = String.valueOf(m);
            // s += second + "秒";
            s += second + "秒";
        }

        return s;
    }


    public static String getDataTime5(Long time) {
        String s = "", day, hour, minute, second;
        long t = time / (24 * 3600 * 1000);
        long lx = time % (24 * 3600 * 1000);
        long x = lx / (3600 * 1000);
        long lf = lx % (3600 * 1000);
        long f = lf / (60 * 1000);
        long lm = lf % (60 * 1000);
        long m = lm / 1000;
        if (t < 1) {

        } else if (t < 10) {
            // day = "0" + t;
            day = "" + t;
            s += day + "天";
        } else {
            day = String.valueOf(t);
            s += day + "天";
        }
        if (x < 1) {
            hour = "00时";
            s += hour;
        } else if (x < 10) {
            hour = "0" + x;
            s += hour + "时";
        } else {
            hour = String.valueOf(x);
            s += hour + "时";
        }
        if (f < 1) {
            s += "00分";
        } else if (f < 10) {
            minute = "0" + f;
            // s += minute + "分";
            s += minute + "分";
        } else {
            minute = String.valueOf(f);
            s += minute + "分";
        }
        if (m < 1) {
            s += "00秒";
        } else if (m < 10) {
            second = "0" + m;
            s += second + "秒";
        } else {
            second = String.valueOf(m);
            s += second + "秒";
        }

        return s;
    }

    /**
     * 最少看到小时
     *
     * @param time
     * @return
     */
    public static String getDataTime2(Long time) {
        String s = "", day, hour, minute, second;
        long t = time / (24 * 3600 * 1000);
        long lx = time % (24 * 3600 * 1000);
        long x = lx / (3600 * 1000);
        long lf = lx % (3600 * 1000);
        long f = lf / (60 * 1000);
        long lm = lf % (60 * 1000);
        long m = lm / 1000;
        if (t < 1) {

        } else if (t < 10) {
            // day = "0" + t;
            day = "" + t;
            s += day + "天";
        } else {
            day = String.valueOf(t);
            s += day + "天";
        }
        if (x < 1) {
            s += "00:";
        } else if (x < 10) {
            hour = "0" + x;
            // s += hour + "小时";
            s += hour + ":";
        } else {
            hour = String.valueOf(x);
            // s += hour + "小时";
            s += hour + ":";
        }
        if (f < 1) {
            s += "00:";
        } else if (f < 10) {
            minute = "0" + f;
            // s += minute + "分";
            s += minute + ":";
        } else {
            minute = String.valueOf(f);
            // s += minute + "分";
            s += minute + ":";
        }
        if (m < 1) {
            // s += "00"+"秒";
            s += "00";
        } else if (m < 10) {
            second = "0" + m;
            // s += second + "秒";
            s += second + "";
        } else {
            second = String.valueOf(m);
            // s += second + "秒";
            s += second + "";
        }

        return s;
        // day + "天" + hour + "小时" + minute + "分" + second + "秒";
    }

    /**
     * [3.0特卖倒计时显示修改]
     *
     * @param time
     * @return day-hour / hour-minute
     */
    public static String getDataTime3(Long time) {
        String s = "", day, hour, minute, second;
        long t = time / (24 * 3600 * 1000);
        long lx = time % (24 * 3600 * 1000);
        long x = lx / (3600 * 1000);
        long lf = lx % (3600 * 1000);
        long f = lf / (60 * 1000);
        long lm = lf % (60 * 1000);
        long m = lm / 1000;
        //天
        if (t < 1) {

        } else if (t < 10) {
            // day = "0" + t;
            day = "" + t;
            s += day + "天";
        } else {
            day = String.valueOf(t);
            s += day + "天";
        }

        //小时
        if (x < 1) {
            s += "00:";
        } else if (x < 10) {
            hour = "0" + x;
            s += hour + "小时";

        } else {
            hour = String.valueOf(x);
            s += hour + "小时";

        }


        if (t < 1) {
            //分钟
            if (f < 1) {

            } else if (f < 10) {
                minute = "0" + f;
                s += minute + "分";
            } else {
                minute = String.valueOf(f);
                s += minute + "分";
            }
        }


        return s;
        // day + "天" + hour + "小时" + minute + "分";
    }

    public static String[] getTimeArray(Long time) {
        String day = null, hour = null, minute = null, second = null;
        long t = time / (24 * 3600 * 1000);
        long lx = time % (24 * 3600 * 1000);
        long x = lx / (3600 * 1000);
        long lf = lx % (3600 * 1000);
        long f = lf / (60 * 1000);
        long lm = lf % (60 * 1000);
        long m = lm / 1000;
        if (t < 1) {

        } else if (t < 10) {
            // day = "0" + t;
            day = "" + t;
            // s += day + "天";
        } else {
            day = String.valueOf(t);
            // s += day + "天";
        }
        if (x < 1) {
            hour = "00";
        } else if (x < 10) {
            hour = "0" + x;
            // s += hour + "小时";
            // s += hour + ":";
        } else {
            hour = String.valueOf(x);
            // s += hour + "小时";
            // s += hour + ":";
        }
        if (f < 1) {
            minute = "00";
        } else if (f < 10) {
            minute = "0" + f;
            // s += minute + "分";
            // s += minute + ":";
        } else {
            minute = String.valueOf(f);
            // s += minute + "分";
            // s += minute + ":";
        }
        if (m < 1) {
            // s += "00"+"秒";
            second = "00";
        } else if (m < 10) {
            second = "0" + m;
            // s += second + "秒";
            // s += second + "";
        } else {
            second = String.valueOf(m);
            // s += second + "秒";
            // s += second + "";
        }

        // return s;
        // day + "天" + hour + "小时" + minute + "分" + second + "秒";
        String timeArray[] = new String[3];
        timeArray[0] = hour;
        timeArray[1] = minute;
        timeArray[2] = second;
        return timeArray;
    }

    public static String getTimeForDay(Long time) {
        String s = "", day, hour, minute, second;
        long t = time / (24 * 3600 * 1000);
        if (t < 10) {
            // day = "0" + t;
            day = "" + t;
            s += day + "天";
        } else {
            day = String.valueOf(t);
            s += day + "天";
        }
        return s;

        // Date date = new Date(time * 1000);
        // SimpleDateFormat format = new SimpleDateFormat("dd");
        // // System.out.println("format==" + format.format(date));
        // return format.format(date);
    }

    /**
     * 时间：String 转 long
     *
     * @param timeStr
     * @param defaultTime
     * @return
     */
    public static long convertTime(String timeStr, long defaultTime) {
        if (TextUtils.isEmpty(timeStr)) {
            return defaultTime;
        }
        try {
            return Long.valueOf(timeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultTime;
    }

    /**
     * 获取年份
     *
     * @param millis 毫秒值
     * @return
     */
    public static int getYear(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(millis));
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @param millis 毫秒值
     * @return
     */
    public static int getMonth(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(millis));
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取日
     *
     * @param millis 毫秒值
     * @return
     */
    public static int getDateOfDay(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(millis));
        return cal.get(Calendar.DATE);
    }


    public static int getDay(long millis) {
        long totalSeconds = millis / 1000;
        int day = (int) (totalSeconds / 60 / 60 / 24);// 天
        return day;
    }

    public static int getHour(long millis) {
        long totalSeconds = millis / 1000;
        int hour = (int) ((totalSeconds / 60 / 60) % 24);// 时
        return hour;
    }

    public static int getMinute(long millis) {
        long totalSeconds = millis / 1000;
        long totalMinutes = totalSeconds / 60;//分
        return (int) (totalMinutes % 60);
    }

    public static int getSecond(long millis) {
        long totalSeconds = millis / 1000;
        int second = (int) (totalSeconds % 60);// 秒
        return second;
    }

    public static String getDayStr(long millis) {
        String day = getDay(millis) + "";
        if (day.length() == 1) {
            day = "0" + day;
        }
        return day;
    }

    /**
     * 获取小时，包含天
     *
     * @param millis
     * @return
     */
    public static String getDayAsHour(long millis) {
        int hour = getHour(millis);
        String hourStr = getDay(millis) * 24 + hour + "";
        if (hourStr.length() == 1) {
            hourStr = "0" + hour;
        }
        return hourStr;
    }

    public static String getHourStr(long millis) {
        String hour = getHour(millis) + "";
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        return hour;
    }

    public static String getMinuteStr(long millis) {
        String minute = getMinute(millis) + "";
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        return minute;
    }

    public static String getSecondStr(long millis) {
        String second = getSecond(millis) + "";
        if (second.length() == 1) {
            second = "0" + second;
        }
        return second;
    }

    /**
     * 获取年份
     *
     * @param dateStr 格式为：2016-9-20 12:00:00
     * @return
     */
    public static String getYearStr(String dateStr) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            String year = cal.get(Calendar.YEAR) + "";
            if (year.length() == 1) {
                year = "0" + year;
            }
            return year;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "00";
    }

    /**
     * 获取月份
     *
     * @param dateStr 格式为：2016-9-20 12:00:00
     * @return
     */
    public static String getMonthStr(String dateStr) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            String month = cal.get(Calendar.MONTH) + 1 + "";
            if (month.length() == 1) {
                month = "0" + month;
            }
            return month;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "00";
    }

    /**
     * 获取日期：20号
     *
     * @param dateStr 格式为：2016-9-20 12:00:00
     * @return
     */
    public static String getDayStr(String dateStr) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            String day = cal.get(Calendar.DATE) + "";
            if (day.length() == 1) {
                day = "0" + day;
            }
            return day;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "00";
    }

    /**
     * 获取日期：小时
     * yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr 格式为：2016-9-20 12:00:00
     * @return
     */
    public static String getHourStr(String dateStr) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            String hour = cal.get(Calendar.HOUR_OF_DAY) + "";
            if (hour.length() == 1) {
                hour = "0" + hour;
            }
            return hour;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "00";
    }

    /**
     * 获取日期：分钟
     * yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr 格式为：2016-9-20 12:00:00
     * @return
     */
    public static String getMinuteStr(String dateStr) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            String minute = cal.get(Calendar.MINUTE) + "";
            if (minute.length() == 1) {
                minute = "0" + minute;
            }
            return minute;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "00";
    }
}
