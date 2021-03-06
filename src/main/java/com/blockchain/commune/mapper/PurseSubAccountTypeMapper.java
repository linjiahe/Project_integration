package com.blockchain.commune.mapper;

import com.blockchain.commune.model.PurseSubAccountType;
import com.blockchain.commune.model.PurseSubAccountTypeCriteria;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface PurseSubAccountTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account_type
     *
     * @mbg.generated
     */
    @SelectProvider(type=PurseSubAccountTypeSqlProvider.class, method="countByExample")
    long countByExample(PurseSubAccountTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account_type
     *
     * @mbg.generated
     */
    @DeleteProvider(type=PurseSubAccountTypeSqlProvider.class, method="deleteByExample")
    int deleteByExample(PurseSubAccountTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account_type
     *
     * @mbg.generated
     */
    @Insert({
        "insert into purse_sub_account_type (sub_account_type, sub_account_type_desc, ",
        "category, create_time)",
        "values (#{subAccountType,jdbcType=VARCHAR}, #{subAccountTypeDesc,jdbcType=VARCHAR}, ",
        "#{category,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(PurseSubAccountType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account_type
     *
     * @mbg.generated
     */
    @InsertProvider(type=PurseSubAccountTypeSqlProvider.class, method="insertSelective")
    int insertSelective(PurseSubAccountType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account_type
     *
     * @mbg.generated
     */
    @SelectProvider(type=PurseSubAccountTypeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="sub_account_type", property="subAccountType", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_account_type_desc", property="subAccountTypeDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="category", property="category", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<PurseSubAccountType> selectByExample(PurseSubAccountTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account_type
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PurseSubAccountTypeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") PurseSubAccountType record, @Param("example") PurseSubAccountTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_sub_account_type
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PurseSubAccountTypeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") PurseSubAccountType record, @Param("example") PurseSubAccountTypeCriteria example);
}