package com.blockchain.commune.custommodel;

import com.blockchain.commune.model.MarketTradeOnSpider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by wrb on 2018/10/23
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NUXTData {
    private NUXTCurrencyDetail currencydetail;
    private List<MarketTradeOnSpider> currencyexchangelist;

    public List<MarketTradeOnSpider> getCurrencyexchangelist() {
        return currencyexchangelist;
    }

    public void setCurrencyexchangelist(List<MarketTradeOnSpider> currencyexchangelist) {
        this.currencyexchangelist = currencyexchangelist;
    }

    public NUXTCurrencyDetail getCurrencydetail() {
        return currencydetail;
    }

    public void setCurrencydetail(NUXTCurrencyDetail currencydetail) {
        this.currencydetail = currencydetail;
    }
}
