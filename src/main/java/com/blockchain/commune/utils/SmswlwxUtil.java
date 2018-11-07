package com.blockchain.commune.utils;

import com.blockchain.commune.custommodel.SmsContent;
import com.blockchain.commune.enums.SmsTypeEnum;
import com.wlwx.client.SmsClient;
import com.wlwx.vo.ResultMsg;
import com.wlwx.vo.SmsReq;

/**
 * Created by wrb on 2018/10/11
 */
public class SmswlwxUtil {
    public static String sendSms(String phone, String verifyCode, SmsTypeEnum smsTypeEnum) {
        String custCode = "830150";                             //[必填] 用户账号
        String password = "1RFA12E8FC";                         //[必填] 账号密码
        String serviceBaseUrl = "http://123.58.255.70:8860";             //[必填] http://ip:port

        /**
         * 通过SmsReq对象传参
         */

        SmsReq smsReq = new SmsReq();
        smsReq.setUid("");                            //[选填] 业务标识，由贵司自定义32为数字透传至我司
        smsReq.setCust_code(custCode);                //[必填] 用户账号
        SmsContent smsContent = new SmsContent(verifyCode, smsTypeEnum);
        String content = smsContent.toString();
        smsReq.setContent(content);                //[必填] 短信内容
        smsReq.setDestMobiles(phone);        //[必填] 接收号码，同时发送给多个号码时,号码之间用英文半角逗号分隔(,)
        smsReq.setNeed_report("yes");                //[选填] 状态报告需求与否，是 yes 否 no 默认yes
        smsReq.setSp_code("");                        //[选填] 长号码
        smsReq.setMsgFmt("8");                        //[选填] 信息格式，0：ASCII串；3：短信写卡操作；4：二进制信息；8：UCS2编码；默认8
        System.out.println("发起开始发送信息请求");
        SmsClient smsClient = new SmsClient();
        ResultMsg resultMsg = smsClient.sendSms(smsReq, password, serviceBaseUrl);
        if (resultMsg.isSuccess()) {
            System.out.println(resultMsg.getData());
            return resultMsg.getData();
        } else {
            System.out.println(resultMsg.getCode());
            System.out.println(resultMsg.getMsg());
            return resultMsg.getData();
        }
    }
}
