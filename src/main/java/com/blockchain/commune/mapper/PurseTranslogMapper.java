package com.blockchain.commune.mapper;

import com.blockchain.commune.model.PurseTranslog;
import com.blockchain.commune.model.PurseTranslogCriteria;
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

public interface PurseTranslogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    @SelectProvider(type=PurseTranslogSqlProvider.class, method="countByExample")
    long countByExample(PurseTranslogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    @DeleteProvider(type=PurseTranslogSqlProvider.class, method="deleteByExample")
    int deleteByExample(PurseTranslogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    @Delete({
        "delete from purse_translog",
        "where trans_id = #{transId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String transId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    @Insert({
        "insert into purse_translog (trans_id, rela_trans_id, ",
        "trans_type, account_id, ",
        "sub_account_id, sub_account_type, ",
        "trans_title, trans_amount, ",
        "trans_director, party_userId, ",
        "trans_party, upAndDown, ",
        "source_balance, tageet_balance, ",
        "reverse_status, display_status, ",
        "is_block_trans, trans_detail_id, ",
        "block_end_time, remark, ",
        "order_status, create_time, ",
        "update_time)",
        "values (#{transId,jdbcType=VARCHAR}, #{relaTransId,jdbcType=VARCHAR}, ",
        "#{transType,jdbcType=VARCHAR}, #{accountId,jdbcType=VARCHAR}, ",
        "#{subAccountId,jdbcType=VARCHAR}, #{subAccountType,jdbcType=VARCHAR}, ",
        "#{transTitle,jdbcType=VARCHAR}, #{transAmount,jdbcType=DECIMAL}, ",
        "#{transDirector,jdbcType=VARCHAR}, #{partyUserid,jdbcType=VARCHAR}, ",
        "#{transParty,jdbcType=VARCHAR}, #{upanddown,jdbcType=TINYINT}, ",
        "#{sourceBalance,jdbcType=DECIMAL}, #{tageetBalance,jdbcType=DECIMAL}, ",
        "#{reverseStatus,jdbcType=TINYINT}, #{displayStatus,jdbcType=TINYINT}, ",
        "#{isBlockTrans,jdbcType=TINYINT}, #{transDetailId,jdbcType=VARCHAR}, ",
        "#{blockEndTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=VARCHAR}, ",
        "#{orderStatus,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(PurseTranslog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    @InsertProvider(type=PurseTranslogSqlProvider.class, method="insertSelective")
    int insertSelective(PurseTranslog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    @SelectProvider(type=PurseTranslogSqlProvider.class, method="selectByExample")
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
        @Result(column="party_userId", property="partyUserid", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_party", property="transParty", jdbcType=JdbcType.VARCHAR),
        @Result(column="upAndDown", property="upanddown", jdbcType=JdbcType.TINYINT),
        @Result(column="source_balance", property="sourceBalance", jdbcType=JdbcType.DECIMAL),
        @Result(column="tageet_balance", property="tageetBalance", jdbcType=JdbcType.DECIMAL),
        @Result(column="reverse_status", property="reverseStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="display_status", property="displayStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="is_block_trans", property="isBlockTrans", jdbcType=JdbcType.TINYINT),
        @Result(column="trans_detail_id", property="transDetailId", jdbcType=JdbcType.VARCHAR),
        @Result(column="block_end_time", property="blockEndTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="order_status", property="orderStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<PurseTranslog> selectByExample(PurseTranslogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "trans_id, rela_trans_id, trans_type, account_id, sub_account_id, sub_account_type, ",
        "trans_title, trans_amount, trans_director, party_userId, trans_party, upAndDown, ",
        "source_balance, tageet_balance, reverse_status, display_status, is_block_trans, ",
        "trans_detail_id, block_end_time, remark, order_status, create_time, update_time",
        "from purse_translog",
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
        @Result(column="party_userId", property="partyUserid", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_party", property="transParty", jdbcType=JdbcType.VARCHAR),
        @Result(column="upAndDown", property="upanddown", jdbcType=JdbcType.TINYINT),
        @Result(column="source_balance", property="sourceBalance", jdbcType=JdbcType.DECIMAL),
        @Result(column="tageet_balance", property="tageetBalance", jdbcType=JdbcType.DECIMAL),
        @Result(column="reverse_status", property="reverseStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="display_status", property="displayStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="is_block_trans", property="isBlockTrans", jdbcType=JdbcType.TINYINT),
        @Result(column="trans_detail_id", property="transDetailId", jdbcType=JdbcType.VARCHAR),
        @Result(column="block_end_time", property="blockEndTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="order_status", property="orderStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    PurseTranslog selectByPrimaryKey(String transId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PurseTranslogSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") PurseTranslog record, @Param("example") PurseTranslogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PurseTranslogSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") PurseTranslog record, @Param("example") PurseTranslogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PurseTranslogSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PurseTranslog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    @Update({
        "update purse_translog",
        "set rela_trans_id = #{relaTransId,jdbcType=VARCHAR},",
          "trans_type = #{transType,jdbcType=VARCHAR},",
          "account_id = #{accountId,jdbcType=VARCHAR},",
          "sub_account_id = #{subAccountId,jdbcType=VARCHAR},",
          "sub_account_type = #{subAccountType,jdbcType=VARCHAR},",
          "trans_title = #{transTitle,jdbcType=VARCHAR},",
          "trans_amount = #{transAmount,jdbcType=DECIMAL},",
          "trans_director = #{transDirector,jdbcType=VARCHAR},",
          "party_userId = #{partyUserid,jdbcType=VARCHAR},",
          "trans_party = #{transParty,jdbcType=VARCHAR},",
          "upAndDown = #{upanddown,jdbcType=TINYINT},",
          "source_balance = #{sourceBalance,jdbcType=DECIMAL},",
          "tageet_balance = #{tageetBalance,jdbcType=DECIMAL},",
          "reverse_status = #{reverseStatus,jdbcType=TINYINT},",
          "display_status = #{displayStatus,jdbcType=TINYINT},",
          "is_block_trans = #{isBlockTrans,jdbcType=TINYINT},",
          "trans_detail_id = #{transDetailId,jdbcType=VARCHAR},",
          "block_end_time = #{blockEndTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "order_status = #{orderStatus,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where trans_id = #{transId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(PurseTranslog record);
}