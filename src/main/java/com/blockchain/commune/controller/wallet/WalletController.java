package com.blockchain.commune.controller.wallet;


import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.Wallet.TransTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.WalletDailySummary;
import com.blockchain.commune.model.WalletSubAccount;
import com.blockchain.commune.model.WalletTranslog;
import com.blockchain.commune.service.wallet.WalletService;
import com.blockchain.commune.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Controller
@EnableAutoConfiguration
@Api(value = "钱包相关API", description = "钱包相关API")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class WalletController {

    @Autowired
    WalletService walletService;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(WalletController.class);

    @RequestMapping(value = "/wallet/balance", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取钱包余额信息")
    public String queryBalance(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId

    ) {
        try {
            //判断是否登陆
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            //查询当前用户积分钱包信息详情
            List<WalletSubAccount> walletSubAccount = walletService.queryBalance(userId);
            //返回信息为空代表当前用户无积分钱包信息
            if (CollectionUtils.isEmpty(walletSubAccount)) {
                return ResponseHelper.errorEmptyData(",用户积分钱包信息不存在");
            }
            //初始化一个积分钱包子账户对象用户返回数据，设置金额为0否在下面的循环递加报空指针异常
            WalletSubAccount walletSUM = new WalletSubAccount();
            walletSUM.setTotalAivilable(new BigDecimal(0));
            walletSUM.setAvailableAivilable(new BigDecimal(0));
            walletSUM.setBlockAivilable(new BigDecimal(0));
            //循环累加各个子账户的金额
            for (int i = 0; i < walletSubAccount.size(); i++) {
                WalletSubAccount wsa = walletSubAccount.get(i);
                walletSUM.setTotalAivilable(walletSUM.getTotalAivilable().add(wsa.getTotalAivilable()));
                walletSUM.setAvailableAivilable(walletSUM.getAvailableAivilable().add(wsa.getAvailableAivilable()));
                walletSUM.setBlockAivilable(walletSUM.getBlockAivilable().add(wsa.getBlockAivilable()));

            }
            return ResponseHelper.successFormat(walletSUM);
        } catch (CommonException e) {
            logger.error("queryBalance:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("queryBalance:" + e.getMessage());
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "发生未知错误");
        }
    }


    @RequestMapping(value = "/wallet/querytodaybalance", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取今日积分")
    public String queryTodayBalance(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId

    ) {
        try {
            //判断是否登陆
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            //获取当日日结积分详情
            WalletDailySummary walletDailySummary = walletService.queryWalletDailySummary(userId);
            //返回为空表示当日无积分信息
            if (walletDailySummary == null) {
                return ResponseHelper.successFormat(new WalletDailySummary());
            }
            return ResponseHelper.successFormat(walletDailySummary);
        } catch (CommonException e) {
            logger.error("queryTodayBalance:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("queryTodayBalance:" + e.getMessage());
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "发生未知错误");
        }


    }


    /**
     * @param token
     * @param userId
     * @param transType transType为空，默认查询所有类型
     * @param beginDate 开始时间为空默认，前3天
     * @param endDate   结束时间为空默认当前
     * @return
     */
    @RequestMapping(value = "/wallet/translog", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询交易明细")
    public String queryTrans(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
            @ApiParam(required = false, value = "transType为空，默认查询所有类型") @RequestParam(required = false) String transType,
            @ApiParam(required = false, value = "开始时间为空默认，前3天") @RequestParam(required = false) Date beginDate,
            @ApiParam(required = false, value = "结束时间为空默认当前") @RequestParam(required = false) Date endDate,
            @ApiParam(required = false, value = "当前页(0开始)") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize
    ) {
        try {
            //判断是否登陆
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            String[] tranType = null;
            if (!StringUtils.isBlank(transType)) {
                transType = transType.toUpperCase();
                tranType = transType.split(",");
            }

            //判断开始时间是否为空，为空则默认为三天前的00：00：00开始
            if (beginDate == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, -3);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                beginDate = calendar.getTime();
            }
            //判断结数时间是否为空，为空则默认为当前时间
            if (endDate == null) {
                endDate = new Date();
            }
            HashMap<String, Object> walletTranslogs = walletService.queryWalletTransLogs(userId, tranType, beginDate, endDate, page, pageSize);
            return ResponseHelper.successFormat(walletTranslogs);
        } catch (CommonException e) {
            logger.error("queryTrans:" + e.getMessage());
            e.printStackTrace();
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("queryTrans:" + e.getMessage());
            e.printStackTrace();
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "发生未知错误");
        }
    }

    /**
     * @param token
     * @param userId
     * @param transType transType为空，默认查询所有类型
     * @param beginDate 开始时间为空默认，前3天
     * @param endDate   结束时间为空默认当前
     * @return
     */
    @RequestMapping(value = "/wallet/translogTypeCount", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询指定交易类型总额")
    public String translogTypeCount(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
            @ApiParam(required = true, value = "transType为空，默认查询所有类型") @RequestParam(required = true) TransTypeEnum transType,
            @ApiParam(required = false, value = "开始时间为空默认，当天0点") @RequestParam(required = false) Date beginDate,
            @ApiParam(required = false, value = "结束时间为空默认当前") @RequestParam(required = false) Date endDate
    ) {
        try {
            //判断是否登陆
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }

            //判断开始时间是否为空，为空则默认为当日的00：00：00开始
            if (beginDate == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                beginDate = calendar.getTime();
            }
            //判断结数时间是否为空，为空则默认为当前时间
            if (endDate == null) {
                endDate = new Date();
            }
            BigDecimal count = walletService.translogTypeCount(userId, transType.toString(), beginDate, endDate);
            Map<String, Object> map = new HashMap<>();
            map.put("count", count);
            return ResponseHelper.successFormat(map);
        } catch (CommonException e) {
            logger.error("translogTypeCount:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("translogTypeCount:" + e.getMessage());
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "发生未知错误");
        }
    }


}




