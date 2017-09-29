package com.pyf.woku.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/29
 */
public class DateFormatHelper {

    public enum DateFormat {
        DATE_1("MM-dd"), DATE_2("MM月dd日"), DATE_3("MM月dd日 HH:mm"), DATE_4("yyyy-MM-dd HH:mm"), DATE_5("yyyy-MM-dd"), DATE_6("EEEE"), DATE_7(
                "yyyy-MM-dd"), DATE_8("yyyy-MM-dd hh:mm:ss"), DATE_9("MM-dd HH:mm"), DATE_10("yyyy/MM/dd"), DATE_11("HH:mm"), DATE_12("yyyy年MM月dd日");
        private String value;

        private DateFormat(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 将时间措格式化为任意指定格式
     * @param timeSpace
     * @param dateFormat
     * @return
     */
    public static String formatDate(String timeSpace, DateFormat dateFormat) {
        /**
         * 把默认时区改为东八区
         * */
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat formate = null;
        Long time = Long.parseLong(timeSpace);
        Date date = new Date(time);
        formate = new SimpleDateFormat(dateFormat.getValue());
        return formate.format(date);
    }
}
