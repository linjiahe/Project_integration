package com.blockchain.commune.custommodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by wrb on 2018/10/23
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketKLineData {
    List<MarketKLine> kline;

    public List<MarketKLine> getKline() {
        return kline;
    }

    public void setKline(List<MarketKLine> kline) {
        this.kline = kline;
    }
}
