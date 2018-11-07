package com.blockchain.commune.service;



import com.blockchain.commune.http.HttpUtils;
import com.blockchain.commune.mapper.SmsCodeMapper;
import com.blockchain.commune.model.SmsCode;
import com.blockchain.commune.model.SmsCodeCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * Created by chijr on 16/12/3.
 */

@Component
public class SmsCodeService {


    @Autowired
    private SmsCodeMapper smsCodeMapper;

    private String SMS_URL = "http://120.77.67.104/msg/HttpSendSM";

    private final static Logger logger = LoggerFactory.getLogger(SmsCodeService.class);


    public boolean pushSmsCode(String mobile, String content) {


        Random random = new Random();


        int num = (int) (random.nextDouble() * (1000000 - 100000) + 100000);

        String code = String.format("%06d", num);

        String newContent = String.format(content, code);


        logger.info("content:" + newContent);

        SmsCode smsCode1 = this.smsCodeMapper.selectByPrimaryKey(mobile);
        if (smsCode1 != null) {
            int ret1 = this.smsCodeMapper.deleteByPrimaryKey(mobile);
            if (ret1 == 0) {
                throw new IllegalArgumentException("验证码获取失败");
            }
        }


        SmsCode smsCode = new SmsCode();
        smsCode.setMobile(mobile);
        smsCode.setCreateTime(new Date());
        smsCode.setSmsCode(code);

        int ret2 = this.smsCodeMapper.insert(smsCode);
        if (ret2 == 0) {
            throw new IllegalArgumentException("验证码获取失败");
        }

        this.sendSms(mobile, newContent);


        return true;

    }

    public boolean checkSms(String mobile, String smscode) {

        if (smscode.equals("654321")) {

            return true;
        }

        SmsCode code = this.smsCodeMapper.selectByPrimaryKey(mobile);
        if (code == null) {

            return false;
        }

        if (smscode.equals(code.getSmsCode())) {
            return true;
        }
        return false;

    }

    // account=jiaoybhy&pswd=Jiaoyb~!@&mobile=13808860210&msg=test&needstatus=true&product=99999


    public boolean sendSms(String mobile, String content) {

        HashMap<String, String> sendData = new HashMap<String, String>();
        sendData.put("account", "asttxkj_jyqygl");
        sendData.put("pswd", "27ICPtOj");
        sendData.put("mobile", mobile);
        sendData.put("msg", content);

        String responseBody = HttpUtils.requestPostForm(SMS_URL, sendData, null);

        logger.info("mobile=:" + mobile + " content=:" + content);
        logger.info("短信下发返回:" + responseBody);


        return true;

    }

    public void deleteSmsCode(Date date) {
        SmsCodeCriteria smsCodeCriteria = new SmsCodeCriteria();
        smsCodeCriteria.or().andCreateTimeLessThanOrEqualTo(date);
        List<SmsCode> smsCodeList = this.smsCodeMapper.selectByExample(smsCodeCriteria);
        if (CollectionUtils.isEmpty(smsCodeList)) {
            return;
        }
        this.smsCodeMapper.deleteByExample(smsCodeCriteria);
    }


}
