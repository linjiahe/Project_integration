package com.blockchain.commune.customemapper.marketspider;

import com.blockchain.commune.custommodel.MarketTradeOnVo;
import com.blockchain.commune.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

/**
 * Created by wrb on 2018/10/19
 */
public interface MarketSpiderMapper {
    @Insert({
            "<script>",
            "replace into market_cap (id, symbol, ",
            "price, price_usd, ",
            "market_cap_display_cny, volume_24h, ",
            "logo, alias, rank, ",
            "percent_change_display, volume_24h_usd,batch,name)",
            "values",
            "<foreach item='value' index='key' collection='marketCapList' separator=','>",
            "(#{value.id},#{value.symbol},#{value.price},#{value.priceUsd},#{value.marketCapDisplayCny},#{value.volume24h},",
            "#{value.logo},#{value.alias},#{value.rank},#{value.percentChangeDisplay},#{value.volume24hUsd},#{value.batch},#{value.name})",
            "</foreach>",
            "</script>"
    })
    int replaceMarketCapList(@Param(value = "marketCapList") List<MarketCapSpider> marketCapList);

    @Insert({
            "<script>",
            "replace into market_exchange (id, market_id, ",
            "name, alias, website, ",
            "language, logo)",
            "values",
            "<foreach item='value' index='key' collection='marketExchangeList' separator=','>",
            "(#{value.id},#{value.marketId},#{value.name},#{value.alias},#{value.website},#{value.language},",
            "#{value.logo})",
            "</foreach>",
            "</script>"
    })
    int replaceIntoMarketExchangeList(@Param(value = "marketExchangeList") List<MarketExchange> marketExchangeList);

    @Insert({
            "<script>",
            "replace into market_trade_on (id, symbol, ",
            "market_id, market_name, ",
            "pair, price_display, ",
            "hr_price_display, percent_change_display, ",
            "market_alias, ",
            "logo,price,sort_id)",
            "values",
            "<foreach item='value' index='key' collection='marketTradeOnSpiderList' separator=','>",
            "(#{value.id},#{value.symbol},#{value.marketId},#{value.marketName},#{value.pair},#{value.priceDisplay},",
            "#{value.hrPriceDisplay},#{value.percentChangeDisplay},#{value.marketAlias},#{value.logo},#{value.price},#{value.sortId})",
            "</foreach>",
            "</script>"
    })
    int replaceMarketTradeOnList(@Param(value = "marketTradeOnSpiderList") List<MarketTradeOnSpider> marketTradeOnSpiderList);

    @Insert({
            "replace into market_basic_info (id, logo, ",
            "exchange, price, ",
            "percent, conver_coin, ",
            "conver_btc, title, ",
            "context, amount_24h, ",
            "volume_24h,  ",
            " tradedvalue, ",
            "global_market,  ",
            "kline_link, currency_on_market_id, ",
            "market_id, market_name, ",
            "symbol, anchor)",
            "values (#{id,jdbcType=VARCHAR}, #{logo,jdbcType=VARCHAR}, ",
            "#{exchange,jdbcType=VARCHAR}, #{price,jdbcType=VARCHAR}, ",
            "#{percent,jdbcType=VARCHAR}, #{converCoin,jdbcType=VARCHAR}, ",
            "#{converBtc,jdbcType=VARCHAR}, #{title,jdbcType=VARCHAR}, ",
            "#{context,jdbcType=VARCHAR}, #{amount24h,jdbcType=VARCHAR}, ",
            "#{volume24h,jdbcType=VARCHAR},  ",
            " #{tradedvalue,jdbcType=VARCHAR}, ",
            "#{globalMarket,jdbcType=VARCHAR}, ",
            "#{klineLink,jdbcType=VARCHAR}, #{currencyOnMarketId,jdbcType=VARCHAR}, ",
            "#{marketId,jdbcType=VARCHAR}, #{marketName,jdbcType=VARCHAR}, ",
            "#{symbol,jdbcType=VARCHAR}, #{anchor,jdbcType=VARCHAR})"
    })
    int replaceIntoMarketBasicInfo(MarketBasicInfoSpider record);

    @Select({
            "<script>",
            "SELECT a.logo,a.symbol,a.market_name,a.pair,a.price_display," ,
            "a.percent_change_display,a.market_alias,b.`logo` exchange_logo ",
            "FROM market_trade_on a ",
            "LEFT JOIN market_exchange b ON a.`market_id` = b.`market_id` " ,
            "WHERE  a.`symbol`=#{symbol} AND a.`create_time`= #{createTime}" ,
            " <when test='percentChange_desc==null'>" ,
            " ORDER BY a.sort_id asc LIMIT #{offset},#{limit} ",
            "</when>",
            " <when test='\"ASC\".equals(percentChange_desc)'>" ,
            " ORDER BY a.percent_change_display asc LIMIT #{offset},#{limit} ",
            "</when>",
            " <when test='\"DESC\".equals(percentChange_desc)'>" ,
            " ORDER BY a.percent_change_display desc LIMIT #{offset},#{limit} ",
            "</when>",
            "</script>"
    })
    List<MarketTradeOnVo> selectMarketTradeOnVo(@Param("symbol") String symbol, @Param("createTime") Date createTime,
                                             @Param("percentChange_desc") String percentChange_desc, @Param("offset") Integer offset,@Param("limit") Integer limit);

    @Select("select symbol,logo from market_cap where batch=#{batch} order by rank asc")
    List<MarketCap> selectALLSymbolList(@Param("batch") String batch);

    @Select({
            "<script>",
            "SELECT b.id, b.symbol," ,
            " b.price, b.price_usd, " ,
            " b.market_cap_display_cny, b.volume_24h," ,
            " b.logo, b.alias, b.rank," ,
            " b.percent_change_display, b.volume_24h_usd,b.batch,b.name FROM market_user_select a LEFT JOIN market_cap b ON a.`market_id`=b.`id` WHERE a.`user_id`=#{userId} ",
            " <when test='orderStr != null'>" ,
            " ORDER BY ${orderStr} ${desc}",
            "</when>",
            "</script>"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.VARCHAR, id=true),
            @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR),
            @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
            @Result(column="price_usd", property="priceUsd", jdbcType=JdbcType.DECIMAL),
            @Result(column="market_cap_display_cny", property="marketCapDisplayCny", jdbcType=JdbcType.DECIMAL),
            @Result(column="volume_24h", property="volume24h", jdbcType=JdbcType.DECIMAL),
            @Result(column="logo", property="logo", jdbcType=JdbcType.VARCHAR),
            @Result(column="alias", property="alias", jdbcType=JdbcType.VARCHAR),
            @Result(column="rank", property="rank", jdbcType=JdbcType.INTEGER),
            @Result(column="percent_change_display", property="percentChangeDisplay", jdbcType=JdbcType.DECIMAL),
            @Result(column="volume_24h_usd", property="volume24hUsd", jdbcType=JdbcType.DECIMAL),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="batch", property="batch", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    List<MarketCap> selectUserSelectMarketCap(@Param("userId")String userId,@Param("orderStr")String orderStr,@Param("desc")String desc);

    @Insert({
            "insert into market_hot_search (symbol, search_date, ",
            "logo, price, ",
            "percent_change_display,id)",
            "values (#{symbol,jdbcType=VARCHAR}, #{searchDate,jdbcType=DATE}, ",
            "#{logo,jdbcType=VARCHAR}, #{price,jdbcType=DECIMAL}, ",
            "#{percentChangeDisplay,jdbcType=DECIMAL},#{id}) ON DUPLICATE KEY UPDATE number=number+1;"
    })
    int replaceMarketHotSearchNumber(MarketHotSearch marketHotSearch);

    @Select({
            "SELECT a.id,a.symbol,a.logo,a.price,a.percent_change_display ",
            "FROM market_cap a LEFT JOIN market_hot_search b ON a.`symbol`=b.`symbol` AND b.`search_date`= #{searchDate,jdbcType=DATE} ",
            "WHERE b.`symbol`IS NULL AND a.`batch`=#{batch} ORDER BY a.`market_cap_display_cny` DESC LIMIT #{offset},#{limit} "
    })
    @Results({
            @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="logo", property="logo", jdbcType=JdbcType.VARCHAR),
            @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
            @Result(column="percent_change_display", property="percentChangeDisplay", jdbcType=JdbcType.DECIMAL),
            @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR)
    })
    List<MarketHotSearch> selectMarketHotSearchList(@Param("searchDate") Date searchDate, @Param("batch") String batch, @Param("offset") Integer offset,@Param("limit") Integer limit);

    @Select({
            "SELECT id, symbol," +
            "price, price_usd, " +
            "market_cap_display_cny, volume_24h," +
            " logo, alias, rank," +
            "percent_change_display, volume_24h_usd,batch,name FROM market_cap WHERE id IN (${idString})"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.VARCHAR, id=true),
            @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR),
            @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
            @Result(column="price_usd", property="priceUsd", jdbcType=JdbcType.DECIMAL),
            @Result(column="market_cap_display_cny", property="marketCapDisplayCny", jdbcType=JdbcType.DECIMAL),
            @Result(column="volume_24h", property="volume24h", jdbcType=JdbcType.DECIMAL),
            @Result(column="logo", property="logo", jdbcType=JdbcType.VARCHAR),
            @Result(column="alias", property="alias", jdbcType=JdbcType.VARCHAR),
            @Result(column="rank", property="rank", jdbcType=JdbcType.INTEGER),
            @Result(column="percent_change_display", property="percentChangeDisplay", jdbcType=JdbcType.DECIMAL),
            @Result(column="volume_24h_usd", property="volume24hUsd", jdbcType=JdbcType.DECIMAL),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="batch", property="batch", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    List<MarketCap> selectMarketCapByIdString(@Param("idString")String idString);
}
