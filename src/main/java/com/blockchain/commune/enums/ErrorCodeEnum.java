package com.blockchain.commune.enums;

public class ErrorCodeEnum {

    public static final int PARAMETERROR = 404;//参数错误
    public static final int DBERROR = 505;//数据库操作失败
    public static final int EXCEPTION = 510;//自定义异常或未知异常
    public static final int TOKENERROR = 400;//token验证错误
    public static final int IDERROR = 401;//id验证错误
    public static final int AUTHERROR = 402;//api_auth验证错误
    public static final int TOKENREPEATERROR = 403;//不同设备登陆
    public static final int SMSCODEERROR = 550;//短信验证码错误
    public static final int NETWORKERROR = 500;//服务器异常

}
