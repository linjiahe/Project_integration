package com.blockchain.commune.customemapper.wallet;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * Created by wrb on 2018/10/10
 */
public interface CustomTransFlowMapper {

    /**
     * 不冻结，加积分的更新语句
     * @param score
     * @param subAccountId 子账号id
     * @return
     */
    @Update("update wallet_sub_account " +
            "set total_aivilable=total_aivilable+#{score},available_aivilable=available_aivilable+#{score} " +
            "where available_aivilable+#{score}>=0 and sub_account_id=#{subAccountId}")
    int updateFalseAddWallet(@Param("score")BigDecimal score,@Param("subAccountId")String subAccountId);
    /**
     * 不冻结，减积分的更新语句
     * @param score
     * @param subAccountId 子账号id
     * @return
     */
    @Update("update wallet_sub_account " +
            "set total_aivilable=total_aivilable-#{score},available_aivilable=available_aivilable-#{score} " +
            "where available_aivilable-#{score}>=0 and sub_account_id=#{subAccountId}")
    int updateFalseSubWallet(@Param("score")BigDecimal score,@Param("subAccountId")String subAccountId);

    /**
     * 冻结，加积分的更新语句
     * @param score
     * @param subAccountId 子账号id
     * @return
     */
    @Update("update wallet_sub_account " +
            "set total_aivilable=total_aivilable+#{score},block_aivilable=block_aivilable+#{score} " +
            "where block_aivilable+#{score}>=0 and sub_account_id=#{subAccountId}")
    int updateTrueAddWallet(@Param("score")BigDecimal score,@Param("subAccountId")String subAccountId);

    /**
     * 冻结，减积分的更新语句 score必须为负数
     * @param score
     * @param subAccountId 子账号id
     * @return
     */
    @Update("update wallet_sub_account " +
            "set available_aivilable=available_aivilable-#{score},block_aivilable=block_aivilable+#{score} " +
            "where available_aivilable-#{score}>=0 and sub_account_id=#{subAccountId}")
    int updateTrueSubWallet(@Param("score")BigDecimal score,@Param("subAccountId")String subAccountId);

    /**
     * 不冻结，加币的更新语句
     * @param score
     * @param subAccountId 子账号id
     * @return
     */
    @Update("update purse_sub_account " +
            "set total_aivilable=total_aivilable+#{score},available_aivilable=available_aivilable+#{score} " +
            "where available_aivilable+#{score}>=0 and sub_account_id=#{subAccountId}")
    int updateFalseAddPurse(@Param("score")BigDecimal score,@Param("subAccountId")String subAccountId);

    /**
     * 不冻结，减币的更新语句
     * @param score
     * @param subAccountId 子账号id
     * @return
     */
    @Update("update purse_sub_account " +
            "set total_aivilable=total_aivilable-#{score},available_aivilable=available_aivilable-#{score} " +
            "where available_aivilable-#{score}>=0 and sub_account_id=#{subAccountId}")
    int updateFalseSubPurse(@Param("score")BigDecimal score,@Param("subAccountId")String subAccountId);

    /**
     * 冻结，加币的更新语句
     * @param score
     * @param subAccountId 子账号id
     * @return
     */
    @Update("update purse_sub_account " +
            "set total_aivilable=total_aivilable+#{score},block_aivilable=block_aivilable+#{score} " +
            "where block_aivilable+#{score}>=0 and sub_account_id=#{subAccountId}")
    int updateTrueAddPurse(@Param("score")BigDecimal score,@Param("subAccountId")String subAccountId);

    /**
     * 冻结，减币的更新语句 score必须为负数
     * @param score
     * @param subAccountId 子账号id
     * @return
     */
//    @Update("update purse_sub_account " +
//            "set available_aivilable=available_aivilable+#{score},block_aivilable=block_aivilable-#{score} " +
//            "where available_aivilable+#{score}>=0 and sub_account_id=#{subAccountId}")
//    int updateTrueSubPurse(@Param("score")BigDecimal score,@Param("subAccountId")String subAccountId);


    /**
     * 冻结，减币的更新语句 score必须为正数
     * @param score
     * @param subAccountId 子账号id
     * @return
     */
    @Update("update purse_sub_account " +
            "set available_aivilable=available_aivilable-#{score},block_aivilable=block_aivilable+#{score} " +
            "where available_aivilable-#{score}>=0 and sub_account_id=#{subAccountId}")
    int updateTrueSubPurse(@Param("score")BigDecimal score,@Param("subAccountId")String subAccountId);

    /**
     * 不冻结，分享加积分的更新语句，限制每日5次
     * @param score
     * @param subAccountId 子账号id
     * @return
     */
    @Update("update wallet_sub_account " +
            "set total_aivilable=total_aivilable+#{score},available_aivilable=available_aivilable+#{score} " +
            "where available_aivilable+#{score}>=0 and sub_account_id=#{subAccountId} and (SELECT COUNT(*) FROM wallet_translog WHERE sub_account_id=#{subAccountId} AND trans_type=#{trans_type} AND create_time BETWEEN DATE_FORMAT(concat(CURDATE(),' 00:00:00'),'%Y-%m-%d %H:%i:%s') AND DATE_FORMAT(concat(CURDATE(),' 23:59:59'),'%Y-%m-%d %H:%i:%s'))<=#{num}")
    int updateFalseAddWalletWhereShareNum(@Param("score")BigDecimal score,@Param("subAccountId")String subAccountId,@Param("trans_type")String trans_type,@Param("num")BigDecimal num);


    /**
     * 查询用户当日分享快讯得次数
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(*) FROM wallet_translog a,`user` b,wallet_account c,wallet_sub_account d WHERE b.user_id=#{userId} AND a.trans_type='SHARE_NEWS' AND a.create_time BETWEEN DATE_FORMAT(concat(CURDATE(),' 00:00:00'),'%Y-%m-%d %H:%i:%s') AND DATE_FORMAT(concat(CURDATE(),' 23:59:59'),'%Y-%m-%d %H:%i:%s') and a.sub_account_id=d.sub_account_id and c.account_id=d.account_id and c.user_id=b.user_id")
    int selectShareNewsCount(@Param("userId")String userId);

    /**
     * 解冻用户积分到可用余额
     * @param score
     * @param subAccountId
     * @return
     */
    @Update("update wallet_sub_account " +
            "set block_aivilable=block_aivilable-#{score},available_aivilable=available_aivilable+#{score} " +
            "where block_aivilable-#{score}>=0 and sub_account_id=#{subAccountId}")
    int updateUnfreezeWallet(@Param("score") BigDecimal score, @Param("subAccountId") String subAccountId);
}
