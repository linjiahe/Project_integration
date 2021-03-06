package com.blockchain.commune.mapper;

import com.blockchain.commune.model.MarketExchangeInfo;
import com.blockchain.commune.model.MarketExchangeInfoCriteria;
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

public interface MarketExchangeInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_exchange_info
     *
     * @mbg.generated
     */
    @SelectProvider(type=MarketExchangeInfoSqlProvider.class, method="countByExample")
    long countByExample(MarketExchangeInfoCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_exchange_info
     *
     * @mbg.generated
     */
    @DeleteProvider(type=MarketExchangeInfoSqlProvider.class, method="deleteByExample")
    int deleteByExample(MarketExchangeInfoCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_exchange_info
     *
     * @mbg.generated
     */
    @Delete({
        "delete from market_exchange_info",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_exchange_info
     *
     * @mbg.generated
     */
    @Insert({
        "insert into market_exchange_info (id, exchange_name, ",
        "exchange_code, currency_name, ",
        "currency_code, logo, ",
        "sort, last, high, ",
        "low, degree, vol, ",
        "domain, k_line, create_time, ",
        "update_time)",
        "values (#{id,jdbcType=VARCHAR}, #{exchangeName,jdbcType=VARCHAR}, ",
        "#{exchangeCode,jdbcType=VARCHAR}, #{currencyName,jdbcType=VARCHAR}, ",
        "#{currencyCode,jdbcType=VARCHAR}, #{logo,jdbcType=VARCHAR}, ",
        "#{sort,jdbcType=INTEGER}, #{last,jdbcType=VARCHAR}, #{high,jdbcType=VARCHAR}, ",
        "#{low,jdbcType=VARCHAR}, #{degree,jdbcType=VARCHAR}, #{vol,jdbcType=VARCHAR}, ",
        "#{domain,jdbcType=VARCHAR}, #{kLine,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(MarketExchangeInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_exchange_info
     *
     * @mbg.generated
     */
    @InsertProvider(type=MarketExchangeInfoSqlProvider.class, method="insertSelective")
    int insertSelective(MarketExchangeInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_exchange_info
     *
     * @mbg.generated
     */
    @SelectProvider(type=MarketExchangeInfoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="exchange_name", property="exchangeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="exchange_code", property="exchangeCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="currency_name", property="currencyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="currency_code", property="currencyCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="logo", property="logo", jdbcType=JdbcType.VARCHAR),
        @Result(column="sort", property="sort", jdbcType=JdbcType.INTEGER),
        @Result(column="last", property="last", jdbcType=JdbcType.VARCHAR),
        @Result(column="high", property="high", jdbcType=JdbcType.VARCHAR),
        @Result(column="low", property="low", jdbcType=JdbcType.VARCHAR),
        @Result(column="degree", property="degree", jdbcType=JdbcType.VARCHAR),
        @Result(column="vol", property="vol", jdbcType=JdbcType.VARCHAR),
        @Result(column="domain", property="domain", jdbcType=JdbcType.VARCHAR),
        @Result(column="k_line", property="kLine", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<MarketExchangeInfo> selectByExample(MarketExchangeInfoCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_exchange_info
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, exchange_name, exchange_code, currency_name, currency_code, logo, sort, ",
        "last, high, low, degree, vol, domain, k_line, create_time, update_time",
        "from market_exchange_info",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="exchange_name", property="exchangeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="exchange_code", property="exchangeCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="currency_name", property="currencyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="currency_code", property="currencyCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="logo", property="logo", jdbcType=JdbcType.VARCHAR),
        @Result(column="sort", property="sort", jdbcType=JdbcType.INTEGER),
        @Result(column="last", property="last", jdbcType=JdbcType.VARCHAR),
        @Result(column="high", property="high", jdbcType=JdbcType.VARCHAR),
        @Result(column="low", property="low", jdbcType=JdbcType.VARCHAR),
        @Result(column="degree", property="degree", jdbcType=JdbcType.VARCHAR),
        @Result(column="vol", property="vol", jdbcType=JdbcType.VARCHAR),
        @Result(column="domain", property="domain", jdbcType=JdbcType.VARCHAR),
        @Result(column="k_line", property="kLine", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    MarketExchangeInfo selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_exchange_info
     *
     * @mbg.generated
     */
    @UpdateProvider(type=MarketExchangeInfoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") MarketExchangeInfo record, @Param("example") MarketExchangeInfoCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_exchange_info
     *
     * @mbg.generated
     */
    @UpdateProvider(type=MarketExchangeInfoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") MarketExchangeInfo record, @Param("example") MarketExchangeInfoCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_exchange_info
     *
     * @mbg.generated
     */
    @UpdateProvider(type=MarketExchangeInfoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MarketExchangeInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_exchange_info
     *
     * @mbg.generated
     */
    @Update({
        "update market_exchange_info",
        "set exchange_name = #{exchangeName,jdbcType=VARCHAR},",
          "exchange_code = #{exchangeCode,jdbcType=VARCHAR},",
          "currency_name = #{currencyName,jdbcType=VARCHAR},",
          "currency_code = #{currencyCode,jdbcType=VARCHAR},",
          "logo = #{logo,jdbcType=VARCHAR},",
          "sort = #{sort,jdbcType=INTEGER},",
          "last = #{last,jdbcType=VARCHAR},",
          "high = #{high,jdbcType=VARCHAR},",
          "low = #{low,jdbcType=VARCHAR},",
          "degree = #{degree,jdbcType=VARCHAR},",
          "vol = #{vol,jdbcType=VARCHAR},",
          "domain = #{domain,jdbcType=VARCHAR},",
          "k_line = #{kLine,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(MarketExchangeInfo record);

}