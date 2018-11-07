package com.blockchain.commune.utils;

import java.util.Random;

public class RandomStringUtil{

    private static String range = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static synchronized String getRecommendedCode(){
        Random random = new Random();
        StringBuffer result = new StringBuffer();

        for ( int i = 0; i < 6; i++ ){
            result.append( range.charAt( random.nextInt( range.length() ) ) );
        }

        return result.toString().toUpperCase();
    }


    //顺序表
    static String orderStr="";
    static{
        for(int i=33;i<127;i++){
            orderStr+=Character.toChars(i)[0];
        }
    }
    //判断是否有顺序
    public static boolean isOrder(String str){
        if(!str.matches("((\\d)|([a-z])|([A-Z]))+")){
            return false;
        }
        return orderStr.contains(str);
    }
    //判断是否相同
    public static boolean isSame(String str){
        String regex=str.substring(0,1)+"{"+str.length()+"}";
        return str.matches(regex);
    }

}