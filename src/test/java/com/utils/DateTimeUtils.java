package com.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * 
 * @author : 李广彬 (liguangbin@ideadata.com.cn)
 * @createDate: 2016年8月9日 上午9:36:15
 * @upateLog: Name Date Reason/Contents
 * @log: ---------------------------------------
 * @log: *** **** ****
 */
public class DateTimeUtils {
    public static SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat SDF_YYYY_MM_DD_2 = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat SDF_YYYY_MM = new SimpleDateFormat("yyyy-MM");
    public static SimpleDateFormat SDF_MM_DD = new SimpleDateFormat("MM-dd");
    public static SimpleDateFormat SDF_MM_DD_HH_MM = new SimpleDateFormat("MM-dd HH:mm");
    public static SimpleDateFormat SDF_YYYY_MM_DD_2HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat SDF_YYYY_MM_DD_2HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat SDF_HH_MM = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat SDF_HH_MM_SS = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat SDF_MM_DD_WEEK = new SimpleDateFormat("MM-dd E");
    public static SimpleDateFormat SDF_MM_DD_CN = new SimpleDateFormat("MM月dd日");
    public static SimpleDateFormat SDF_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat CNSDF_YYYY_MM_DD = new SimpleDateFormat("yyyy年MM月dd日");
    public static SimpleDateFormat CNSDF_YYYY_MM = new SimpleDateFormat("yyyy年MM月");
    public static SimpleDateFormat CNSDF_MM_DD_CN = new SimpleDateFormat("MM月dd日");
    
    /**
     * 时间转换函数(由Stirng转换成Date对象类型)
     * 
     * @param dstr （日期串:yyyy-MM-dd HH:mm:ss)
     * @param sdf （SimpleDateFormat对象，可从常量中取到，如果为空默认为yyyy-MM-dd格式)
     * @return Date
     * @throws ParseException
     * @throws Exception
     */
    public static Date strToDate(String dateStr, SimpleDateFormat sdf) throws ParseException {
        Date date = null;
        if (dateStr != null && !"".equals(dateStr)) {
            if (sdf != null) {
                date = sdf.parse(dateStr);
            } else {
                date = SDF_YYYY_MM_DD.parse(dateStr);
            }
        }
        return date;
    }
    
    /**
     * 将日期转为默认格式
     * 
     * @param dateStr
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public static Date strToDateDefault(String dateStr) throws ParseException {
        Date date = null;
        if (dateStr != null && !"".equals(dateStr)) {
            date = DateTimeUtils.strToDate(dateStr, null);
        }
        return date;
    }
    
    /**
     * 时间转换函数(由Stirng转换成Date对象类型)
     * 
     * @param date （日期对象)
     * @param sdf （SimpleDateFormat对象，可从常量中取到，如果为空默认为yyyy-MM-dd格式)
     * @return String
     * @throws Exception
     */
    public static String dateToStr(Date date, SimpleDateFormat sdf) {
        String dateStr = null;
        if (date != null) {
            if (sdf != null) {
                dateStr = sdf.format(date);
            } else {
                dateStr = SDF_YYYY_MM_DD.format(date);
            }
        }
        return dateStr;
    }
    
    public static String dateToStrYM(Date date) {
        return DateTimeUtils.dateToStr(date, SDF_YYYY_MM);
    }
    
    /**
     * 将日期格式化成指定的日期格式
     * 
     * @param date
     * @param sdf
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public static Date dateFormat(Date date, SimpleDateFormat sdf) throws ParseException {
        Date formatDate = null;
        if (date != null) {
            if (sdf != null) {
                formatDate = DateTimeUtils.strToDate(sdf.format(date), sdf);
            } else {
                formatDate = DateTimeUtils.strToDate(SDF_YYYY_MM_DD.format(date), sdf);
            }
        }
        
        return formatDate;
    }
    
    /**
     * 得到指定时间的字符串指定格式
     * 
     * @param date
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String dateToStrDefault(Date date) {
        String dateStr = null;
        if (date != null) {
            dateStr = SDF_YYYY_MM_DD.format(date);
        }
        
        return dateStr;
    }
    
    /**
     * 得到默认的日期时间的字符串格式
     * 
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateTimeToStrDefault(Date date) {
        String dateStr = null;
        if (date != null) {
            dateStr = SDF_YYYY_MM_DD_2HH_MM.format(date);
        }
        return dateStr;
    }
    
    /**
     * 将时间转为时间 的默认格式
     * 
     * @param date
     * @return
     * @throws Exception
     */
    public static String timeToStrDefault(Date date) {
        String dateStr = null;
        
        if (date != null) {
            dateStr = SDF_HH_MM_SS.format(date);
        }
        return dateStr;
    }
    
    /**
     * 得到当前的日期字符串
     * 
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String getCurrentDateStr() {
        return DateTimeUtils.dateToStrDefault(new Date());
        
    }
    
    /**
     * 得到指定日期的前多少天 如:9月3号的前一天是9月2号
     * 
     * @return
     * @throws ParseException
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    public static Date getDateBefore(Date date, Integer len) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - len);
        
        return cal.getTime();
    }
    
    @SuppressWarnings("static-access")
    public static Date getTimeBefore(Date date, Integer minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - minute);
        
        return cal.getTime();
    }
    
    /**
     * 得到指定日期的后多少天 如:9月3号的后一天是9月4号
     * 
     * @return
     * @throws ParseException
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    public static Date getDateAfter(Date date, Integer len) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + len);
        
        return cal.getTime();
    }
    
    @SuppressWarnings("static-access")
    public static Date getTimeAfter(Date date, Integer minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + minute);
        
        return cal.getTime();
    }
    
    /**
     * 得到一周的第一天
     * 
     * @return
     * @throws ParseException
     * @throws Exception
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    public static Date getFristDateOfWeekCn(Date date, SimpleDateFormat sdf) throws ParseException {
        int dayIndexInWeek;
        Date resultDate = null;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            dayIndexInWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayIndexInWeek == 1) {
                cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 6);
            } else {
                cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - dayIndexInWeek + 2);
            }
            
            if (sdf != null) {
                resultDate = DateTimeUtils.strToDate(sdf.format(cal.getTime()), sdf);
            } else {
                resultDate = cal.getTime();
            }
            
        }
        
        return resultDate;
    }
    
    /**
     * 得到一周的最后一天
     * 
     * @return
     * @throws ParseException
     * @throws Exception
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    public static Date getLastDateOfWeekCn(Date date, SimpleDateFormat sdf) throws ParseException {
        int dayIndexInWeek;
        Date resultDate = null;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            dayIndexInWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayIndexInWeek == 1) {
                
            } else {
                cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - dayIndexInWeek + 8);
            }
            
            if (sdf != null) {
                resultDate = DateTimeUtils.strToDate(sdf.format(cal.getTime()), sdf);
            } else {
                resultDate = cal.getTime();
            }
        }
        return resultDate;
    }
    
    /**
     * 取得当前日期是多少周
     * 
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        
        return c.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 取得当前日期是多少周
     * 
     * @param date
     * @return
     */
    public static int getWeekOfMonth(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        
        return c.get(Calendar.WEEK_OF_MONTH);
    }
    
    /**
     * 得到某一年周的总数
     * 
     * @param year
     * @return
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        
        return DateTimeUtils.getWeekOfYear(c.getTime());
    }
    
    /**
     * 得到某年某周的第一天
     * 
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        
        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);
        
        return DateTimeUtils.getFirstDayOfWeek(cal.getTime());
    }
    
    /**
     * 得到某年某周的最后一天
     * 
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        
        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);
        
        return DateTimeUtils.getLastDayOfWeek(cal.getTime());
    }
    
    /**
     * 取得指定日期所在周的第一天
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        
        return c.getTime();
    }
    
    /**
     * 取得一个月的第一天
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        return c.getTime();
    }
    
    /**
     * 取得一个月的最后一天
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(DateTimeUtils.getFirstDayOfMonth(date));
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }
    
    /**
     * 取得指定日期所在周的最后一天
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }
    
    /**
     * 取得当前日期所在周的第一天
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek() {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }
    
    /**
     * 取得当前日期所在周的最后一天
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek() {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }
    
    /**
     * 给定日期与当前时间，相差的天数 不准确:当两天相差不到24小时时会出问题
     * 
     * @param date
     * @return
     */
    public static int subduction_days(Date startDate, Date endDate) {
        Long days = (startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000);
        return days.intValue();
    }
    
    public static int subduction_days(Date endDate) {
        Date startDate = new Date();
        Long days = (startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000);
        return days.intValue();
    }
    
    /**
     * 转换日历函数到sql timestamp
     * 
     * @param date
     * @return
     */
    public static Timestamp convUtilCalendarToSqlTimestamp(Calendar date) {
        if (date == null) {
            return null;
        } else {
            return new Timestamp(date.getTimeInMillis());
        }
    }
    
    /**
     * 转换sql timestamp到日历函数
     * 
     * @param date
     * @return
     */
    public static Calendar convSqlTimestampToUtilCalendar(Timestamp date) {
        if (date == null) {
            return null;
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            return gc;
        }
    }
    
    /**
     * 得到两个日期中 较大的日期
     * 
     * @param date
     * @return
     */
    public static Date getMaxDate(Date date1, Date date2) throws Exception {
        if (date1 != null && date2 != null) {
            if (date1.getTime() > date2.getTime()) {
                return date1;
            } else {
                return date2;
            }
        } else {
            throw new Exception("比较对像不可为空！");
        }
    }
    
    /**
     * 得到两个日期中 较小的日期
     * 
     * @param date
     * @return
     */
    public static Date getMinDate(Date date1, Date date2) throws Exception {
        if (date1 != null && date2 != null) {
            if (date1.getTime() < date2.getTime()) {
                return date1;
            } else {
                return date2;
            }
        } else {
            throw new Exception("比较对像不可为空！");
        }
    }
    
    /**
     * 指定月里有多少周
     * 
     * @param year
     * @param month
     * @return
     * @throws Exception
     */
    public static Integer getWeekNumOfMonth(Integer year, Integer month) throws Exception {
        Integer weekNum = null;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.setMinimalDaysInFirstWeek(7);
        weekNum = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
        
        return weekNum;
    }
    
    /**
     * 得到指定日期一天中最大时间 如：2012-12-12 24:59:59
     * 
     * @param date
     * @return
     */
    @SuppressWarnings("static-access")
    public static Date getLastTimeOfDay(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }
    
    /**
     * 得到指定日期一天中最小时间 如：2012-12-12 00:00:00
     * 
     * @param date
     * @return
     */
    @SuppressWarnings("static-access")
    public static Date getFirstTimeOfDay(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    
    /**
     * 转化时间
     * 
     * @param createtime
     * @return
     */
    
    public static String dfdate(Date createtime) {
        String result = null;
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        long createTimeMillis = createtime.getTime() / 1000;
        
        long time = currentTimeMillis - createTimeMillis;
        if (time > 86400) {
            result = SDF_MM_DD_HH_MM.format(createtime);
            ;
        } else if (time > 3600) {
            result = (time / 3600) + "小时前";
        } else if (time > 60) {
            result = (time / 60) + "分钟前";
        } else if (time > 0) {
            result = time + "秒前";
        } else {
            result = "刚刚";
        }
        
        return result;
        
    }
    
    public static void main(String[] args) {
        // int year = 2009;
        // int week = 1;
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2012); // 2010年
        c.set(Calendar.MONTH, 1); // 6 月
        c.setMinimalDaysInFirstWeek(7);
        System.out.println(
                "------------" + c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月的天数和周数-------------");
        System.out.println("天数：" + c.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("周数：" + c.getActualMaximum(Calendar.WEEK_OF_MONTH));
        
    }
}
