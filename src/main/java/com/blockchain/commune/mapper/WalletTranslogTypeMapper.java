package com.blockchain.commune.mapper;

import com.blockchain.commune.model.WalletTranslogType;
import com.blockchain.commune.model.WalletTranslogTypeCriteria;
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

public interface WalletTranslogTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog_type
     *
     * @mbg.generated
     */
    @SelectProvider(type=WalletTranslogTypeSqlProvider.class, method="countByExample")
    long countByExample(WalletTranslogTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog_type
     *
     * @mbg.generated
     */
    @DeleteProvider(type=WalletTranslogTypeSqlProvider.class, method="deleteByExample")
    int deleteByExample(WalletTranslogTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog_type
     *
     * @mbg.generated
     */
    @Delete({
        "delete from wallet_translog_type",
        "where trans_type = #{transType,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String transType);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog_type
     *
     * @mbg.generated
     */
    @Insert({
        "insert into wallet_translog_type (trans_type, trans_type_name, ",
        "trans_config, trans_director, ",
        "create_time)",
        "values (#{transType,jdbcType=VARCHAR}, #{transTypeName,jdbcType=VARCHAR}, ",
        "#{transConfig,jdbcType=VARCHAR}, #{transDirector,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(WalletTranslogType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog_type
     *
     * @mbg.generated
     */
    @InsertProvider(type=WalletTranslogTypeSqlProvider.class, method="insertSelective")
    int insertSelective(WalletTranslogType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog_type
     *
     * @mbg.generated
     */
    @SelectProvider(type=WalletTranslogTypeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="trans_type", property="transType", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="trans_type_name", property="transTypeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_config", property="transConfig", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_director", property="transDirector", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<WalletTranslogType> selectByExample(WalletTranslogTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog_type
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "trans_type, trans_type_name, trans_config, trans_director, create_time",
        "from wallet_translog_type",
        "where trans_type = #{transType,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="trans_type", property="transType", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="trans_type_name", property="transTypeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_config", property="transConfig", jdbcType=JdbcType.VARCHAR),
        @Result(column="trans_director", property="transDirector", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    WalletTranslogType selectByPrimaryKey(String transType);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog_type
     *
     * @mbg.generated
     */
    @UpdateProvider(type=WalletTranslogTypeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") WalletTranslogType record, @Param("example") WalletTranslogTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog_type
     *
     * @mbg.generated
     */
    @UpdateProvider(type=WalletTranslogTypeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") WalletTranslogType record, @Param("example") WalletTranslogTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog_type
     *
     * @mbg.generated
     */
    @UpdateProvider(type=WalletTranslogTypeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WalletTranslogType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_translog_type
     *
     * @mbg.generated
     */
    @Update({
        "update wallet_translog_type",
        "set trans_type_name = #{transTypeName,jdbcType=VARCHAR},",
          "trans_config = #{transConfig,jdbcType=VARCHAR},",
          "trans_director = #{transDirector,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where trans_type = #{transType,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(WalletTranslogType record);
}