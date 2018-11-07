package com.blockchain.commune.utils;




import com.blockchain.commune.custommodel.DateModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    private static List<Date> getAllDayOftheMonth(Date date) {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        int month = cal.get(Calendar.MONTH);
        while (cal.get(Calendar.MONTH) == month) {
            list.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
        return list;
    }

    public static String yearString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(date);
    }


    public static DateModel dateString(Date date) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        String year = sdf1.format(date);

        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        String month = sdf2.format(date);

        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
        String day = sdf3.format(date);

        SimpleDateFormat sdf4 = new SimpleDateFormat("HH");
        String hour = sdf4.format(date);

        SimpleDateFormat sdf5 = new SimpleDateFormat("mm");
        String minute = sdf5.format(date);

        SimpleDateFormat sdf6 = new SimpleDateFormat("ss");
        String second = sdf6.format(date);

        DateModel dateModel = new DateModel();
        dateModel.setYear(year);
        dateModel.setMonth(month);
        dateModel.setDay(day);
        dateModel.setHour(hour);
        dateModel.setMinute(minute);
        dateModel.setSecond(second);

        return dateModel;
    }


    public static String dayString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(date);
    }

    public static String monthString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(date);
    }

    public static String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DAY_OF_MONTH, 1);//这个时间就是日期往后推一天的结果
        date = calendar.getTime();
        return date;
    }

    public static Date getLastDay(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DAY_OF_MONTH, -1);//这个时间就是日期往后推一天的结果
        date = calendar.getTime();
        return date;
    }

    public static Date dayStartTime(Date date) {
        String formatString = dateFormat(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(formatString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date dayAndTime(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dayAndTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(date);

    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        return sdf.format(date);
    }

    // 获得当天0点时间
    public static Date getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    //获取当天23：59：59
    public static Date todayLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    // 获得本周一0点时间
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    // 获得本月第一天0点时间
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    //获取距离当天结束剩余的毫秒数
    public static long getTimeEndSeconds() {
        Calendar curDate = Calendar.getInstance();
        Calendar nextDayDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DATE)+1, 0, 0, 0);
        return (nextDayDate.getTimeInMillis() - curDate.getTimeInMillis())/1000;
    }
}
