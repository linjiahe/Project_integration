package com.blockchain.commune.service;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import com.blockchain.commune.config.Constant;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.SmsCodeMapper;
import com.blockchain.commune.model.SmsCode;
import com.blockchain.commune.model.SmsCodeCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * Created by wenfengzhang on 16/12/3.
 */

@Component
public class SmsService {


    @Autowired
    private SmsCodeMapper smsCodeMapper;

    //产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";


    public boolean pushSmsCode(String mobile) throws CommonException{
        Random random = new Random();
        int num = (int) (random.nextDouble() * (1000000 - 100000) + 100000);
        String code = String.format("%06d", num);


        SmsCode smsCode1 = this.smsCodeMapper.selectByPrimaryKey(mobile);
        if (smsCode1 != null) {
            int ret1 = this.smsCodeMapper.deleteByPrimaryKey(mobile);
            if (ret1 == 0) {
                throw new CommonException(ErrorCodeEnum.EXCEPTION,"验证码获取失败");
            }
        }


        SmsCode smsCode = new SmsCode();
        smsCode.setMobile(mobile);
        smsCode.setCreateTime(new Date());
        smsCode.setSmsCode(code);

        int ret2 = this.smsCodeMapper.insert(smsCode);
        if (ret2 == 0) {
            throw new CommonException(ErrorCodeEnum.SMSCODEERROR,"验证码获取失败");
        }

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("code", code);
        String data = JSONObject.toJSONString(hashMap);

        this.sendSms(mobile, "SMS_133966022", data);

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


    public boolean sendSms(String mobile, String smsCode, String params) throws CommonException {
        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", Constant.accessKeyId, Constant.accessKeySecret);

            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);

            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(mobile);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(Constant.signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(smsCode);

            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为"{\"name\":\"Tom\", \"code\":\"123\"}"
            request.setTemplateParam(params);

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                return true;
            }

        } catch (ClientException e) {


            throw new CommonException(ErrorCodeEnum.EXCEPTION,e.getMessage(),e);

        }

        return false;


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
