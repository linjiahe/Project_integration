package com.blockchain.commune.mapper;

import com.blockchain.commune.model.WalletTranslog;
import com.blockchain.commune.model.WalletTranslogCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface WalletTranslogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog
     *
     * @mbg.generated
     */
    @SelectProvider(type=WalletTranslogSqlProvider.class, method="countByExample")
    long countByExample(WalletTranslogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog
     *
     * @mbg.generated
     */
    @DeleteProvider(type=WalletTranslogSqlProvider.class, method="deleteByExample")
    int deleteByExample(WalletTranslogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog
     *
     * @mbg.generated
     */
    @Delete({
        "delete from wallet_translog",
        "where trans_id = #{transId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String transId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog
     *
     * @mbg.generated
     */
    @Insert({
        "insert into wallet_translog (trans_id, rela_trans_id, ",
        "trans_type, account_id, ",
        "sub_account_id, sub_account_type, ",
        "trans_title, trans_amount, ",
        "trans_director, source_balance, ",
        "tageet_balance, reverse_status, ",
        "display_status, is_block_trans, ",
        "block_end_time, remark, ",
        "create_time, update_time)",
        "values (#{transId,jdbcType=VARCHAR}, #{relaTransId,jdbcType=VARCHAR}, ",
        "#{transType,jdbcType=VARCHAR}, #{accountId,jdbcType=VARCHAR}, ",
        "#{subAccountId,jdbcType=VARCHAR}, #{subAccountType,jdbcType=VARCHAR}, ",
        "#{transTitle,jdbcType=VARCHAR}, #{transAmount,jdbcType=DECIMAL}, ",
        "#{transDirector,jdbcType=VARCHAR}, #{sourceBalance,jdbcType=DECIMAL}, ",
        "#{tageetBalance,jdbcType=DECIMAL}, #{reverseStatus,jdbcType=TINYINT}, ",
        "#{displayStatus,jdbcType=TINYINT}, #{isBlockTrans,jdbcType=TINYINT}, ",
        "#{blockEndTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(WalletTranslog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog
     *
     * @mbg.generated
     */
    @InsertProvider(type=WalletTranslogSqlProvider.class, method="insertSelective")
    int insertSelective(WalletTranslog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog
     *
     * @mbg.generated
     */
    @SelectProvider(type=WalletTranslogSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="trans_id", property="transId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="rela_trans_id", property="relaTransId", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_type", property="transType", jdbcType=JdbcType.VARCHAR),
        @Result(column="account_id", property="accountId", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_account_id", property="subAccountId", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_account_type", property="subAccountType", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_title", property="transTitle", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_amount", property="transAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="trans_director", property="transDirector", jdbcType=JdbcType.VARCHAR),
        @Result(column="source_balance", property="sourceBalance", jdbcType=JdbcType.DECIMAL),
        @Result(column="tageet_balance", property="tageetBalance", jdbcType=JdbcType.DECIMAL),
        @Result(column="reverse_status", property="reverseStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="display_status", property="displayStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="is_block_trans", property="isBlockTrans", jdbcType=JdbcType.TINYINT),
        @Result(column="block_end_time", property="blockEndTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<WalletTranslog> selectByExample(WalletTranslogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "trans_id, rela_trans_id, trans_type, account_id, sub_account_id, sub_account_type, ",
        "trans_title, trans_amount, trans_director, source_balance, tageet_balance, reverse_status, ",
        "display_status, is_block_trans, block_end_time, remark, create_time, update_time",
        "from wallet_translog",
        "where trans_id = #{transId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="trans_id", property="transId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="rela_trans_id", property="relaTransId", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_type", property="transType", jdbcType=JdbcType.VARCHAR),
        @Result(column="account_id", property="accountId", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_account_id", property="subAccountId", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_account_type", property="subAccountType", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_title", property="transTitle", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_amount", property="transAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="trans_director", property="transDirector", jdbcType=JdbcType.VARCHAR),
        @Result(column="source_balance", property="sourceBalance", jdbcType=JdbcType.DECIMAL),
        @Result(column="tageet_balance", property="tageetBalance", jdbcType=JdbcType.DECIMAL),
        @Result(column="reverse_status", property="reverseStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="display_status", property="displayStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="is_block_trans", property="isBlockTrans", jdbcType=JdbcType.TINYINT),
        @Result(column="block_end_time", property="blockEndTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    WalletTranslog selectByPrimaryKey(String transId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog
     *
     * @mbg.generated
     */
    @UpdateProvider(type=WalletTranslogSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") WalletTranslog record, @Param("example") WalletTranslogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog
     *
     * @mbg.generated
     */
    @UpdateProvider(type=WalletTranslogSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") WalletTranslog record, @Param("example") WalletTranslogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog
     *
     * @mbg.generated
     */
    @UpdateProvider(type=WalletTranslogSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WalletTranslog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog
     *
     * @mbg.generated
     */
    @Update({
        "update wallet_translog",
        "set rela_trans_id = #{relaTransId,jdbcType=VARCHAR},",
          "trans_type = #{transType,jdbcType=VARCHAR},",
          "account_id = #{accountId,jdbcType=VARCHAR},",
          "sub_account_id = #{subAccountId,jdbcType=VARCHAR},",
          "sub_account_type = #{subAccountType,jdbcType=VARCHAR},",
          "trans_title = #{transTitle,jdbcType=VARCHAR},",
          "trans_amount = #{transAmount,jdbcType=DECIMAL},",
          "trans_director = #{transDirector,jdbcType=VARCHAR},",
          "source_balance = #{sourceBalance,jdbcType=DECIMAL},",
          "tageet_balance = #{tageetBalance,jdbcType=DECIMAL},",
          "reverse_status = #{reverseStatus,jdbcType=TINYINT},",
          "display_status = #{displayStatus,jdbcType=TINYINT},",
          "is_block_trans = #{isBlockTrans,jdbcType=TINYINT},",
          "block_end_time = #{blockEndTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where trans_id = #{transId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(WalletTranslog record);
}