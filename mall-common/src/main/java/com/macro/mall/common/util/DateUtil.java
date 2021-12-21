package com.macro.mall.common.util;

import com.google.common.base.Joiner;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description DateUtil
 * @date 2021/12/20 16:19
 */
public class DateUtil {
    public static String ZH_CN_PATTERN_DATE = "yyyy-MM-dd";
    public static String ZH_CN_PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static SimpleDateFormat yyyy_MM_dd_WithLineSDF = new SimpleDateFormat(ZH_CN_PATTERN_DATE);
    public static SimpleDateFormat yyyy_MM_dd_HH_mm_ss_WithLineSDF = new SimpleDateFormat(ZH_CN_PATTERN_DATETIME);

    /**
     * 获取多少天之前 或者 之后的日期 23点59分59秒
     *
     * @param days 增减的天数
     * @return 年月日 时分秒 str
     */
    public static Date getDateAddOrSubtractDays(int days) {
        StringBuilder stringBuilder = new StringBuilder(getTargetDateStrAddOrSubtract(new Date(), days));
        stringBuilder.append(" 23:59:59");
        try {
            return parseDate(stringBuilder.toString(), ZH_CN_PATTERN_DATETIME);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final ThreadLocal<Map<String, SimpleDateFormat>> sdfMap = new ThreadLocal<>();

    public static Date parseDate(String date, String pattern) throws ParseException {
        if (sdfMap.get() == null) {
            sdfMap.set(new HashMap<>());
        }
        if (sdfMap.get().get(pattern) == null) {
            sdfMap.get().put(pattern, new SimpleDateFormat(pattern));
        }
        return sdfMap.get().get(pattern).parse(date);
    }

    public static String getTargetDateStrAddOrSubtract(Date date, int days) {
        Assert.isTrue((days > -365 && days < 365), "日期错误");
        long DaysOpt = 1000L * 60 * 60 * 24 * days;
        Calendar calendarTmp = Calendar.getInstance();
        calendarTmp.setTime(date);
        long currentDate = calendarTmp.getTimeInMillis() + DaysOpt;
        Date dateNew = new Date(currentDate);
        return yyyy_MM_dd_WithLineSDF.format(dateNew);
    }
}
