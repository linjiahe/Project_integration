package com.blockchain.commune.enums.Purse;

/**
 * Created by wrb on 2018/9/3
 */
public enum PTransTypeEnum {
    TRANSFER//转账
    ,DISMISSAL//转账驳回
    ,RECHARGE//充值，转入
    //积分兑换BCT
    ,TB_TO_BCT
    //违规积分兑换为BCT追回
    ,VIOLATION_OPERATION
    // 冻结
    ,BLOCK
    //解冻
    ,THAW
    //后台管理扣除
    ,DEDUCT
    //后台管理发放
    ,PUT_OUT
}

