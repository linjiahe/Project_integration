package com.blockchain.commune.utils;


import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public class NumberUtil {
    private static NumberUtil util = new NumberUtil();
    private int id;

    private NumberUtil() {
        this.id = 1000;
    }

    public static String getId() {
        long id = 0;
        synchronized (NumberUtil.util) {
            if (util.id > 9999) util.id = 1000;
            id = ++util.id;

        }
        return String.valueOf(id).substring(1);
    }


    public static String getFormatMobile(String mobile) {
        if (!StringUtils.isEmpty(mobile)) {
            String str1 = mobile.substring(0, 3);
            String str2 = mobile.substring(7);
            return str1 + "****" + str2;
        }
        return "";
    }

    public static String getUserCode(Long size) {

        String code = size + "";
        if (size < 10000000) {
            String str = "00000000";
            int a = str.length() - size.toString().length();
            String b = str.substring(0, a) + size;
            code = b;
        }
        return code;
    }

    public static String getStoreCode(Long size) {

        String code = size + "";
        if (size < 100000) {
            String str = "000000";
            int a = str.length() - size.toString().length();
            String b = str.substring(0, a) + size;
            code = b;
        }
        return code;
    }

    public static String getRegionId(Integer size) {

        String code = size + "";
        if (size < 9999) {
            String str = "0000";
            int a = str.length() - size.toString().length();
            String b = str.substring(0, a) + size;
            code = b;
        }
        return code;
    }

    public static boolean isNumber(String numString) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(numString).matches();
    }

    private static final Double MILLION = 10000.0;
    private static final Double MILLIONS = 1000000.0;
    private static final Double BILLION = 100000000.0;
    private static final String MILLION_UNIT = "万";
    private static final String BILLION_UNIT = "亿";

    /**
     * 将数字转换成以万为单位或者以亿为单位，因为在前端数字太大显示有问题
     *
     * @param amount 金额
     * @return
     */
    public static String amountConversion(double amount){
        //最终返回的结果值
        String result = String.valueOf(amount);
        //四舍五入后的值
        double value = 0;
        //转换后的值
        double tempValue = 0;
        //余数
        double remainder = 0;

        //金额大于1百万小于1亿
        if(amount > MILLIONS && amount < BILLION){
            tempValue = amount/MILLION;
            remainder = amount%MILLION;

            //余数小于5000则不进行四舍五入
            if(remainder < (MILLION/2)){
                value = formatNumber(tempValue,2,false);
            }else{
                value = formatNumber(tempValue,2,true);
            }
            //如果值刚好是10000万，则要变成1亿
            if(value == MILLION){
                result = zeroFill(value/MILLION) + BILLION_UNIT;
            }else{
                result = zeroFill(value) + MILLION_UNIT;
            }
        }
        //金额大于1亿
        else if(amount > BILLION){
            tempValue = amount/BILLION;
            remainder = amount%BILLION;

            //余数小于50000000则不进行四舍五入
            if(remainder < (BILLION/2)){
                value = formatNumber(tempValue,2,false);
            }else{
                value = formatNumber(tempValue,2,true);
            }
            result = zeroFill(value) + BILLION_UNIT;
        }else{
            result = zeroFill(amount);
        }
        return result;
    }
    /**
     * 对数字进行四舍五入，保留2位小数
     *
     * @param number 要四舍五入的数字
     * @param decimal 保留的小数点数
     * @param rounding 是否四舍五入
     * @return
     */
    public static Double formatNumber(double number, int decimal, boolean rounding){
        BigDecimal bigDecimal = new BigDecimal(number);

        if(rounding){
            return bigDecimal.setScale(decimal, RoundingMode.HALF_UP).doubleValue();
        }else{
            return bigDecimal.setScale(decimal,RoundingMode.DOWN).doubleValue();
        }
    }

    /**
     * 对四舍五入的数据进行补0显示，即显示.00
     *
     */
    public static String zeroFill(double number){
        String value = String.valueOf(number);

        if(value.indexOf(".")<0){
            value = value + ".00";
        }else{
            String decimalValue = value.substring(value.indexOf(".")+1);

            if(decimalValue.length()<2){
                value = value + "0";
            }
        }
        return value;
    }


}
