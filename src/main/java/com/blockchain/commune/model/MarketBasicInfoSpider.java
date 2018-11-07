package com.blockchain.commune.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketBasicInfoSpider {

    private String id;

    private String logo;

    private String exchange;

    private String price;

    private String percent;

    private String converCoin;

    private String converBtc;

    private String title;

    private String context;

    private String amount24h;

    private String volume24h;

    private String tradedvalue;

    private String globalMarket;

    private Date createtime;

    private String klineLink;

    @JsonProperty("currency_on_market_id")
    private String currencyOnMarketId;

    @JsonProperty("market_id")
    private String marketId;

    @JsonProperty("market_name")
    private String marketName;

    private String symbol;

    private String anchor;

    @Override
    public String toString() {
        return "MarketBasicInfoSpider{" +
                "id='" + id + '\'' +
                ", logo='" + logo + '\'' +
                ", exchange='" + exchange + '\'' +
                ", price='" + price + '\'' +
                ", percent='" + percent + '\'' +
                ", converCoin='" + converCoin + '\'' +
                ", converBtc='" + converBtc + '\'' +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", amount24h='" + amount24h + '\'' +
                ", volume24h='" + volume24h + '\'' +
                ", tradedvalue='" + tradedvalue + '\'' +
                ", globalMarket='" + globalMarket + '\'' +
                ", createtime=" + createtime +
                ", klineLink='" + klineLink + '\'' +
                ", currencyOnMarketId='" + currencyOnMarketId + '\'' +
                ", marketId='" + marketId + '\'' +
                ", marketName='" + marketName + '\'' +
                ", symbol='" + symbol + '\'' +
                ", anchor='" + anchor + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getConverCoin() {
        return converCoin;
    }

    public void setConverCoin(String converCoin) {
        this.converCoin = converCoin;
    }

    public String getConverBtc() {
        return converBtc;
    }

    public void setConverBtc(String converBtc) {
        this.converBtc = converBtc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getAmount24h() {
        return amount24h;
    }

    public void setAmount24h(String amount24h) {
        this.amount24h = amount24h;
    }

    public String getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(String volume24h) {
        this.volume24h = volume24h;
    }

    public String getTradedvalue() {
        return tradedvalue;
    }

    public void setTradedvalue(String tradedvalue) {
        this.tradedvalue = tradedvalue;
    }

    public String getGlobalMarket() {
        return globalMarket;
    }

    public void setGlobalMarket(String globalMarket) {
        this.globalMarket = globalMarket;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getKlineLink() {
        return klineLink;
    }

    public void setKlineLink(String klineLink) {
        this.klineLink = klineLink;
    }

    public String getCurrencyOnMarketId() {
        return currencyOnMarketId;
    }

    public void setCurrencyOnMarketId(String currencyOnMarketId) {
        this.currencyOnMarketId = currencyOnMarketId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }
}