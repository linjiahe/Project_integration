package com.blockchain.commune.controller;


import com.blockchain.commune.config.Constant;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.Purse.PSubAccountTypeEnum;
import com.blockchain.commune.enums.SmsTypeEnum;
import com.blockchain.commune.enums.TeamMemberLeverEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.PurseSubAccount;
import com.blockchain.commune.model.User;
import com.blockchain.commune.model.UserPrivate;
import com.blockchain.commune.model.UserRecommendedCode;
import com.blockchain.commune.service.*;
import com.blockchain.commune.service.wallet.PurseService;
import com.blockchain.commune.service.wallet.TransService;
import com.blockchain.commune.utils.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Watchable;
import java.util.*;

@Api(value = "用户相关", description = "用户相关接口", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})

public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRecommendedCodeService userRecommendedCodeService;

    @Autowired
    SmsBaoService smsBaoService;

    @Autowired
    Configuration configuration;

    @Autowired
    TransService transService;

    @Autowired
    PurseService purseService;

    @Autowired
    private UserPrivateService userPrivateService;

    @Autowired
    private TeamService teamService;

    @Value("${web.upload-path}")
    private String uploadPath;

    private Logger logger = LoggerFactory.getLogger(UserController.class);


    @RequestMapping(value = "/user/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新用户信息", position = 1)
    public String updateUser(
            String token,
            User user
    ) {
        try {

            if (!JWTUtils.checkToken(token, user.getUserId())) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            User user1 = this.userService.selectUserByKey(user.getUserId());
            if (user1 == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
            }

            user.setPassword(null);

            int ret = this.userService.updateUserByKey(user);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }
            User user2 = this.userService.selectUserByKey(user.getUserId());

            return ResponseHelper.successFormat(user2);
        } catch (CommonException e) {
            logger.error("updateUser:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }

    @RequestMapping(value = "/user/query", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取用户信息", position = 1)
    public String queryUser(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId
    ) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
            }

            List<PurseSubAccount> purseSubAccountList = purseService.queryBalance(userId, PSubAccountTypeEnum.BCT.toString());
            if (CollectionUtils.isEmpty(purseSubAccountList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "查询钱包余额失败");
            }
            BigDecimal total = purseSubAccountList.get(0).getTotalAivilable();
            BigDecimal divideNum = new BigDecimal(10000);
            BigDecimal num = total.divide(divideNum, BigDecimal.ROUND_DOWN);
            int level = this.userService.getVipLevel(num, 0, Constant.vipLevel.length);
            if (!user.getVipLevel().equals(level)) {
                user.setVipLevel(String.valueOf(level));
                int ret = this.userService.updateUserByKey(user);
                if (ret == 0) {
                    return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
                }
            }

            user.setPassword("");

            return ResponseHelper.successFormat(user);
        } catch (CommonException e) {
            logger.error("获取用户信息：" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("获取用户信息：" + e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }


    @RequestMapping(value = "/user/getRecommendedCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取用户推荐码", position = 1)
    public String getRecommendedCode(
            HttpServletRequest req,
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId
    ) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
            }
            //String serverPath = req.getScheme() + "://" +req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/";
            String serverPath = "https://www.cococo.org" + req.getContextPath() + "/";
            List<UserRecommendedCode> recommendedCodeList = this.userRecommendedCodeService.selectUserRecommendedCodeByUserId(userId);
            UserRecommendedCode userRecommendedCode = null;
            if (CollectionUtils.isEmpty(recommendedCodeList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "推荐码信息未找到");
            } else {
                userRecommendedCode = recommendedCodeList.get(0);
                File file = new File(uploadPath + userRecommendedCode.getRecommendedQrcode());
                if (!file.exists() || null == userRecommendedCode.getRecommendedUrl() || null == userRecommendedCode.getRecommendedQrcode() || !userRecommendedCode.getRecommendedCode().equals(userRecommendedCode.getRecommendedUrl())) {
                    String url = serverPath + "recommend/" + recommendedCodeList.get(0).getRecommendedCode();
                    String path = CreateQrcodeUtil.createQrcode(url, this.uploadPath, "recommendedCode");
                    userRecommendedCode = this.userRecommendedCodeService.getRecommendUrlQrCode(userRecommendedCode.getRecommendedCode(), path);
                }
            }
            userRecommendedCode.setRecommendedUrl(serverPath + "recommend/" + userRecommendedCode.getRecommendedCode());
            userRecommendedCode.setRecommendedQrcode(serverPath + userRecommendedCode.getRecommendedQrcode());
            HashMap<String, Object> hashMap = ConvertUtil.objectToMap(userRecommendedCode);

            hashMap.putAll(this.transService.getRecommendedInfo(userId));
            return ResponseHelper.successFormat(hashMap);
        } catch (CommonException e) {
            logger.error("获取用户推荐码异常：" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/user/updatePhone", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "修改手机号码", position = 1)

    public String updatePhone(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
            @ApiParam(required = true, value = "资金密码") @RequestParam(required = true) String paymentPassword,
            @ApiParam(required = false, value = "手机号码") @RequestParam(required = false) String loginName,
            @ApiParam(required = false, value = "短信验证码") @RequestParam(required = false) String smsCode,
            @ApiParam(required = false, value = "邮箱账号") @RequestParam(required = false) String email,
            @ApiParam(required = false, value = "邮箱账号Token") @RequestParam(required = false) String emailToken,
            @ApiParam(required = false, value = "邮箱验证码") @RequestParam(required = false) String mailCode,
            @ApiParam(required = false, value = "谷歌验证码") @RequestParam(required = false) String googleAuthCode,
            @ApiParam(value = "滑块验证码Token",required = false) @RequestParam(required = false) String BlockToken,
            @ApiParam(value = "x坐标（最左边的像素值）",required = false) @RequestParam(required = false) Integer xint
    ) {
        try {
            //1.校验用户登陆状况
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }
            if (StringUtils.isEmpty(loginName)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "手机号码为空");
            }
            //2.查询所变更的手机号码是否已注册账户
            List<User> listuser = userService.selectUserByLoginName(loginName);
            //2.1所变更的手机号码未注册，正常变更业务
            if (CollectionUtils.isEmpty(listuser)) {
                //3.1查询资金密码表，进行比较支付密码
                UserPrivate userPrivate = userPrivateService.selectUserPrivateByKey(userId);
                if (!paymentPassword.equals(userPrivate.getPaymentPassword())) {
                    return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "资金密码错误");
                }
                //3.2查询用户信息查看校验方式
                User user = userService.selectUserByKey(userId);
                //3.2.1
                //根据用户选择的验证方式进行验证
                userService.checkAuthMethod(user, loginName, smsCode, email, mailCode, emailToken, googleAuthCode, null, null);
                user.setLoginName(loginName);
                user.setUpdateTime(new Date());
                Integer updateInt = userService.updateUserByKey(user);
                if (updateInt > 0) {
                    return ResponseHelper.successFormat("修改成功");
                } else {
                    return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "录入数据库失败");
                }
            } else {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "该手机号已绑定，无法绑定多个账户");
            }

        } catch (CommonException e) {
            logger.error("updatePhone：" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }

    @RequestMapping(value = "/user/getsmscode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取手机验证码，用户必须存在")
    public String doGetsmscode(
            @ApiParam(required = true, value = "手机号") @RequestParam(required = true) String loginName,
            @ApiParam(required = true, value = "短信验证类型") @RequestParam(required = true) SmsTypeEnum smsTypeEnum
    ) {
        try {
            List<User> userList = this.userService.selectUserByLoginName(loginName);
            if (CollectionUtils.isEmpty(userList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "用户不存在");
            }
            String resp = null;
            resp = this.smsBaoService.pushSmsCode(loginName, smsTypeEnum);
            return ResponseHelper.successFormat(resp);
        } catch (Exception e) {
            logger.error("doGetsmscode：", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/user/getCaptcha", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取图形验证码")
    public String getCaptcha() {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            String str = RandomGraphic.createInstance(4).drawInputstr(4, RandomGraphic.GRAPHIC_PNG, output);
            System.out.println("----------------str:" + str);
            byte[] captcha = output.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();

            String imagestr = encoder.encode(captcha);// 返回Base64编码过的字节数组字符串
            String token = JWTUtils.createJWT(str.toLowerCase(), null, 1000 * 50 * 5);
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("captchaToken", token);
            hashMap.put("captcha", imagestr);
            return ResponseHelper.successFormat(hashMap);

        } catch (Exception e) {
            logger.error("getCaptcha：", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/user/checkCaptcha", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "检查图形验证码是否正确")
    public String checkCaptcha(
            @ApiParam(required = true, value = "图形验证码") @RequestParam(required = true) String captcha,
            @ApiParam(required = true, value = "图形验证码token") @RequestParam(required = true) String captchaToken
    ) {
        try {
            if (!JWTUtils.checkToken(captchaToken, captcha.toLowerCase())) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "图形验证码错误");
            }
            return ResponseHelper.successFormat(true);
        } catch (Exception e) {
            logger.error("checkCaptcha：", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/user/checksmscode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "检查手机验证码是否正确")
    public String doChecksmscode(
            @ApiParam(required = true, value = "手机号") @RequestParam(required = true) String loginName,
            @ApiParam(required = true, value = "短信验证码") @RequestParam(required = true) String smsCode
    ) {
        try {
            List<User> userList = this.userService.selectUserByLoginName(loginName);
            if (CollectionUtils.isEmpty(userList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "用户不存在");
            }
            boolean check = this.smsBaoService.checkSms(loginName, smsCode);
            if (!check) {
                return ResponseHelper.errorException(ErrorCodeEnum.SMSCODEERROR, "短信验证码错误");
            }
            return ResponseHelper.successFormat("验证成功");
        } catch (Exception e) {
            logger.error("doGetsmscode：", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "忘记密码后重置密码")
    public String resetPassword(
            @ApiParam(required = true, value = "手机/邮箱号") @RequestParam(required = true) String loginName,
            @ApiParam(required = true, value = "密码") @RequestParam(required = true) String password,
            @ApiParam(required = true, value = "短信/邮箱验证码") @RequestParam(required = true) String smsCode,
            @ApiParam(required = true, value = "类型（phone为手机号，mail为邮箱）") @RequestParam(required = true) String Regtype,
            @ApiParam(required = false, value = "邮箱账号Token") @RequestParam(required = false) String emailToken
    ) {
        try {
            List<User> userList = this.userService.selectUserByLoginName(loginName);
            if (CollectionUtils.isEmpty(userList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "用户不存在");
            }

            User user = userList.get(0);
            user.setPassword(password);
            if (Regtype.equals("phone")) {
                boolean check = this.smsBaoService.checkSms(loginName, smsCode);
                if (!check) {
                    return ResponseHelper.errorException(ErrorCodeEnum.SMSCODEERROR, "短信验证码错误");
                }
                user.setLoginMethod("P");
                user.setAuthMethod("P");
            } else if (Regtype.equals("mail")) {
                boolean check = this.userService.checkMailCode(loginName, smsCode, emailToken);
                if (!check) {
                    return ResponseHelper.errorException(ErrorCodeEnum.SMSCODEERROR, "邮箱验证码错误");
                }
                user.setLoginMethod("P");
                user.setAuthMethod("M");
            } else {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知验证方式");
            }

            user.setUpdateTime(new Date());
            int ret = this.userService.updateUserByKey(user);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "修改密码失败");
            }
            return ResponseHelper.successFormat("修改密码成功");
        } catch (Exception e) {
            logger.error("resetPassword：", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "登录之后修改密码")
    public String updatePassword(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
            @ApiParam(required = true, value = "旧密码") @RequestParam(required = true) String oldPassword,
            @ApiParam(required = true, value = "新密码") @RequestParam(required = true) String newPassword,
            @ApiParam(required = false, value = "短信验证码") @RequestParam(required = false) String smsCode,
            @ApiParam(required = false, value = "邮箱账号Token") @RequestParam(required = false) String emailToken,
            @ApiParam(required = false, value = "邮箱验证码") @RequestParam(required = false) String mailCode,
            @ApiParam(required = false, value = "谷歌验证码") @RequestParam(required = false) String googleAuthCode,
            @ApiParam(value = "滑块验证码Token",required = false) @RequestParam(required = false) String BlockToken,
            @ApiParam(value = "x坐标（最左边的像素值）",required = false) @RequestParam(required = false) Integer xint
    ) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }
            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
            }
            if (!user.getPassword().equals(oldPassword)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "旧密码错误");
            }
            if (user.getPassword().equals(newPassword)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "新旧密码不能相同");
            }
            //根据用户选择的验证方式进行验证
            userService.checkAuthMethod(user, user.getLoginName(), smsCode, user.getEmail(), mailCode, emailToken, googleAuthCode, null, null);

            user.setPassword(newPassword);
            user.setUpdateTime(new Date());
            int ret = this.userService.updateUserByKey(user);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "修改密码失败");
            }
            return ResponseHelper.successFormat("修改密码成功");
        } catch (CommonException e) {
            logger.error("updatePassword：" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/user/sendMailCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "发送邮箱验证码")
    public String sendMailCode(
            @ApiParam(required = true, value = "邮箱账号") @RequestParam(required = true) String email,
            @ApiParam(value = "滑块验证码Token", required = true) @RequestParam(required = true) String BlockToken,
            @ApiParam(value = "x坐标（最左边的像素值）", required = true) @RequestParam(required = true) Integer xint,
            HttpServletRequest request) {
        try {
            if (!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "邮箱账户不合法");
            }
            if (!userService.checkBlockToken(xint, BlockToken, true)) {
                throw new CommonException(ErrorCodeEnum.EXCEPTION, "滑块验证错误");
            }
            //6位验证码
            String code = String.valueOf(new Random().nextInt(899999) + 100000);
            System.out.println("===============" + code);
            //使用邮箱号 +6位随机码 作为加密串，防止别的得到token进行别的操作
            String emailtoken = email + code;
            //使用验证码生成jwt Token密钥
            String tokens = JWTUtils.createJWT(emailtoken, null, 1000 * 60 * 30);

            //获取项目地址
            String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            //获取FTL邮件模板文件
            Template template = configuration.getTemplate("/mailTemplate/mail.ftl");
            //装载模板数据
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", email);//绑定的邮箱号
            map.put("mailCode", code);//验证码

            //发送邮件，并将数据填入模型渲染
            MailUtil.sendTemplateMail(new String[]{email}, "邮箱验证码", template, map);

            //返回前端的数据模型
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("token", tokens);
            return ResponseHelper.successFormat(hashMap);

        } catch (Exception e) {
            logger.error("sendMailCode：", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/user/bindMail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "绑定/修改邮箱账户")
    public String bindMail(@ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                           @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
                           @ApiParam(required = true, value = "资金密码") @RequestParam(required = true) String paymentPassword,
                           @ApiParam(required = false, value = "短信验证码") @RequestParam(required = false) String smsCode,
                           @ApiParam(required = true, value = "邮箱账号") @RequestParam(required = true) String email,
                           @ApiParam(required = false, value = "邮箱账号Token") @RequestParam(required = false) String emailToken,
                           @ApiParam(required = false, value = "邮箱验证码") @RequestParam(required = false) String mailCode,
                           @ApiParam(required = false, value = "谷歌验证码") @RequestParam(required = false) String googleAuthCode,
                           @ApiParam(value = "滑块验证码Token",required = false) @RequestParam(required = false) String BlockToken,
                           @ApiParam(value = "x坐标（最左边的像素值）",required = false) @RequestParam(required = false) Integer xint
    ) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }
            if (!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "邮箱账户不合法");
            }

            //获取user并修改邮箱账户
            User user = userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "无此用户");
            }
            UserPrivate userPrivate = userPrivateService.selectUserPrivateByKey(userId);
            if (!paymentPassword.equals(userPrivate.getPaymentPassword())) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "资金密码错误");
            }
            userService.checkAuthMethod(user, user.getLoginName(), smsCode, email, mailCode, emailToken, googleAuthCode, null, null);
            user.setEmail(email);
            int updateUserByKey = userService.updateUserByKey(user);
            if (updateUserByKey > 0) {
                return ResponseHelper.successFormat();
            } else {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "邮箱入库失败");
            }
        } catch (CommonException e) {
            logger.error("bindMail：" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/user/getRecommendedPeople", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取登录用户推荐的所有人详情", position = 1)
    public String getRecommendedPeople(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = false, value = "页码") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize,
            @ApiParam(required = true, value = "推荐码") @RequestParam(required = true) String recommendedCode,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId) {
        try {

            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }
            HashMap<String, Object> returnData = this.userService.selectRecommendedPeopleList(recommendedCode, null, page, pageSize);
            return ResponseHelper.successFormat(returnData);
        } catch (CommonException e) {
            logger.error("getRecommendedPeople:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/user/getTeamInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取我的团队信息", position = 1)
    public String getTeamInfo(@ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                              @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId) {
        try {

            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }
            if (TextUtils.isEmpty(userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "参数错误");
            }
            return ResponseHelper.successFormat(teamService.queryTeamInfo(userId));
        } catch (CommonException e) {
            logger.error("getRecommendedPeople:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/user/getDirectTeamList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取登录用户推荐的所有人详情", position = 1)
    public String getDirectPushUser(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = false, value = "页码") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize,
            @ApiParam(required = false, value = "搜索") @RequestParam(required = false) String filter,
            @ApiParam(required = false, value = "会员等级过滤") @RequestParam(required = false,defaultValue = "-1") int leverEnum,
            @ApiParam(required = true, value = "仅显示实名认证") @RequestParam(required = true) int isReal,
            @ApiParam(required = true, value = "推荐码") @RequestParam(required = true) String recommendedCode,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId) {
        try {

            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }
            if (TextUtils.isEmpty(recommendedCode)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "参数错误");
            }
            HashMap<String, Object> returnData = this.teamService.queryDirectTeamInfo(recommendedCode, filter, leverEnum, isReal != 0, page, pageSize);
            return ResponseHelper.successFormat(returnData);
        } catch (CommonException e) {
            logger.error("getRecommendedPeople:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/user/setPaymentPassword", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "首次设置资金密码")
    public String setPaymentPassword(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
            @ApiParam(required = true, value = "密码") @RequestParam(required = true) String paymentPassword,
            @ApiParam(required = true, value = "手机验证码") @RequestParam(required = true) String smsCode
    ) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }
            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
            }
            boolean check = this.smsBaoService.checkSms(user.getLoginName(), smsCode);
            if (!check) {
                return ResponseHelper.errorException(ErrorCodeEnum.SMSCODEERROR, "短信验证码错误");
            }
            UserPrivate userPrivate = userPrivateService.selectUserPrivateByKey(userId);
            if(userPrivate == null) {
                userPrivate = new UserPrivate();
                userPrivate.setUserId(userId);
                userPrivate.setPaymentPassword(paymentPassword);
                int ret = this.userPrivateService.insertUserPrivate(userPrivate);
                if (ret == 0) {
                    return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "资金密码设置失败");
                }
            }else{
                userPrivate = new UserPrivate();
                userPrivate.setUserId(userId);
                userPrivate.setPaymentPassword(paymentPassword);
                int ret = this.userPrivateService.updateUserPrivate(userPrivate);
                if (ret == 0) {
                    return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "资金密码设置失败");
                }
            }

            boolean paymentPasswordStatus = this.userPrivateService.checkPaymentPassword(userId);
            return ResponseHelper.successFormat(paymentPasswordStatus);
        } catch (CommonException e) {
            logger.error("setPaymentPassword" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/user/updatePaymentPassword", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "修改资金密码")
    public String updatePaymentPassword(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
            @ApiParam(required = true, value = "旧资金密码") @RequestParam(required = true) String oldPaymentPassword,
            @ApiParam(required = true, value = "新资金密码") @RequestParam(required = true) String newPaymentPassword,
            @ApiParam(required = false, value = "短信验证码") @RequestParam(required = false) String smsCode,
            @ApiParam(required = false, value = "邮箱账号Token") @RequestParam(required = false) String emailToken,
            @ApiParam(required = false, value = "邮箱验证码") @RequestParam(required = false) String mailCode,
            @ApiParam(required = false, value = "谷歌验证码") @RequestParam(required = false) String googleAuthCode,
            @ApiParam(value = "滑块验证码Token",required = false) @RequestParam(required = false) String BlockToken,
            @ApiParam(value = "x坐标（最左边的像素值）",required = false) @RequestParam(required = false) Integer xint
    ) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }
            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
            }
            UserPrivate userPrivate = this.userPrivateService.selectUserPrivateByKey(userId);
            if (userPrivate == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户资金密码未找到");
            }

            if (!userPrivate.getPaymentPassword().equals(oldPaymentPassword)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "旧资金密码错误");
            }
            if (userPrivate.getPaymentPassword().equals(newPaymentPassword)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "新旧资金密码不能相同");
            }
            userService.checkAuthMethod(user, user.getLoginName(), smsCode, user.getEmail(), mailCode, emailToken, googleAuthCode, null, null);
            userPrivate.setPaymentPassword(newPaymentPassword);
            userPrivate.setUpdateTime(new Date());
            int ret = this.userPrivateService.updateUserPrivate(userPrivate);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "修改资金密码失败");
            }
            return ResponseHelper.successFormat("修改资金密码成功");
        } catch (CommonException e) {
            logger.error("updatePaymentPassword" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

}
