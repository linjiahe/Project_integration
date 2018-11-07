package com.blockchain.commune.customemapper.wallet;

import com.blockchain.commune.custommodel.CheckedOrder;
import com.blockchain.commune.custommodel.PurseTranslogAdmin;
import com.blockchain.commune.custommodel.UserCoinDetail;
import com.blockchain.commune.custommodel.WithdrawOrder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface CustomPurseMapper {
    @Results({
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="account_id", property="accountId", jdbcType=JdbcType.VARCHAR),
            @Result(column="sub_account_type", property="coinName", jdbcType=JdbcType.VARCHAR),
            @Result(column="total_aivilable", property="totalBalance", jdbcType=JdbcType.VARCHAR),
            @Result(column="available_aivilable", property="availBalance", jdbcType=JdbcType.VARCHAR),
            @Result(column="block_aivilable", property="blockBalance", jdbcType=JdbcType.VARCHAR),
    })
    @SelectProvider(type=CustomPurseProvider.class,method="queryUserCoinDetailSql")
    public List<UserCoinDetail> queryUserCoinDetail(String filter,int start,int end);

    @SelectProvider(type=CustomPurseProvider.class,method="queryUserCoinDetailCountSql")
    public int queryCoinDetailCount(String filter);


    @Results({
            @Result(column="trans_id", property="transId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="account_id", property="accountId", jdbcType=JdbcType.VARCHAR),
            @Result(column="sub_account_type", property="coinName", jdbcType=JdbcType.VARCHAR),
            @Result(column="purse_address", property="fromAddress", jdbcType=JdbcType.VARCHAR),
            @Result(column="trans_party", property="toAddress", jdbcType=JdbcType.VARCHAR),
            @Result(column="login_name", property="loginName", jdbcType=JdbcType.DECIMAL),
            @Result(column="trans_amount", property="amount", jdbcType=JdbcType.DECIMAL),
            @Result(column="source_balance", property="sourceBalance", jdbcType=JdbcType.DECIMAL),
            @Result(column="tageet_balance", property="tageetBalance", jdbcType=JdbcType.DECIMAL),
            @Result(column="order_status", property="state", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.DATE),
    })
    @SelectProvider(type=CustomPurseProvider.class,method="queryAuditOrderSql")
    public List<WithdrawOrder> queryAuditOrder(String filter,int start,int end);

    @SelectProvider(type=CustomPurseProvider.class,method="queryAuditOrderCountSql")
    public int queryAuditOrderCount(String filter);

    @Select({
         "update purse_translog set trans_detail_id = #{txid},order_status = 'pending' where trans_id = #{transId}"
    })
    public void updateAudiOrder(@Param("txid")String txid, @Param("transId")String transId);


    @SelectProvider(type=CustomPurseProvider.class,method="queryPurseTransLogByUserId")
    public List<PurseTranslogAdmin> queryPurseTransLogByUserId(String filter, int start, int end);


    @SelectProvider(type=CustomPurseProvider.class,method="queryPurseTransLogByUserIdCount")
    public int queryPurseTransLogByUserIdCount(String filter);

}
