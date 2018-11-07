package com.blockchain.commune.customemapper.marketspider;

import com.blockchain.commune.model.MarketCurrencyInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wrb on 2018/9/15
 */
public interface MarketCurrencySpiderMapper {
    @Insert({
            "<script>",
            "insert into market_currency_info (id, coin_name, ",
            "coin_img, market_cap, ",
            "price, turnover, ",
            "volume, changepercent, ",
            "k_line, crawl_status,currency_code) ",
            "values",
            "<foreach item='value' index='key' collection='marketCurrencyInfoList' separator=','>",
            "(#{value.id},#{value.coinName},#{value.coinImg},#{value.marketCap},#{value.price},#{value.turnover},",
            "#{value.volume},#{value.changepercent},#{value.kLine},#{value.crawlStatus},#{value.currencyCode})",
            "</foreach>",
            "</script>"
    })
    int insertMarketCurrencyInfoList(@Param(value = "marketCurrencyInfoList") List<MarketCurrencyInfo> marketCurrencyInfoList);
}
