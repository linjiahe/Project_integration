package com.blockchain.commune.controller;

import com.blockchain.commune.config.AutoTeamComputer;
import com.blockchain.commune.config.Constant;
import com.blockchain.commune.enums.ClientTypeEnum;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.Purse.PSubAccountTypeEnum;
import com.blockchain.commune.enums.SmsTypeEnum;
import com.blockchain.commune.enums.UserTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.service.*;
import com.blockchain.commune.service.wallet.PurseService;
import com.blockchain.commune.service.wallet.TransService;
import com.blockchain.commune.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Controller
@EnableAutoConfiguration
@Api(value = "认证", description = "与认证相关的api都在这里")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    SmsBaoService smsBaoService;
    //SmsCodeService smsService;

    @Autowired
    MerchantUserService merchantUserService;

    @Autowired
    TransService transService;

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    UserRecommendedCodeService userRecommendedCodeService;

    @Autowired
    PurseService purseService;

    @Autowired
    private UserPrivateService userPrivateService;

    @Autowired
    CustomTeamService customTeamService;

    @Autowired
    AutoTeamComputer autoTeamComputer;

    @Autowired
    UserAccessRecordService userAccessRecordService;

    @Value("${web.upload-path}")
    private String uploadPath;

    //设置用户登录的token失效时间
    private final static long TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 48;

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Transactional(rollbackFor = IllegalArgumentException.class)
    @RequestMapping(value = "/auth/register", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "会员注册")
    public String doRegister(HttpServletRequest request,
                             @ApiParam(required = true, value = "手机号") @RequestParam(required = true) String loginName,
                             @ApiParam(required = true, value = "密码") @RequestParam(required = true) String password,
                             @ApiParam(required = true, value = "短信验证码") @RequestParam(required = true) String smsCode,
                             @ApiParam(required = true, value = "推荐码") @RequestParam(required = true) String recommendedCode,
                             @ApiParam(required = true, value = "注册类型（phone为手机号，mail为邮箱）") @RequestParam(required = true) String Regtype,
                             @ApiParam(required = false, value = "邮箱账号Token") @RequestParam(required = false) String emailToken,
                             @ApiParam(required = true, value = "客户端唯一id") @RequestParam(required = true) String clientId,
                             @ApiParam(required = true, value = "客户端类型") @RequestParam(required = true) ClientTypeEnum clientType
    ) {
        try {
            UserAccessRecord userAccessRecord = new UserAccessRecord();
            userAccessRecord.setAccessType("REGISTER");
            userAccessRecord.setClientId(clientId.trim());
            userAccessRecord.setClientType(clientType.toString());
            userAccessRecord.setIp(HttpUtils.getIp(request));
            int registerNum = this.userAccessRecordService.checkAccessTypNum(userAccessRecord);
//            if (registerNum >= 3) {
//                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "设备注册太过频繁，请于第二天重试");
//            }

            if (StringUtils.isEmpty(loginName)) {
                if(Regtype.equals("phone")){
                    return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "缺少手机号码");
                }else if(Regtype.equals("mail")){
                    return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "缺少邮箱号");
                }
            }
            if (StringUtils.isEmpty(password)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "缺少密码");
            }

            List<User> userList = this.userService.selectUserByLoginName(loginName);
            if (!CollectionUtils.isEmpty(userList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "用户已存在");
            }

            String topRecommendedCode = null;
            if (!StringUtils.isEmpty(recommendedCode)) {
                UserRecommendedCode userRecommendedCode = this.userRecommendedCodeService.selectUserRecommendedCodeByKey(recommendedCode);
                if (null == userRecommendedCode) {
                    return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "推荐人信息没找到");
                }
                topRecommendedCode = userRecommendedCode.getRecommendedCode();
            }else {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "请填写邀请码");
            }

            if(Regtype.equals("phone")){
//                boolean check = this.smsBaoService.checkSms(loginName, smsCode);
//                if (!check) {
//                    return ResponseHelper.errorException(ErrorCodeEnum.SMSCODEERROR, "手机验证码错误");
//                }
            }else if(Regtype.equals("mail")){
                if(StringUtils.isEmpty(emailToken)){
                    return ResponseHelper.errorException(ErrorCodeEnum.SMSCODEERROR, "验证码Token为空");
                }
                boolean check = this.userService.checkMailCode(loginName,smsCode,emailToken );
                if (!check) {
                    return ResponseHelper.errorException(ErrorCodeEnum.SMSCODEERROR, "邮箱验证码错误");
                }
            }else{
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "未知注册方式");
            }


            long count = this.userService.countUser();

            User user = new User();
            String userId = IdUtil.getUserId();
            if(Regtype.equals("phone")){
                user.setLoginName(loginName);
            }else if(Regtype.equals("mail")){
                user.setEmail(loginName);
            }
            user.setPassword(password);
            user.setRecommendId(recommendedCode);
            user.setUserId(userId);
            user.setUserType(UserTypeEnum.COMMON_USER.toString());
            user.setUserCode(NumberUtil.getUserCode(count + 1));
            user.setVipLevel("0");
            if(Regtype.equals("phone")){
                user.setAuthMethod("P");
            }else if(Regtype.equals("mail")){
                user.setAuthMethod("M");
            }

            int ret = this.userService.insertUser(user);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据插入失败");
            }

            //添加用户推荐码
            String recommendCode = userRecommendedCodeService.getRecommendedCode();
            String serverPath = request.getScheme() + "://" +request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
            String url = serverPath + "recommend/" + recommendCode;
            String path = CreateQrcodeUtil.createQrcode(url, this.uploadPath,"recommendedCode");
            UserRecommendedCode userRecommendedCode = new UserRecommendedCode();
            userRecommendedCode.setRecommendedCode(recommendCode);
            userRecommendedCode.setRecommendedUrl(recommendCode);
            userRecommendedCode.setRecommendedQrcode(path);
            userRecommendedCode.setUserId(userId);
            int ret1 = this.userRecommendedCodeService.insertUserRecommendedCode(userRecommendedCode);
            if (ret1 == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据插入失败");
            }
            //注册成功后，新建team队列
            customTeamService.insertTeam(userId);

            //注册送积分
            this.transService.addRegisterScore(userId, loginName, topRecommendedCode);

            //新增币种钱包（暂时支持三个币种）
            this.purseService.sendAllCoinAccountQuene(userId, loginName);

            //记录用户ip及设备信息
            userAccessRecord.setId(IdUtil.getId());
            userAccessRecord.setUserId(userId);
            userAccessRecord.setAccessTime(new Date());
            int ret2 = this.userAccessRecordService.insertUserAccessRecord(userAccessRecord);
            if (ret2 == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据插入失败!");
            }

            User user1 = this.userService.selectUserByKey(userId);
            user1.setPassword("");

            String token = JWTUtils.createJWT(userId, null, TOKEN_EXPIRED_TIME);
            HashMap<String, Object> hashMap = ConvertUtil.objectToMap(user1);
            boolean paymentPasswordStatus = this.userPrivateService.checkPaymentPassword(userId);
            hashMap.put("paymentPasswordStatus", paymentPasswordStatus);
            hashMap.put("token", token);
            hashMap.put("attendanceStatus", false);
            int[] attendanceScore = transService.getAttendanceScoreArr();
            hashMap.put("attendanceScore", attendanceScore);
            hashMap.put("attendanceDay", 0);
            //hashMap.put("recommendedCode", this.getRecommendedCode(userId));
            hashMap.put("recommendedQrCode", serverPath + path);
            return ResponseHelper.successFormat(hashMap);
        } catch (Exception e) {
            logger.error("registerError:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }

    }

    @RequestMapping(value = "/auth/user/login", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "用户密码登录", position = 1)

    public String doUserLogin(
            HttpServletRequest request,
            @ApiParam(required = true, value = "用户名") @RequestParam(required = true) String loginName,
            @ApiParam(required = false, value = "密码") @RequestParam(required = false) String password,
            @ApiParam(required = false, value = "谷歌验证码") @RequestParam(required = false) String googleCode,
            @ApiParam(required = true, value = "客户端唯一id") @RequestParam(required = true) String clientId,
            @ApiParam(required = true, value = "客户端类型") @RequestParam(required = true) ClientTypeEnum clientType
    ) {
        try {
            List<User> userList = this.userService.selectUserByLoginName(loginName);
            User user = null;
            if (CollectionUtils.isEmpty(userList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "用户不存在");
            }

            user = userList.get(0);
            //登陆验证方式  P密码登陆，G谷歌验证登陆，PAG密码+谷歌验证登陆
            String way = user.getLoginMethod();
            //验证方式
            if(way.equals("P")){
                if (!user.getPassword().equals(password)) {
                    return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "登录密码错误");
                }
            }else if(way.equals("G")){
                if(!userService.checkGoogleAuth(user.getGoogleAuthkey(),Long.parseLong(googleCode))){
                    return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "谷歌认证失败");
                }
            }else if(way.equals("PAG")){
                if (!user.getPassword().equals(password)) {
                    return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "登录密码错误");
                }
                if(!userService.checkGoogleAuth(user.getGoogleAuthkey(),Long.parseLong(googleCode))){
                    return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "谷歌认证失败");
                }
            }else{
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "未知认证方式");
            }
            //生成token
            String token = JWTUtils.createJWT(user.getUserId(), null, TOKEN_EXPIRED_TIME);
            user.setUpdateTime(new Date());

            String userId = userList.get(0).getUserId();
            PurseAccount purseAccount = purseService.queryPurseAccount(userId);
            if (purseAccount != null){
                //登录之后发送查询余额的消息
                this.purseService.sendGetBalanceQuene(userId);
                this.purseService.updatePurseAddress(userId);
            }

            List<PurseSubAccount> purseSubAccountList = purseService.queryBalance(userId, PSubAccountTypeEnum.BCT.toString());
            if (CollectionUtils.isEmpty(purseSubAccountList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "查询钱包余额失败");
            }
            BigDecimal total = purseSubAccountList.get(0).getAvailableAivilable();
            BigDecimal divideNum = new BigDecimal(10000);
            BigDecimal num = total.divide(divideNum, BigDecimal.ROUND_DOWN);
            int level = this.userService.getVipLevel(num, 0, Constant.vipLevel.length);
            if (!user.getVipLevel().equals(level)) {
                user.setVipLevel(String.valueOf(level));
            }
            //更新用户登录时间
            user.setUpdateTime(new Date());
            int ret = this.userService.updateUserByKey(user);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }
                user.setPassword("");

            //记录用户ip及设备信息
            UserAccessRecord userAccessRecord = new UserAccessRecord();
            userAccessRecord.setId(IdUtil.getId());
            userAccessRecord.setAccessType("LOGIN");
            userAccessRecord.setClientId(clientId);
            userAccessRecord.setClientType(clientType.toString());
            userAccessRecord.setIp(HttpUtils.getIp(request));
            userAccessRecord.setUserId(userId);
            userAccessRecord.setAccessTime(new Date());
            int ret2 = this.userAccessRecordService.insertUserAccessRecord(userAccessRecord);
            if (ret2 == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据插入失败!");
            }

            //判断登录天数解冻用户注册积分
            this.transService.unfreezeRegister(user);

            HashMap<String, Object> hashMap = ConvertUtil.objectToMap(user);
            boolean paymentPasswordStatus = this.userPrivateService.checkPaymentPassword(userId);
            hashMap.put("paymentPasswordStatus", paymentPasswordStatus);
            hashMap.put("token", token);
            String serverPath = request.getScheme() + "://" +request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
            UserRecommendedCode userRecommendedCode = this.getRecommendedCode(userId);
            hashMap.put("recommendedQrCode", serverPath + userRecommendedCode.getRecommendedQrcode());
            hashMap.put("recommendedCode", userRecommendedCode.getRecommendedCode());
            hashMap.putAll(this.attendanceService.checkAttendance(user.getUserId()));
            return ResponseHelper.successFormat(hashMap);
        } catch (Exception e) {
            logger.error("doUserLogin:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/auth/autologin", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "用户自动登录", notes = "如果用户已经登录,使用token和userid登录")
    public String doAutoLogin(HttpServletRequest request,
                              @ApiParam(required = true, value = "用户Id") @RequestParam(required = true) String userId,
                              @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                              @ApiParam(required = true, value = "客户端唯一id") @RequestParam(required = true) String clientId,
                              @ApiParam(required = true, value = "客户端类型") @RequestParam(required = true) ClientTypeEnum clientType) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }

            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }

            PurseAccount purseAccount = purseService.queryPurseAccount(userId);
            if (purseAccount != null) {
                //登录之后发送查询余额的消息
                //this.purseService.sendGetBalanceQuene(userId);
                this.purseService.updatePurseAddress(userId);
            }
            List<PurseSubAccount> purseSubAccountList = purseService.queryBalance(userId, PSubAccountTypeEnum.BCT.toString());
            if (CollectionUtils.isEmpty(purseSubAccountList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "查询钱包余额失败");
            }
            BigDecimal total = purseSubAccountList.get(0).getAvailableAivilable();
            BigDecimal divideNum = new BigDecimal(10000);
            BigDecimal num = total.divide(divideNum, BigDecimal.ROUND_DOWN);
            int level = this.userService.getVipLevel(num, 0, Constant.vipLevel.length);
            if (!user.getVipLevel().equals(level)) {
                user.setVipLevel(String.valueOf(level));
            }
            //更新用户登录时间
            user.setUpdateTime(new Date());
            int ret1 = this.userService.updateUserByKey(user);
            if (ret1 == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "更新用户失败");
            }

            user.setPassword("");

            //记录用户ip及设备信息
            UserAccessRecord userAccessRecord = new UserAccessRecord();
            userAccessRecord.setId(IdUtil.getId());
            userAccessRecord.setAccessType("LOGIN");
            userAccessRecord.setClientId(clientId);
            userAccessRecord.setClientType(clientType.toString());
            userAccessRecord.setIp(HttpUtils.getIp(request));
            userAccessRecord.setUserId(userId);
            userAccessRecord.setAccessTime(new Date());
            int ret2 = this.userAccessRecordService.insertUserAccessRecord(userAccessRecord);
            if (ret2 == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据插入失败!");
            }

            //判断登录天数解冻用户注册积分
            this.transService.unfreezeRegister(user);

            HashMap<String, Object> hashMap = ConvertUtil.objectToMap(user);
            boolean paymentPasswordStatus = this.userPrivateService.checkPaymentPassword(userId);
            hashMap.put("paymentPasswordStatus", paymentPasswordStatus);
            hashMap.put("token", token);
            String serverPath = request.getScheme() + "://" +request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
            UserRecommendedCode userRecommendedCode = this.getRecommendedCode(userId);
            hashMap.put("recommendedQrCode", serverPath + userRecommendedCode.getRecommendedQrcode());
            hashMap.put("recommendedCode", userRecommendedCode.getRecommendedCode());
            hashMap.putAll(this.attendanceService.checkAttendance(user.getUserId()));
            return ResponseHelper.successFormat(hashMap);
        } catch (CommonException e) {
            logger.error("doAutoLogin:", e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("doAutoLogin:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }


    }

    @RequestMapping(value = "/merchant/user/login", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "商户端用户密码登录", position = 1)
    public String doLogin
            (@ApiParam(required = true, value = "商户账号") @RequestParam(required = true) String loginName,
             @ApiParam(required = true, value = "密码") @RequestParam(required = true) String password) {


        List<MerchantUser> lst = this.merchantUserService.selectMerchantUserByLoginName(loginName);
        if (CollectionUtils.isEmpty(lst)) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "登录错误,用户不存在");
        }


        MerchantUser user = lst.get(0);
        if (!password.equals(user.getPassword())) {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "密码错误");
        }

        String token = JWTUtils.createJWT(user.getMerchantUserId(), null, 1000 * 60 * 60 * 24);
        user.setUpdateTime(new Date());

        int ret = this.merchantUserService.updateMerchantUserByKey(user);
        if (ret == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "登录出错");
        }

        user.setPassword("");
        HashMap<String, Object> hashMap = ConvertUtil.objectToMap(user);
        hashMap.put("token", token);

        return ResponseHelper.successFormat(hashMap);

    }

    @RequestMapping(value = "/auth/getsmscode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取手机验证码")
    public String doGetSmsCode(
            @ApiParam(required = true, value = "用户名,就是手机号") @RequestParam(required = true) String mobile,
            @ApiParam(value = "滑块验证码Token",required = true) @RequestParam(required = true) String BlockToken,
            @ApiParam(value = "x坐标（最左边的像素值）",required = true) @RequestParam(required = true) Integer xint,
            @ApiParam(required = true, value = "短信验证类型") @RequestParam(required = true) SmsTypeEnum smsTypeEnum
    ){
        String resp = null;
        try {
            if(!userService.checkBlockToken(xint,BlockToken,true)){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "滑块验证错误");
            }
        //this.smsService.pushSmsCode(mobile, "您本次的短信验证码是：%s，有效时间5分钟");

            resp = this.smsBaoService.pushSmsCode(mobile, smsTypeEnum);
            return ResponseHelper.successFormat(resp);
        } catch (CommonException e) {
            logger.error("doGetSmsCode:{}",e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    /**
     * 通过userId获得用户对应的推荐码
     * @param userId
     * @return
     */
    private UserRecommendedCode getRecommendedCode(String userId) {
        List<UserRecommendedCode> recommendedCodeList = this.userRecommendedCodeService.selectUserRecommendedCodeByUserId(userId);
        if (CollectionUtils.isEmpty(recommendedCodeList)) {
            return null;
        }
        return recommendedCodeList.get(0);
    }



    @RequestMapping(value = "/auth/userAuthenticate",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "实名认证")
    public String userAuthenticate(@ApiParam(value = "token",required = true) @RequestParam(required = true) String token,
                                    @ApiParam(value = "userId",required = true) @RequestParam(required = true) String userId,
                                    @ApiParam(value = "姓名",required = true) @RequestParam(required = true) String name,
                                    @ApiParam(value = "身份证号",required = true) @RequestParam(required = true) String idNo){

        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录过期，请重新登录");
            }

            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }
            boolean check = userService.checkUser(idNo);
            if (!check) {
                throw new CommonException(ErrorCodeEnum.EXCEPTION,"该身份证已被认证，请重新输入");
            }
            long number = 3L; //实名认证次数限制
            String countString = RedisUtil.get("AuthNum_" + userId);
            if (!StringUtils.isEmpty(countString) && NumberUtil.isNumber(countString)) {
                if (Long.parseLong(countString) >= number) {
                    return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "今日实名认证3次机会已用完");
                }
            }
            long expireTime = DateUtil.getTimeEndSeconds();
            long timeCount = RedisUtil.inscrement("AuthTime_" + userId, 60L, 1);
            if (timeCount > 1) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "频繁操作，请在1分钟后重试");
            }
            long count = RedisUtil.inscrement("AuthNum_" + userId, expireTime,number);
            if (count > number) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "今日实名认证3次机会已用完");
            }

            Map<String, Object> map = userService.sendUserMessage(userId, name, idNo);
            boolean time = userService.getTime(userId);//判断重复得积分
            if (time){
                transService.addUserAuthScore(userId);//实名认证后送积分
                map.put("score",3000);
            }else {
                map.put("score",0);
            }
            autoTeamComputer.addUserId(userId);
            return ResponseHelper.successFormat(map);
        }  catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(),e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"认证次数已用完");
        }
    }

    @RequestMapping(value = "/auth/userAuth",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "判断用户是否已进行实名认证")
    public String userAuth(@ApiParam(value = "token",required = true) @RequestParam(required = true) String token,
                           @ApiParam(value = "userId",required = true) @RequestParam(required = true) String userId){

        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录过期，请重新登录");
            }
            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }
            int status = userService.getUserValidate(userId);
            switch (status){
                case 1:
                    return ResponseHelper.successFormat(status);//初级认证成功
                case 2:
                    return ResponseHelper.successFormat(status);//高级认证待审核
                case 3:
                    return ResponseHelper.successFormat(status);//高级认证成功
                case 4:
                    return ResponseHelper.successFormat(status);//高级认证审核未通过
            }
            return ResponseHelper.successFormat(status);//否则未认证
        } catch (CommonException e) {
            logger.error("userAuth",e.getMessage());
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"未知错误");
        }
    }

    @RequestMapping(value = "/auth/updateUserAuth",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "高级认证")
    public String updateUserAuth(@ApiParam(value = "token",required = true) @RequestParam(required = true) String token,
                                 @ApiParam(value = "userId",required = true) @RequestParam(required = true) String userId,
                                 @ApiParam(value = "身份证正面",required = false) @RequestParam(required = false) String front,
                                 @ApiParam(value = "身份证反面",required = false) @RequestParam(required = false) String back,
                                 @ApiParam(value = "半身照",required = false) @RequestParam(required = false) String userPic
                                 ){
        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录过期，请重新登录");
            }
            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }
            this.userService.updateUserAuth(userId,front,back,userPic);
            return ResponseHelper.successFormat();
        } catch (CommonException e) {
            logger.error("updateUserAuth", e.getMessage());
            return ResponseHelper.errorException(e.getCode(),e.getMessage());
        }
    }

    @RequestMapping(value = "/auth/googleAuthKey",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "生成谷歌认证密钥")
    public String googleAuthKey(@ApiParam(value = "token",required = true) @RequestParam(required = true) String token,
                           @ApiParam(value = "userId",required = true) @RequestParam(required = true) String userId){

        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录过期，请重新登录");
            }
            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }
            //谷歌验证工具类
            GoogleAuthenticatorUtil google = new GoogleAuthenticatorUtil();
            //生成谷歌认证密钥
            String key = google.generateSecretKey();
            if(!StringUtils.isEmpty(key)){
                Map<String, String> map = new HashMap<>();
                map.put("key", key);
                //保存生成的密钥2天
                long expircTime = 1000 * 60 * 60 * 24;
                RedisUtil.set("googleAuth_"+userId,key, expircTime);
                return ResponseHelper.successFormat(map);
            }else{
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"密钥生成失败");
            }
        } catch (CommonException e) {
            logger.error("googleAuthKey:"+e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/auth/googleAuthCheck",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "谷歌认证校验验证码")
    public String googleAuthCheck(@ApiParam(value = "token",required = true) @RequestParam(required = true) String token,
                                  @ApiParam(value = "userId",required = true) @RequestParam(required = true) String userId,
                                  @ApiParam(required = false, value = "短信验证码") @RequestParam(required = false) String smsCode,
                                  @ApiParam(required = false, value = "邮箱账号Token") @RequestParam(required = false) String emailToken,
                                  @ApiParam(required = false, value = "邮箱验证码") @RequestParam(required = false) String mailCode,
                                  @ApiParam(required = false, value = "谷歌验证码") @RequestParam(required = false) String googleAuthCode,
                                  @ApiParam(value = "滑块验证码Token",required = false) @RequestParam(required = false) String BlockToken,
                                  @ApiParam(value = "x坐标（最左边的像素值）",required = false) @RequestParam(required = false) Integer xint){

        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录过期，请重新登录");
            }
            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }
            //根据用户选择的验证方式进行验证
            userService.checkAuthMethod(user,user.getLoginName(),smsCode,user.getEmail(),mailCode,emailToken,googleAuthCode,null,null);
            if(RedisUtil.exists("googleAuth_"+userId)){
                String key = RedisUtil.get("googleAuth_" + userId);
                user.setGoogleAuthkey(key);
                RedisUtil.remove("googleAuth_" + userId);
                int i = userService.updateUserByKey(user);
                if(i>0){
                    return ResponseHelper.successFormat();
                }else{
                    return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "修改失败");
                }
            }else{
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未找到生成的谷歌验证密钥");
            }

        } catch (CommonException e) {
            logger.error("googleAuthKey:"+e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/auth/deleteGoogleAuth",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "解除谷歌认证")
    public String deleteGoogleAuth(@ApiParam(value = "token",required = true) @RequestParam(required = true) String token,
                                @ApiParam(value = "userId",required = true) @RequestParam(required = true) String userId){
        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录过期，请重新登录");
            }
            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }
            //清除谷歌认证密钥
            user.setGoogleAuthkey(null);
            //写入数据库
            int updateUserByKey = this.userService.updateUserByPK(user);
            if(updateUserByKey>0){
                return ResponseHelper.successFormat();
            }else{
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"解除失败");
            }
        } catch (CommonException e) {
            logger.error("googleAuthKey:"+e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/auth/updateLoginMethod",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "修改登陆验证方式")
    public String updateLoginMethod(@ApiParam(value = "token",required = true) @RequestParam(required = true) String token,
                                    @ApiParam(value = "userId",required = true) @RequestParam(required = true) String userId,
                                    @ApiParam(value = "登陆方式（P密码登陆，G谷歌验证登陆，PAG密码+谷歌验证登陆）",required = false)
                                    @RequestParam(required = false) String login_method,
                                    @ApiParam(value = "认证方式（P手机验证，M邮箱验证，PAG手机+谷歌验证，EAG邮箱+谷歌验证）",required = false)
                                    @RequestParam(required = false) String auth_method){
        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录过期，请重新登录");
            }
            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }
            if(StringUtils.isEmpty(login_method)&&StringUtils.isEmpty(auth_method)){
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "方式设置空，无法修改");
            }
            if(!StringUtils.isEmpty(login_method)){
                user.setLoginMethod(login_method);
            }
            if(!StringUtils.isEmpty(auth_method)){
                user.setAuthMethod(auth_method);
            }
            int updateUserByKey = this.userService.updateUserByPK(user);
            if(updateUserByKey>0){
                return ResponseHelper.successFormat();
            }else{
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"修改失败");
            }
        } catch (CommonException e) {
            logger.error("updateLoginMethod:"+e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/auth/checkEmail",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "验证邮箱验证码")
    public String checkEmail(
                                    @ApiParam(value = "邮箱账号",required = true) @RequestParam(required = true) String email,
                                    @ApiParam(value = "邮箱账号Token",required = true) @RequestParam(required = true) String emailToken,
                                    @ApiParam(value = "邮箱验证码",required = true) @RequestParam(required = true) String mailCode){
        boolean check = this.userService.checkMailCode(email,mailCode,emailToken );
        if (check) {
            return ResponseHelper.successFormat();
        }else{
            return ResponseHelper.errorException(ErrorCodeEnum.SMSCODEERROR, "邮箱验证码错误");
        }
    }


    @RequestMapping(value = "/auth/blockToken",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取滑块验证码")
    public String queryBlockToken(@ApiParam(value = "上一个滑块验证码Token,用于清除上一个滑块",required = false)
                                      @RequestParam(required = false) String upBlockToken) {
        try {
            if(!StringUtils.isEmpty(upBlockToken)){
                if(RedisUtil.exists(upBlockToken)){
                    RedisUtil.remove(upBlockToken);
                }
            }
            Map<String, String> pictureMap;
            File templateFile;
            File targetFile;
            Random random = new Random();
            int templateNo = random.nextInt(5) + 1;
            int targetNo = random.nextInt(40) + 1;

            InputStream stream = getClass().getClassLoader().getResourceAsStream("static/templates/" + templateNo + ".png");
            templateFile = new File(templateNo + ".png");
            FileUtils.copyInputStreamToFile(stream, templateFile);
            stream = getClass().getClassLoader().getResourceAsStream("static/targets/" + targetNo + ".jpg");
            targetFile = new File(targetNo + ".jpg");
            FileUtils.copyInputStreamToFile(stream, targetFile);
            pictureMap = VerifyImageUtil.pictureTemplatesCut(templateFile, targetFile, "png", "jpg");
            stream.close();
            templateFile.delete();
            targetFile.delete();
            String oriCopyImages = pictureMap.get("blockImage");
            String newImages = pictureMap.get("image");
            String blockToken = new String(pictureMap.get("blockToken"));
            return ResponseHelper.successFormat(pictureMap);
        }catch (IOException e){
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "IO流发生异常");
        }
        catch (CommonException e){
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
        catch (Exception e){
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,  "未知异常");
        }

    }


    @RequestMapping(value = "/auth/checkBlockToken",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "验证滑块验证码")
    public String checkBlockToken(@ApiParam(value = "滑块验证码Token",required = true)
                                  @RequestParam(required = true) String BlockToken,
                                  @ApiParam(value = "x坐标（最左边的像素值）",required = true)
                                  @RequestParam(required = true) Integer xint) {
        try {
            if(userService.checkBlockToken(xint,BlockToken,false)){
                return ResponseHelper.successFormat();
            }else{
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "验证失败");
            }
        } catch (CommonException e){
            return ResponseHelper.errorException(e.getCode(),  e.getMessage());
        }

    }




}
