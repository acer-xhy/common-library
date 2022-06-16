package com.jgw.common_library.utils;

import android.text.TextUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 熊少武 on 2017/11/29 0029.
 */

public class FormatUtils {
    private static String[] numArray = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一"
            , "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三"
            , "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十", "三十一", "三十二"};

    /**
     * 将整数转换成汉字数字
     *
     * @param num 需要转换的数字
     * @return 转换后的汉字
     */
    public static String formatInteger(int num) {
        String str = "";
        if (num <= numArray.length - 1) {
            str = numArray[num];
        }
        return str;
    }

    public static String formatAddZero(int i) {
        if (i >= 10) {
            return String.valueOf(i);
        } else {
            return "0" + String.valueOf(i);
        }
    }

    public static String formatDate(long l) {
        String strPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        return sdf.format(l);
    }

    public static String formatMonthDay(String s) {

        Long l = Long.valueOf(s);
        String strPattern = "MM月dd日";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        return sdf.format(l);
    }

    public static String formatDate(Date l) {
        String strPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        return sdf.format(l);
    }

    public static String formatDate(Date l,String strPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        return sdf.format(l);
    }
    public static Date formatDate(String data) {
        String strPattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        Date format = null;
        try {
            format = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }
    public static Date formatDate(String data,String strPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        Date format = null;
        try {
            format = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }

    public static String formatDateHour(long start, long end) {
        String strPattern = "yyyy-MM-dd HH时";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        String str = "·" + sdf.format(start) + "-" + sdf.format(end);
        return str;
    }

    public static String formatDateToChineseCharacter(String date,String oldStrPattern,String newStrPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(oldStrPattern, Locale.CHINA);
        Date format = null;
        try {
            format = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat(newStrPattern, Locale.CHINA);
        return sdf.format(format);
    }

    public static String formatDateDay(long l) {
        String strPattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        return sdf.format(new Date(l));
    }

    public static String formatDateDay(String s) {

        String strPattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        return sdf.format(new Date(Long.valueOf(s)));
    }


    public static String formatDateWeek(long l) {
        String strPattern = "yyyy-MM-dd E";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        return sdf.format(new Date(l));
    }

    public static String formatDateWeekHour(long l) {
        String strPattern = "yyyy-MM-dd E HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        return sdf.format(new Date(l));
    }

    public static String formatDateStartAndEnd(long start, long end) {
        String strPattern = "yyyy.MM.dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);


        return sdf.format(new Date(start)) + "-" + sdf.format(new Date(end));
    }

    public static String formatVerifyDate(long l) {
        long currentData = System.currentTimeMillis();
        long l1 = l - currentData;
        String format = "";
        int limit = 7 * 24 * 3600 * 1000;
        if (l1 < limit) {
            return format;
        }
        String strPattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        format = sdf.format(new Date(l - limit)) + "后开始审核";
        return format;
    }


    public static String formatLastDate(long l) {
        long time = (System.currentTimeMillis() - l) / 1000;

        if (time > (60 * 60 * 24 * 30 * 12)) {
            return "一年之前";
        }

        if (time > 60 * 60 * 24 * 30) {
            int month = (int) (time / (60 * 60 * 24 * 30));
            return month + "个月之前";
        }


        if (time > 60 * 60 * 24) {
            int day = (int) (time / (60 * 60 * 24));
            return day + "天之前";
        }
        if (time > 60 * 60) {
            int hour = (int) (time / (60 * 60));
            return hour + "小时之前";
        }
        if (time > 60) {
            int minute = (int) (time / 60);
            return minute + "分钟之前";
        }
        return "1分钟以内";
    }

    public static String formatAfterDate(long l) {
        long time = (l - System.currentTimeMillis()) / 1000;

        if (time > 60 * 60 * 24) {
            int day = (int) (time / (60 * 60 * 24));
            return "还剩" + day + "天";
        }

        if (time > 60 * 60) {
            int hour = (int) (time / (60 * 60));
            return "还剩" + hour + "小时";
        }
        if (time > 60) {
            int hour = (int) (time / 60);
            return "还剩" + hour + "分钟";
        }
        return "1分钟以后过期";
    }

    public static String formatPrice(String price) {
        price = TextUtils.isEmpty(price) ? "0" : price;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(Double.valueOf(price));
    }

    public static String formatPrice(double price) {

        DecimalFormat df = new DecimalFormat("#.##");
        return "¥" + df.format(price);
    }

    public static String nomalPrice(double price) {
        int b = (int) price;
        if (price == b) {
            return "¥" + b;
        } else {
            return "¥" + new DecimalFormat("#.##").format(price);
        }
    }

    public static String nomalPriceFullBuy(int num, double price) {
        int b = (int) price;
        if (price == b) {
            return "拍" + num + "件" + b + "元";
        } else {
            return "拍" + num + "件" + new DecimalFormat("#.##").format(price) + "元";
        }
    }

    public static String nomalPrice_(double price) {
        int b = (int) price;
        if (price == b) {
            return "" + b;
        } else {
            return "" + new DecimalFormat("#.##").format(price);
        }
    }

    public static String nomalLowPrice(double price) {
        int b = (int) price;
        if (price == b) {
            return "¥" + b;
        } else {
            String s;
            try {
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                decimalFormat.setRoundingMode(RoundingMode.FLOOR);
                s = "¥" + decimalFormat.format(price);
            } catch (Exception e) {
                s = "";
            }
            return s;
        }
    }

    public static String nomalDouble(double price) {
        if (price == (int) price) {
            return String.valueOf((int) price);
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(price);
        }
    }

    public static String formatPriceToNum(double price) {
        if (price == (int) price) {
            return String.valueOf((int) price);
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(price);
        }
    }

    public static String formatCouponTime(String startTime, String endTime) {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return "";
        }


        long start = 0;
        long end = 0;
        try {
            start = Long.parseLong(startTime);
            end = Long.parseLong(endTime);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (start <= 0 || end <= 0) {
            return "";
        }
        String strPattern = "yyyy.MM.dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
        Date start_date = new Date();
        start_date.setTime(start);
        Date end_date = new Date();
        end_date.setTime(end);
//        String[] start = startTime.split(" ");
//        String[] end = endTime.split(" ");
//        String startStr = start[0].replace("-",".");
//        String endStr = end[0].replace("-",".");
        return "使用期限：" + sdf.format(start_date) + "-" + sdf.format(end_date);
    }

    public static String formatCouponStrTime(String startTime, String endTime) {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return "";
        }
//
//
//        long start = 0;
//        long end = 0;
//        try {
//            start = Long.parseLong(startTime);
//            end = Long.parseLong(endTime);
//
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        if (start <= 0 || end <= 0) {
//            return "";
//        }
//        String strPattern = "yyyy.MM.dd";
//        SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
//        Date start_date = new Date();
//        start_date.setTime(start);
//        Date end_date = new Date();
//        end_date.setTime(end);
        String[] start = startTime.split(" ");
        String[] end = endTime.split(" ");
        String startStr = start[0].replace("-", ".");
        String endStr = end[0].replace("-", ".");
        return "使用期限：" + startStr + "-" + endStr;
    }

    public static boolean isPriceDouble(String price) {

        Pattern p = Pattern.compile("^(\\d+\\.\\d{0,2})|(\\d+)$");
        Matcher m = p.matcher(price);
        boolean matches = m.matches();
        return matches;
    }

    public static String formatPriceYuan(Double price) {
        if (price == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(price) + "元";
    }

    public static double formatPriceToDouble(String price) {
        price = TextUtils.isEmpty(price) ? "0" : price;
        return Double.valueOf(price);
    }

    public static String NumberToChinese(int mDay) {

        return numArray[mDay];
    }

    /**
     * 金额转化
     */
    public static String getNumber(Integer money) {
        if (money == null || money == 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String result = df.format((float) money / 100);
        result = result.replaceAll("0+?$", "");//去掉后面无用的零
        result = result.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        return result;
    }

    public static String getNumber(Double money) {
        if (money == null || money == 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String result = df.format(money / 100);
        result = result.replaceAll("0+?$", "");//去掉后面无用的零
        result = result.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        return result;
    }
}
