package com.blockchain.commune.custommodel;

import java.math.BigDecimal;

/**
 * Created by wrb on 2018/10/23
 */

public class MarketTradeOnVo {

    private String logo;

    private String symbol;

    private String market_name;

    private String pair;

    private String price_display;

    private BigDecimal percent_change_display;

    private String market_alias;

    private String exchange_logo;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMarket_name() {
        return market_name;
    }

    public void setMarket_name(String market_name) {
        this.market_name = market_name;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getPrice_display() {
        return price_display;
    }

    public void setPrice_display(String price_display) {
        this.price_display = price_display;
    }

    public BigDecimal getPercent_change_display() {
        return percent_change_display;
    }

    public void setPercent_change_display(BigDecimal percent_change_display) {
        this.percent_change_display = percent_change_display;
    }

    public String getMarket_alias() {
        return market_alias;
    }

    public void setMarket_alias(String market_alias) {
        this.market_alias = market_alias;
    }

    public String getExchange_logo() {
        return exchange_logo;
    }

    public void setExchange_logo(String exchange_logo) {
        this.exchange_logo = exchange_logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
