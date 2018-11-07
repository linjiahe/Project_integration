package com.blockchain.commune.customemapper.marketspider;

import com.blockchain.commune.model.MarketExchangeInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wrb on 2018/9/15
 */
public interface MarketExchangeSpiderMapper {
    @Insert({
            "<script>",
            "insert into market_exchange_info (id, exchange_name, ",
            "exchange_code, currency_name, ",
            "currency_code, logo, ",
            "sort, last, high, ",
            "low, degree, vol, ",
            "domain, k_line)",
            "values",
            "<foreach item='value' index='key' collection='marketExchangeInfoList' separator=','>",
            "(#{value.id},#{value.exchangeName},#{value.exchangeCode},#{value.currencyName},#{value.currencyCode},#{value.logo},",
            "#{value.sort},#{value.last},#{value.high},#{value.low},#{value.degree},#{value.vol},#{value.domain},#{value.kLine})",
            "</foreach>",
            "</script>"
    })
    int insertMarketExchangeInfoList(@Param(value = "marketExchangeInfoList") List<MarketExchangeInfo> marketExchangeInfoList);
}
