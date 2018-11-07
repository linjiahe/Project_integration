package com.blockchain.commune.enums;

/**
 * Created by wrb on 2018/10/8
 */
public enum SmsTypeEnum {
    REGISTERED(1, "【人人公社】您正在注册人人公社，验证码为："),
    BIND_MAIL(2, "【人人公社】您正在绑定邮箱，验证码为："),
    FORGET_PSW(3, "【人人公社】您正在重置密码，验证码为："),
    FIRST_SET_FUND_PSW(4, "【人人公社】您正在设置资金密码，验证码为："),
    UPDATE_PSW(5, "【人人公社】您正在修改密码，验证码为："),
    UPDATE_FUND_PSW(6, "【人人公社】您正在修改资金密码，验证码为："),
    UPDATE_PHONE (7, "【人人公社】您正在绑定手机号码，验证码为："),
    GOOGLE_AUTH(8, "【人人公社】您正在绑定谷歌验证码，验证码为：")
    ;

    private int code;
    private String content;
    SmsTypeEnum(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
