package com.blockchain.commune.controller.wallet;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.Purse.PSubAccountTypeEnum;
import com.blockchain.commune.enums.Purse.PTransDirectorEnum;
import com.blockchain.commune.enums.Purse.PTransTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.PurseDailySummary;
import com.blockchain.commune.model.PurseSubAccount;
import com.blockchain.commune.model.UserPrivate;
import com.blockchain.commune.model.WalletSubAccount;
import com.blockchain.commune.service.UserPrivateService;
import com.blockchain.commune.service.UserService;
import com.blockchain.commune.service.wallet.PurseService;
import com.blockchain.commune.service.wallet.WalletService;
import com.blockchain.commune.utils.CreateQrcodeUtil;
import com.blockchain.commune.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@Controller
@EnableAutoConfiguration
@Api(value = "币种钱包相关API", description = "币种钱包相关API")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class PurseController {

    @Autowired
    PurseService purseService;
    
    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    @Autowired
    UserPrivateService userPrivateService;

    @Value("${web.upload-path}")
    private String uploadPath;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(PurseController.class);

    @RequestMapping(value = "/purse/balance", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取钱包余额信息")
    public String queryBalance(
            HttpServletRequest req,
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
            @ApiParam(required = true, value = "币种类型") @RequestParam(required = true) PSubAccountTypeEnum typeEnum


    ) {
        try {
            //判断是否登陆
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            //查询当前用户币种钱包信息详情
            List<PurseSubAccount> purseSubAccountList = purseService.queryBalance(userId, typeEnum.toString());
            //返回信息为空代表当前用户无币种钱包信息
            if (CollectionUtils.isEmpty(purseSubAccountList)) {
                return ResponseHelper.errorEmptyData(",用户币种钱包信息不存在");
            }
            PurseSubAccount purseSubAccount = purseSubAccountList.get(0);
            if (StringUtils.isNotBlank(purseSubAccount.getPurseAddress())) {
                String serverPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/";
                File file = new File(uploadPath + purseSubAccount.getPurseAddressQrcode());
                if (!file.exists() || null == purseSubAccount.getPurseAddressQrcode()) {
                    String path = CreateQrcodeUtil.createQrcode(purseSubAccount.getPurseAddress(), this.uploadPath, "purse_address");
                    PurseSubAccount purseSubAccount1 = new PurseSubAccount();
                    purseSubAccount1.setPurseAddressQrcode(path);
                    purseSubAccount1.setSubAccountId(purseSubAccount.getSubAccountId());
                    int ret = this.purseService.updatePurseSubAccount(purseSubAccount1);
                    if (ret == 0) {
                        return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "新增钱包地址二维码失败");
                    }
                    purseSubAccount.setPurseAddressQrcode(serverPath + path);
                } else {
                    purseSubAccount.setPurseAddressQrcode(serverPath + purseSubAccount.getPurseAddressQrcode());
                }
            }
            return ResponseHelper.successFormat(purseSubAccount);
        } catch (CommonException e) {
            logger.error("queryBalance:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("queryBalance:" + e.getMessage());
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "发生未知错误");
        }
    }


    @RequestMapping(value = "/purse/querytodaybalance", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取今日BCT交易详情")
    public String queryTodayBalance(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId

    ) {
        try {
            //判断是否登陆
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            //获取当日日结币种详情
            PurseDailySummary PurseDailySummary = purseService.queryPurseDailySummary(userId);
            //返回为空表示当日无币种信息
            if (PurseDailySummary == null) {
                return ResponseHelper.successFormat(new PurseDailySummary());
            }
            return ResponseHelper.successFormat(PurseDailySummary);
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
    @RequestMapping(value = "/Purse/translog", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询交易明细")
    public String queryTrans(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
            @ApiParam(required = false, value = "transType为空，默认查询所有类型") @RequestParam(required = false) PTransTypeEnum transType,
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
            //判断类型是否为空，为空查询所有类型
            String tranType = "";
            if (transType != null) {
                tranType = transType.toString();
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
            HashMap<String, Object> PurseTranslogs = purseService.queryPurseTransLogs(userId, tranType, beginDate, endDate, page, pageSize);
            return ResponseHelper.successFormat(PurseTranslogs);
        } catch (CommonException e) {
            logger.error("queryTrans:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("queryTrans:" + e.getMessage());
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "发生未知错误");
        }
    }


    /**
     * 提币（转账）
     *
     * @param token
     * @param userId
     * @param purseAddress
     * @param pSubAccountTypeEnum
     * @param score
     * @param remark
     * @return
     */
    @RequestMapping(value = "/Purse/transCoin", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "提币（转账）")
    public String transCoin(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
            @ApiParam(required = true, value = "转入钱包地址") @RequestParam(required = true) String purseAddress,
            @ApiParam(required = true, value = "币种") @RequestParam(required = true) PSubAccountTypeEnum pSubAccountTypeEnum,
            @ApiParam(required = true, value = "交易金额") @RequestParam(required = true) BigDecimal score,
            @ApiParam(required = true, value = "矿工费") @RequestParam(required = true) BigDecimal fee,
            @ApiParam(required = true, value = "是否上链转账") @RequestParam(required = true) String upAndDown,
            @ApiParam(required = true, value = "资金密码") @RequestParam(required = true) String paymentPassword,
            @ApiParam(required = false, value = "交易备注") @RequestParam(required = false) String remark
    ) {
        try {
            //判断是否登陆
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            //判断当前用户是否已进行实名认证
            int check = userService.getUserValidate(userId);
            if (check != 3){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"请先进行高级实名认证");
            }
            UserPrivate userPrivate = userPrivateService.selectUserPrivateByKey(userId);
            if(null == userPrivate || !paymentPassword.equals(userPrivate.getPaymentPassword())){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "资金密码错误");
            }
            purseService.transCoin(userId, purseAddress, pSubAccountTypeEnum, score, "转账", fee,upAndDown, remark, PTransDirectorEnum.OUT, PTransTypeEnum.TRANSFER);
            return ResponseHelper.successFormat("交易已发起");
        } catch (CommonException e) {
            logger.error("transCoin:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("transCoin:" + e.getMessage());
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, e.getMessage());
        }
    }

    /**
     * 查询所有币种余额及积分余额
     *
     * @param token
     * @param userId
     * @param search
     * @param display
     * @return
     */
    @RequestMapping(value = "/Purse/getPurseWalletBalance", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "积分币种余额查询")
    public String getBalance(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String userId,
            @ApiParam(required = false, value = "搜索值") @RequestParam(required = false) String search,
            @ApiParam(required = false, value = "是否隐藏0余额币种") @RequestParam(required = false) boolean display
    ) {
        try {
            //判断是否登陆
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            List<WalletSubAccount> list = walletService.queryBalance(userId);
            //初始化一个积分钱包子账户对象用户返回数据，设置金额为0否则下面的循环递加报空指针异常
            WalletSubAccount walletSUM = new WalletSubAccount();
            walletSUM.setTotalAivilable(new BigDecimal(0));
            walletSUM.setAvailableAivilable(new BigDecimal(0));
            walletSUM.setBlockAivilable(new BigDecimal(0));
            //循环累加各个子账户的积分
            for (int i = 0; i < list.size(); i++) {
                WalletSubAccount wsa = list.get(i);
                walletSUM.setTotalAivilable(walletSUM.getTotalAivilable().add(wsa.getTotalAivilable()));
                walletSUM.setAvailableAivilable(walletSUM.getAvailableAivilable().add(wsa.getAvailableAivilable()));
                walletSUM.setBlockAivilable(walletSUM.getBlockAivilable().add(wsa.getBlockAivilable()));
            }
            List<PurseSubAccount> all = purseService.queryBalance(userId, search,display);
            Map<String,Object> map = new HashMap<>();
            map.put("wallet",walletSUM);
            map.put("purse",all);
            BigDecimal walletCNY = walletSUM.getTotalAivilable().divide(new BigDecimal(100));
            for (int i = 0; i < all.size() ; i++) {
                walletCNY = walletCNY.add(all.get(i).getTotalAivilable());
            }
            map.put("cny",walletCNY);
            return ResponseHelper.successFormat(map);
        } catch (CommonException e) {
            logger.error("transCoin:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("transCoin:" + e.getMessage());
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, e.getMessage());
        }
    }

    /**
     * 根据钱包地址查询用户是否为平台用户
     * @param purseAddress
     * @return
     */
    @RequestMapping(value = "/Purse/queryUserInPlatform", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "根据钱包地址查询用户是否为平台用户")
    public String queryUserInPlatform(
            @ApiParam(required = true, value = "purseAddress") @RequestParam(required = true) String purseAddress
    ) {
        try {
            if(purseService.queryUserInPlatform(purseAddress)!=null){
                return ResponseHelper.successFormat();
            }else{
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"该地址用户非平台用户");
            }
        } catch (CommonException e) {
            logger.error("queryUserInPlatform:" + e.getMessage());
            return ResponseHelper.errorException(e.getCode(),e.getMessage());
        }
    }





}
