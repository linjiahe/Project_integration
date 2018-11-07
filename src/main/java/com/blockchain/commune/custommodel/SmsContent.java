package com.blockchain.commune.custommodel;

import com.blockchain.commune.enums.SmsTypeEnum;

/**
 * Created by wrb on 2018/10/8
 */
public class SmsContent {

    private String verifyCode;

    private SmsTypeEnum smsTypeEnum;

    public SmsContent(String verifyCode, SmsTypeEnum smsTypeEnum) {
        this.verifyCode = verifyCode;
        this.smsTypeEnum = smsTypeEnum;
    }

    public SmsContent() {

    }

    @Override
    public String toString() {
        return this.smsTypeEnum.getContent() + this.verifyCode + "，5分钟内有效。若非本人操作，请忽略。";
    }

}
