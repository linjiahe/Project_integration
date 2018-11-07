package com.blockchain.commune.controller.admin;

import com.blockchain.commune.custommodel.CheckedOrder;
import com.blockchain.commune.custommodel.UserCoinDetail;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.Purse.PSubAccountTypeEnum;
import com.blockchain.commune.enums.Purse.PTransDirectorEnum;
import com.blockchain.commune.enums.Purse.PTransTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.service.AdminService;
import com.blockchain.commune.service.wallet.PurseService;
import com.blockchain.commune.service.wallet.ScoreService;
import com.blockchain.commune.utils.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Api(value = "财务相关（后台管理）", description = "财务相关（后台管理）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/admin/finance")
public class FinanceAdminController
{
    @Autowired
    ScoreService scoreService;

    @Autowired
    PurseService purseService;

    @RequestMapping(value = "/query_score_args", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询积分参数")
    public String queryScoreArgs()
    {
        HashMap<String, Object> returnData  = this.scoreService.queryScoreArgs();
        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/update_score_args", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新积分参数", position = 1)
    public String updateScoreArgs(
            @ApiParam(required = false,value = "key") @RequestParam(required = false)String key,
                    @ApiParam(required = false,value = "value") @RequestParam(required = false)String value)
    {

        if(TextUtils.isEmpty(key))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "Key为空");
        }
        List<WalletTranslogType> walletTranslogTypes = this.scoreService.queryScoreArgs(key);
        if (CollectionUtils.isEmpty(walletTranslogTypes)) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "参数类型未找到");
        }
        WalletTranslogType translogType = walletTranslogTypes.get(0);
        translogType.setTransConfig(value);

        int ret = this.scoreService.updateScoreArgs(translogType);
        if (ret == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

    @RequestMapping(value = "/query_score_log", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分页根据条件查询积分记录")
    public String queryScoreLog(
            @ApiParam(required = false,value = "根据币名查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize
    )
    {
        HashMap<String, Object> returnData = this.scoreService.queryScoreLog(filter,page,pageSize);
        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/query_score_rank", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询积分参数")
    public String queryScoreRank(
            @ApiParam(required = false,value = "数量") @RequestParam(required = false)Integer count
    )
    {
        if(count == null)
        {
            count = 0 ;
        }
        HashMap<String, Object> returnData  = this.scoreService.getScoreRank(count);
        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/query_coin_log", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分页根据条件查询积分记录")
    public String queryCoinOperateLog(
            @ApiParam(required = false,value = "根据币名查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize
    )
    {
        HashMap<String, Object> returnData = this.purseService.queryCoinOperateLog(filter,page,pageSize);
        return ResponseHelper.successFormat(returnData);
    }


    @RequestMapping(value = "/query_coin_detail", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询")
    public String queryUserCoinDetail(
            @ApiParam(required = false,value = "根据币名查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize
    )
    {
        HashMap<String, Object> returnData = this.purseService.queryUserCoinDetail(filter,page,pageSize);
        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/give_out_coin", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "发放币")
    public String giveOutCoin(String accountId,String coinName,float count,String remark)
    {
        if(TextUtils.isEmpty(accountId))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "accountId为空");
        }
        if(TextUtils.isEmpty(coinName))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "coinName为空");
        }
        if(count <= 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "数量不能小于0");
        }
        List<PurseSubAccount> subAccounts = this.purseService.selectByAccountIdAndCoinName(accountId,coinName);
        if(CollectionUtils.isEmpty(subAccounts))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户币种未找到");
        }

        PurseSubAccount subAccount = subAccounts.get(0);

        //更新用户交易记录表
        PurseTranslog purseTranslog = new PurseTranslog();
        String transId1 = IdUtil.getTransId();
        purseTranslog.setTransId(transId1);
        purseTranslog.setTransType(PTransTypeEnum.PUT_OUT.toString());
        purseTranslog.setAccountId(accountId);
        purseTranslog.setSubAccountId(subAccount.getSubAccountId());
        purseTranslog.setSubAccountType(coinName);
        purseTranslog.setTransTitle(remark);
        purseTranslog.setTransAmount(new BigDecimal(count));
        purseTranslog.setTransDirector(PTransDirectorEnum.IN.toString());
        purseTranslog.setSourceBalance(subAccount.getTotalAivilable());
        purseTranslog.setIsBlockTrans(new Byte("0"));

        purseTranslog.setTageetBalance(subAccount.getTotalAivilable().add(new BigDecimal(count)));

        purseTranslog.setRemark(remark);
        int ret5 = purseService.insertTransLog(purseTranslog);
        if (ret5 == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "插入交易日志失败");
        }
        //更新用户余额表
        subAccount.setAvailableAivilable(subAccount.getAvailableAivilable().add(new BigDecimal(count)));
        subAccount.setTotalAivilable(subAccount.getTotalAivilable().add(new BigDecimal(count)));
        int res = purseService.updatePurseSubAccount(subAccount);
        if(res == 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

    @RequestMapping(value = "/deduct_coin", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "扣除币")
    public String deductCoin(String accountId,String coinName,float count,String remark)
    {
        if(TextUtils.isEmpty(accountId))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "accountId为空");
        }
        if(TextUtils.isEmpty(coinName))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "coinName为空");
        }
        if(count <= 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "数量不能小于0");
        }
        List<PurseSubAccount> subAccounts = this.purseService.selectByAccountIdAndCoinName(accountId,coinName);
        if(CollectionUtils.isEmpty(subAccounts))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户币种未找到");
        }

        PurseSubAccount subAccount = subAccounts.get(0);

        if(subAccount.getAvailableAivilable().floatValue() < count)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "可用余额不足");
        }
        //更新用户交易记录表
        PurseTranslog purseTranslog = new PurseTranslog();
        String transId1 = IdUtil.getTransId();
        purseTranslog.setTransId(transId1);
        purseTranslog.setTransType(PTransTypeEnum.DEDUCT.toString());
        purseTranslog.setAccountId(accountId);
        purseTranslog.setSubAccountId(subAccount.getSubAccountId());
        purseTranslog.setSubAccountType(coinName);
        purseTranslog.setTransTitle(remark);
        purseTranslog.setTransAmount(new BigDecimal(count));
        purseTranslog.setTransDirector(PTransDirectorEnum.OUT.toString());
        purseTranslog.setSourceBalance(subAccount.getTotalAivilable());
        purseTranslog.setIsBlockTrans(new Byte("0"));

        purseTranslog.setTageetBalance(subAccount.getTotalAivilable().subtract(new BigDecimal(count)));

        purseTranslog.setRemark(remark);
        int ret5 = purseService.insertTransLog(purseTranslog);
        if (ret5 == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "插入交易日志失败");
        }
        //更新用户余额表
        subAccount.setAvailableAivilable(subAccount.getAvailableAivilable().subtract(new BigDecimal(count)));
        subAccount.setTotalAivilable(subAccount.getTotalAivilable().subtract(new BigDecimal(count)));
        int res = purseService.updatePurseSubAccount(subAccount);
        if(res == 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

    @RequestMapping(value = "/block_coin", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "冻结币")
    public String blockCoin(String accountId,String coinName,float count,String remark)
    {
        if(TextUtils.isEmpty(accountId))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "accountId为空");
        }
        if(TextUtils.isEmpty(coinName))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "coinName为空");
        }
        if(count <= 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "数量不能小于0");
        }
        List<PurseSubAccount> subAccounts = this.purseService.selectByAccountIdAndCoinName(accountId,coinName);
        if(CollectionUtils.isEmpty(subAccounts))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户币种未找到");
        }

        PurseSubAccount subAccount = subAccounts.get(0);
        if(subAccount.getAvailableAivilable().floatValue() < count)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "可用余额不足");
        }
        //更新用户交易记录表
        PurseTranslog purseTranslog = new PurseTranslog();
        String transId1 = IdUtil.getTransId();
        purseTranslog.setTransId(transId1);
        purseTranslog.setTransType(PTransTypeEnum.BLOCK.toString());
        purseTranslog.setAccountId(accountId);
        purseTranslog.setSubAccountId(subAccount.getSubAccountId());
        purseTranslog.setSubAccountType(coinName);
        purseTranslog.setTransTitle(remark);
        purseTranslog.setTransAmount(new BigDecimal(count));
        purseTranslog.setTransDirector(PTransDirectorEnum.BLOCK.toString());
        purseTranslog.setSourceBalance(subAccount.getTotalAivilable());
        purseTranslog.setIsBlockTrans(new Byte("0"));

        purseTranslog.setTageetBalance(subAccount.getTotalAivilable());

        purseTranslog.setRemark(remark);
        int ret5 = purseService.insertTransLog(purseTranslog);
        if (ret5 == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "插入交易日志失败");
        }
        //更新用户余额表
        subAccount.setAvailableAivilable(subAccount.getAvailableAivilable().subtract(new BigDecimal(count)));
        subAccount.setBlockAivilable(subAccount.getBlockAivilable().add(new BigDecimal(count)));
        int res = purseService.updatePurseSubAccount(subAccount);
        if(res == 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }


    @RequestMapping(value = "/thawing_coin", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "解冻币")
    public String thawingCoin(String accountId,String coinName,float count,String remark)
    {
        if(TextUtils.isEmpty(accountId))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "accountId为空");
        }
        if(TextUtils.isEmpty(coinName))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "coinName为空");
        }
        if(count <= 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "数量不能小于0");
        }
        List<PurseSubAccount> subAccounts = this.purseService.selectByAccountIdAndCoinName(accountId,coinName);
        if(CollectionUtils.isEmpty(subAccounts))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户币种未找到");
        }

        PurseSubAccount subAccount = subAccounts.get(0);
        if(subAccount.getBlockAivilable().floatValue() < count)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "被冻结余额不足");
        }
        //更新用户交易记录表
        PurseTranslog purseTranslog = new PurseTranslog();
        String transId1 = IdUtil.getTransId();
        purseTranslog.setTransId(transId1);
        purseTranslog.setTransType(PTransTypeEnum.THAW.toString());
        purseTranslog.setAccountId(accountId);
        purseTranslog.setSubAccountId(subAccount.getSubAccountId());
        purseTranslog.setSubAccountType(coinName);
        purseTranslog.setTransTitle(remark);
        purseTranslog.setTransAmount(new BigDecimal(count));
        purseTranslog.setTransDirector(PTransDirectorEnum.THAW.toString());
        purseTranslog.setSourceBalance(subAccount.getTotalAivilable());
        purseTranslog.setIsBlockTrans(new Byte("0"));

        purseTranslog.setTageetBalance(subAccount.getTotalAivilable());

        purseTranslog.setRemark(remark);
        int ret5 = purseService.insertTransLog(purseTranslog);
        if (ret5 == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "插入交易日志失败");
        }
        //更新用户余额表
        subAccount.setAvailableAivilable(subAccount.getAvailableAivilable().add(new BigDecimal(count)));
        subAccount.setBlockAivilable(subAccount.getBlockAivilable().subtract(new BigDecimal(count)));
        int res = purseService.updatePurseSubAccount(subAccount);
        if(res == 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

    @RequestMapping(value = "/audit_order_list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查找待审核的提币订单(后台管理)")
    public String queryAuditOrderByConditional(
            @ApiParam(required = false,value = "根据币名查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize)
    {
        HashMap<String, Object> returnData = this.purseService.queryAuditOrderByConditional(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/update_audit_order", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "刷新待审核订单(后台管理)")
    public String updateAuditOrder(@RequestBody HashMap<String,List<CheckedOrder>> body)
    {
        List<CheckedOrder> checkedOrders = body.get("checkedData");
        if(CollectionUtils.isEmpty(checkedOrders))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "参数未空");
        }
        int res =  this.purseService.updateAuditOrder(body.get("checkedData"));
        if(res == 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }


    @RequestMapping(value = "/transExamine", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "外部提币拒绝审核(后台管理)")
    public String transExamine(@ApiParam(required = true,value = "交易ID") @RequestParam(required = true) String transId,
                               @ApiParam(required = false,value = "备注") @RequestParam(required = false) String remark)
    {
        try {
            if(StringUtils.isEmpty(remark)){
                remark = "（驳回提币）";
            }
            boolean b = purseService.transExamine(transId, remark);
            if(b){
                return ResponseHelper.successFormat();
            }else{
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"审核失败");
            }
        }catch (CommonException e){
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }

    @RequestMapping(value = "/transInsideExamine", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "内部提币审核(后台管理)")
    public String transInsideExamine(@ApiParam(required = true,value = "交易ID") @RequestParam(required = true) String transId,
                                     @ApiParam(required = true,value = "审核结果") @RequestParam(required = true) boolean isExamine,
                                     @ApiParam(required = false,value = "备注") @RequestParam(required = false) String remark)
    {
        try {
            if(StringUtils.isEmpty(remark)&&isExamine){
                remark = "（审核通过）";
            }else if(StringUtils.isEmpty(remark)&&!isExamine){
                remark = "（审核驳回）";
            }
            boolean b = purseService.transInsideExamine(transId,isExamine, remark);
            if(b){
                return ResponseHelper.successFormat();
            }else{
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"审核失败");
            }
        }catch (CommonException e){
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }

    @RequestMapping(value = "/queryPurseTransLog", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "币来源查询")
    public String queryPurseTransLogByUserId(
            @ApiParam(required = false,value = "根据币名查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize
    )
    {
        HashMap<String, Object> returnData = this.purseService.queryPurseTransLogByUserId(filter,page,pageSize);
        return ResponseHelper.successFormat(returnData);
    }
}
