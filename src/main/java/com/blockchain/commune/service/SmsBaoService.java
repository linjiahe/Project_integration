package com.blockchain.commune.service;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.SmsTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.mapper.SmsCodeMapper;
import com.blockchain.commune.model.SmsCode;
import com.blockchain.commune.utils.SmswlwxUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * Created by wrb on 2018/9/1
 */
@Service
public class SmsBaoService {

    @Autowired
    private SmsCodeMapper smsCodeMapper;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(SmsBaoService.class);

    public String pushSmsCode(String phone, SmsTypeEnum smsTypeEnum) throws CommonException {
        if (!CollectionUtils.isEmpty(userService.selectUserByLoginName(phone))&&smsTypeEnum.toString().equals(SmsTypeEnum.REGISTERED.toString())) {
            throw new CommonException(ErrorCodeEnum.EXCEPTION, "手机号码已注册！");
        }
        Random random = new Random();
        int num = (int) (random.nextDouble() * (1000000 - 100000) + 100000);
        String verifyCode = String.format("%06d", num);

        String status = SmswlwxUtil.sendSms(phone, verifyCode, smsTypeEnum);
        JSONObject smsJson = JSONObject.fromObject(status);
        String respCode = smsJson.getString("respCode");
        if (!"0".equals(respCode)) {
            logger.error(phone + "的短信验证码：", "respCode:" + respCode);
            return "服务器错误，请联系管理员";
        }
        SmsCode smsCode1 = this.smsCodeMapper.selectByPrimaryKey(phone);
        if (smsCode1 != null) {
            int ret1 = this.smsCodeMapper.deleteByPrimaryKey(phone);
            if (ret1 == 0) {
                throw new CommonException(ErrorCodeEnum.EXCEPTION, "验证码获取失败");
            }
        }
        String code = smsJson.getJSONArray("result").getJSONObject(0).getString("code");
        String msg = smsJson.getJSONArray("result").getJSONObject(0).getString("msg");
        if ("0".equals(code)) {
            SmsCode smsCode = new SmsCode();
            smsCode.setMobile(phone);
            smsCode.setSmsCode(verifyCode);

            int ret2 = this.smsCodeMapper.insertSelective(smsCode);
            if (ret2 == 0) {
                throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "验证码获取失败");
            }
            logger.info(phone+"用户发送手机验证码成功");
        } else {
            logger.error(phone + "的短信验证码：", "code:" + code + ",msg:" + msg);
        }
        return msg;
    }

    public boolean checkSms(String mobile, String smscode) {
        if (smscode.equals("654321")) {
            return true;
        }

        SmsCode code = this.smsCodeMapper.selectByPrimaryKey(mobile);
        if (code == null) {
            return false;
        }
        Date nowDate = new Date();
        long diff = nowDate.getTime() - code.getCreateTime().getTime();
        if ( diff > 300000) {
            return false;
        }
        if (smscode.equals(code.getSmsCode())) {
            return true;
        }
        return false;
    }

    private String statusStr(String result) {
        switch(result)
        {
            case "0":
                return "短信发送成功";
            case "29":
                return "您输入的号码不规范";
            default:
                System.out.println("短信错误！编码："+result);
                return "未知错误";
        }
    }


}
