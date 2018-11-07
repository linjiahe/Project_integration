package com.blockchain.commune.custommodel;

import com.blockchain.commune.model.MarketBasicInfoSpider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wrb on 2018/10/23
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NUXTCurrencyDetail {

    private String logo;

//    private MarketBasicInfoSpider kline_info;
//
//    public MarketBasicInfoSpider getKline_info() {
//        return kline_info;
//    }
//
//    public void setKline_info(MarketBasicInfoSpider kline_info) {
//        this.kline_info = kline_info;
//    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
