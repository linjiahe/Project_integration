package com.blockchain.commune.mapper;

import com.blockchain.commune.model.MarketHotSearch;
import com.blockchain.commune.model.MarketHotSearchCriteria;
import java.util.Date;
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

public interface MarketHotSearchMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_hot_search
     *
     * @mbg.generated
     */
    @SelectProvider(type=MarketHotSearchSqlProvider.class, method="countByExample")
    long countByExample(MarketHotSearchCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_hot_search
     *
     * @mbg.generated
     */
    @DeleteProvider(type=MarketHotSearchSqlProvider.class, method="deleteByExample")
    int deleteByExample(MarketHotSearchCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_hot_search
     *
     * @mbg.generated
     */
    @Delete({
        "delete from market_hot_search",
        "where symbol = #{symbol,jdbcType=VARCHAR}",
          "and search_date = #{searchDate,jdbcType=DATE}"
    })
    int deleteByPrimaryKey(@Param("symbol") String symbol, @Param("searchDate") Date searchDate);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_hot_search
     *
     * @mbg.generated
     */
    @Insert({
        "insert into market_hot_search (symbol, search_date, ",
        "number, logo, price, ",
        "percent_change_display, id)",
        "values (#{symbol,jdbcType=VARCHAR}, #{searchDate,jdbcType=DATE}, ",
        "#{number,jdbcType=INTEGER}, #{logo,jdbcType=VARCHAR}, #{price,jdbcType=DECIMAL}, ",
        "#{percentChangeDisplay,jdbcType=DECIMAL}, #{id,jdbcType=VARCHAR})"
    })
    int insert(MarketHotSearch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_hot_search
     *
     * @mbg.generated
     */
    @InsertProvider(type=MarketHotSearchSqlProvider.class, method="insertSelective")
    int insertSelective(MarketHotSearch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_hot_search
     *
     * @mbg.generated
     */
    @SelectProvider(type=MarketHotSearchSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="search_date", property="searchDate", jdbcType=JdbcType.DATE, id=true),
        @Result(column="number", property="number", jdbcType=JdbcType.INTEGER),
        @Result(column="logo", property="logo", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
        @Result(column="percent_change_display", property="percentChangeDisplay", jdbcType=JdbcType.DECIMAL),
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR)
    })
    List<MarketHotSearch> selectByExample(MarketHotSearchCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_hot_search
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "symbol, search_date, number, logo, price, percent_change_display, id",
        "from market_hot_search",
        "where symbol = #{symbol,jdbcType=VARCHAR}",
          "and search_date = #{searchDate,jdbcType=DATE}"
    })
    @Results({
        @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="search_date", property="searchDate", jdbcType=JdbcType.DATE, id=true),
        @Result(column="number", property="number", jdbcType=JdbcType.INTEGER),
        @Result(column="logo", property="logo", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
        @Result(column="percent_change_display", property="percentChangeDisplay", jdbcType=JdbcType.DECIMAL),
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR)
    })
    MarketHotSearch selectByPrimaryKey(@Param("symbol") String symbol, @Param("searchDate") Date searchDate);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_hot_search
     *
     * @mbg.generated
     */
    @UpdateProvider(type=MarketHotSearchSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") MarketHotSearch record, @Param("example") MarketHotSearchCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_hot_search
     *
     * @mbg.generated
     */
    @UpdateProvider(type=MarketHotSearchSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") MarketHotSearch record, @Param("example") MarketHotSearchCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_hot_search
     *
     * @mbg.generated
     */
    @UpdateProvider(type=MarketHotSearchSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MarketHotSearch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_hot_search
     *
     * @mbg.generated
     */
    @Update({
        "update market_hot_search",
        "set number = #{number,jdbcType=INTEGER},",
          "logo = #{logo,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DECIMAL},",
          "percent_change_display = #{percentChangeDisplay,jdbcType=DECIMAL},",
          "id = #{id,jdbcType=VARCHAR}",
        "where symbol = #{symbol,jdbcType=VARCHAR}",
          "and search_date = #{searchDate,jdbcType=DATE}"
    })
    int updateByPrimaryKey(MarketHotSearch record);
}