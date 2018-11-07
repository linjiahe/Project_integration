package com.blockchain.commune.controller.wallet;


import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.Purse.PSubAccountTypeEnum;
import com.blockchain.commune.enums.Purse.PTransDirectorEnum;
import com.blockchain.commune.enums.Purse.PTransTypeEnum;
import com.blockchain.commune.enums.Purse.PurseStatusEnum;
import com.blockchain.commune.enums.SystemArgsEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.News;
import com.blockchain.commune.model.SystemArgs;
import com.blockchain.commune.model.User;
import com.blockchain.commune.service.NewsService;
import com.blockchain.commune.service.SystemService;
import com.blockchain.commune.service.UserService;
import com.blockchain.commune.service.wallet.PurseService;
import com.blockchain.commune.service.wallet.TransService;
import com.blockchain.commune.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Controller
@EnableAutoConfiguration
@Api(value = "交易信息", description = "交易注册积分")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})

public class TransController {

    @Autowired
    TransService transService;

    @Autowired
    UserService userService;

    @Autowired
    SystemService systemService;

    @Autowired
    PurseService purseService;

    @Autowired
    NewsService newsService;

    private final static Logger logger = LoggerFactory.getLogger(TransController.class);

    @RequestMapping(value = "/trans/addpostscore", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "增加阅读的积分")
    public String addPostScore(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
            @ApiParam(required = true, value = "文章id") @RequestParam(required = true) String postId
            ) {

        try {

            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }

            User user = userService.selectUserByKey(userId);
            if (user == null){
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR,"用户不存在");
            }


            this.transService.addPostScore(userId, postId);
            String mes = "阅读得积分";
            BigDecimal score = transService.getScore(userId, postId);

            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("mes",mes);
            hashMap.put("score",score);

            return ResponseHelper.successFormat(hashMap);

        } catch (CommonException e) {
            logger.error("addCheckInScore:", e.getMessage());
            return ResponseHelper.errorException(e.getCode(),e.getMessage());
        }
    }


    @RequestMapping(value = "/trans/readRepeat",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "判断用户是否重复阅读文章")
    public String readRepeat(@ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                             @ApiParam(required = true, value = "userId") @RequestParam(required = true) String userId,
                             @ApiParam(required = true, value = "文章id") @RequestParam(required = true) String postId){


        try {

            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录过期，请重新登录");
            }

            User user = userService.selectUserByKey(userId);
            if (user == null){
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR,"用户不存在");
            }

            boolean read = transService.getRead(userId, postId);
            boolean flag;
            if (read){
                flag = false;//重复阅读
            }else {
                flag = true;//未阅读
            }
            return ResponseHelper.successFormat("flag",flag);
        } catch (CommonException e) {
            logger.error("addCheckInScore:", e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }



    @RequestMapping(value = "/trans/addCheckInScore", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "签到送积分")
    public String addCheckInScore(@ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                                  @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId
    ) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }

            User user = userService.selectUserByKey(userId);
            if (user == null){
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR,"用户不存在");
            }

            int attendanceDay = transService.addCheckInScore(user);
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("attendanceStatus", true);
            int[] attendanceScore = transService.getAttendanceScoreArr();
            hashMap.put("attendanceScore", attendanceScore);
            hashMap.put("attendanceDay", attendanceDay);
            return ResponseHelper.successFormat(hashMap);
        } catch (CommonException ce) {
            logger.error("addCheckInScore:"+ ce.getMessage());
            return ResponseHelper.errorException(ce.getCode(),ce.getMessage());
        } catch (Exception e) {
            logger.error("addCheckInScore:"+ e.getMessage());
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/trans/getExchangeRate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取积分兑换BCT汇率")
    public String getExchangeRate() {
        try {
            List<SystemArgs> argsList = this.systemService.selectSystemArgsById(SystemArgsEnum.TB_TO_BCT.toString());
            if (CollectionUtils.isEmpty(argsList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "获取不到汇率,请联系管理员");
            }
            SystemArgs args = argsList.get(0);
            BigDecimal exchangeRate = new BigDecimal(args.getSysValue());
            return ResponseHelper.successFormat(exchangeRate);
        } catch (Exception e) {
            logger.error("getTbToBct:", e.getMessage());
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/trans/TbToBct", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "积分兑换BTC")
    public String TbToBct(@ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                          @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
                          @ApiParam(required = true, value = "要兑换的积分") @RequestParam(required = true) BigDecimal TBNum,
                          @ApiParam(required = true, value = "兑换所得的BCT") @RequestParam(required = true) BigDecimal BCTNum,
                          @ApiParam(required = true, value = "汇率") @RequestParam(required = true) BigDecimal exchangeRate
    ) {
        try {
            //判断是否登陆
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }

            List<SystemArgs> argsList = this.systemService.selectSystemArgsById(SystemArgsEnum.TB_TO_BCT.toString());
            if (CollectionUtils.isEmpty(argsList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "获取不到汇率,请联系管理员");
            }
            //判断当前用户是否已进行实名认证
            int check = userService.getUserValidate(userId);
            if (check == 0){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"请先进行初级实名认证");
            }
            SystemArgs args = argsList.get(0);
            BigDecimal systemExchangeRate = new BigDecimal(args.getSysValue());

            if (0 != systemExchangeRate.compareTo(exchangeRate)) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "汇率不正确，请重新刷新页面");
            }
            if (0 != TBNum.multiply(exchangeRate).setScale(8,BigDecimal.ROUND_DOWN).compareTo(BCTNum)) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "兑换数据不正确，请重新刷新页面");
            }
            if (TBNum.compareTo(BigDecimal.ZERO) == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "兑换积分不能为0");
            }
            if (TBNum.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "兑换积分不能为负数");
            }
            if (BCTNum.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "兑换BCT不能为负数");
            }
            //添加交易记录
            //积分交易记录
            this.transService.TBToBCT(userId, TBNum);
            //币种交易记录
            this.purseService.TBToBCT(userId, BCTNum);

            return ResponseHelper.successFormat("兑换成功");
        } catch (CommonException e) {
            logger.error("TbToBct:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }


    @RequestMapping(value = "/trans/shareNews", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分享快讯获得积分")
    public String shareNews(@ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
                            @ApiParam(required = true, value = "快讯ID") @RequestParam(required = true) String newId
    ) {
        try {
            //判断是否登陆
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            int i = transService.selectTodayShareNewCount(userId);
            if(i>=5){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"每天分享快讯收益只能为5次");
            }
            News news = newsService.selectNewsByKey(newId);
            if(news == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"不存在该快讯ID");
            }
            transService.shareNews(userId,new BigDecimal(5),new BigDecimal(5));
            return ResponseHelper.successFormat("兑换成功");
        } catch (CommonException e) {
            logger.error("shareNews:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

}