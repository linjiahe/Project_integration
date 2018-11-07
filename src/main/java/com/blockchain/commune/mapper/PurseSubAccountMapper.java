package com.blockchain.commune.mapper;

import com.blockchain.commune.model.PurseSubAccount;
import com.blockchain.commune.model.PurseSubAccountCriteria;
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

public interface PurseSubAccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account
     *
     * @mbg.generated
     */
    @SelectProvider(type=PurseSubAccountSqlProvider.class, method="countByExample")
    long countByExample(PurseSubAccountCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account
     *
     * @mbg.generated
     */
    @DeleteProvider(type=PurseSubAccountSqlProvider.class, method="deleteByExample")
    int deleteByExample(PurseSubAccountCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account
     *
     * @mbg.generated
     */
    @Delete({
        "delete from purse_sub_account",
        "where sub_account_id = #{subAccountId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String subAccountId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account
     *
     * @mbg.generated
     */
    @Insert({
        "insert into purse_sub_account (sub_account_id, account_id, ",
        "sub_account_type, purse_address, ",
        "purse_address_qrcode, total_aivilable, ",
        "available_aivilable, block_aivilable, ",
        "sub_account_status, create_time, ",
        "update_time)",
        "values (#{subAccountId,jdbcType=VARCHAR}, #{accountId,jdbcType=VARCHAR}, ",
        "#{subAccountType,jdbcType=VARCHAR}, #{purseAddress,jdbcType=VARCHAR}, ",
        "#{purseAddressQrcode,jdbcType=VARCHAR}, #{totalAivilable,jdbcType=DECIMAL}, ",
        "#{availableAivilable,jdbcType=DECIMAL}, #{blockAivilable,jdbcType=DECIMAL}, ",
        "#{subAccountStatus,jdbcType=TINYINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(PurseSubAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account
     *
     * @mbg.generated
     */
    @InsertProvider(type=PurseSubAccountSqlProvider.class, method="insertSelective")
    int insertSelective(PurseSubAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account
     *
     * @mbg.generated
     */
    @SelectProvider(type=PurseSubAccountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="sub_account_id", property="subAccountId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="account_id", property="accountId", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_account_type", property="subAccountType", jdbcType=JdbcType.VARCHAR),
        @Result(column="purse_address", property="purseAddress", jdbcType=JdbcType.VARCHAR),
        @Result(column="purse_address_qrcode", property="purseAddressQrcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="total_aivilable", property="totalAivilable", jdbcType=JdbcType.DECIMAL),
        @Result(column="available_aivilable", property="availableAivilable", jdbcType=JdbcType.DECIMAL),
        @Result(column="block_aivilable", property="blockAivilable", jdbcType=JdbcType.DECIMAL),
        @Result(column="sub_account_status", property="subAccountStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<PurseSubAccount> selectByExample(PurseSubAccountCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "sub_account_id, account_id, sub_account_type, purse_address, purse_address_qrcode, ",
        "total_aivilable, available_aivilable, block_aivilable, sub_account_status, create_time, ",
        "update_time",
        "from purse_sub_account",
        "where sub_account_id = #{subAccountId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="sub_account_id", property="subAccountId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="account_id", property="accountId", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_account_type", property="subAccountType", jdbcType=JdbcType.VARCHAR),
        @Result(column="purse_address", property="purseAddress", jdbcType=JdbcType.VARCHAR),
        @Result(column="purse_address_qrcode", property="purseAddressQrcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="total_aivilable", property="totalAivilable", jdbcType=JdbcType.DECIMAL),
        @Result(column="available_aivilable", property="availableAivilable", jdbcType=JdbcType.DECIMAL),
        @Result(column="block_aivilable", property="blockAivilable", jdbcType=JdbcType.DECIMAL),
        @Result(column="sub_account_status", property="subAccountStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    PurseSubAccount selectByPrimaryKey(String subAccountId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PurseSubAccountSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") PurseSubAccount record, @Param("example") PurseSubAccountCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PurseSubAccountSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") PurseSubAccount record, @Param("example") PurseSubAccountCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PurseSubAccountSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PurseSubAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account
     *
     * @mbg.generated
     */
    @Update({
        "update purse_sub_account",
        "set account_id = #{accountId,jdbcType=VARCHAR},",
          "sub_account_type = #{subAccountType,jdbcType=VARCHAR},",
          "purse_address = #{purseAddress,jdbcType=VARCHAR},",
          "purse_address_qrcode = #{purseAddressQrcode,jdbcType=VARCHAR},",
          "total_aivilable = #{totalAivilable,jdbcType=DECIMAL},",
          "available_aivilable = #{availableAivilable,jdbcType=DECIMAL},",
          "block_aivilable = #{blockAivilable,jdbcType=DECIMAL},",
          "sub_account_status = #{subAccountStatus,jdbcType=TINYINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where sub_account_id = #{subAccountId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(PurseSubAccount record);
}