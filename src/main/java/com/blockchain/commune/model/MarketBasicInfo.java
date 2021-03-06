package com.blockchain.commune.model;

import java.util.Date;

public class MarketBasicInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.logo
     *
     * @mbg.generated
     */
    private String logo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.exchange
     *
     * @mbg.generated
     */
    private String exchange;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.price
     *
     * @mbg.generated
     */
    private String price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.percent
     *
     * @mbg.generated
     */
    private String percent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.conver_coin
     *
     * @mbg.generated
     */
    private String converCoin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.conver_btc
     *
     * @mbg.generated
     */
    private String converBtc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.title
     *
     * @mbg.generated
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.amount_24h
     *
     * @mbg.generated
     */
    private String amount24h;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.volume_24h
     *
     * @mbg.generated
     */
    private String volume24h;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.tradedvalue
     *
     * @mbg.generated
     */
    private String tradedvalue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.global_market
     *
     * @mbg.generated
     */
    private String globalMarket;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.createtime
     *
     * @mbg.generated
     */
    private Date createtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.kline_link
     *
     * @mbg.generated
     */
    private String klineLink;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.currency_on_market_id
     *
     * @mbg.generated
     */
    private String currencyOnMarketId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.market_id
     *
     * @mbg.generated
     */
    private String marketId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.market_name
     *
     * @mbg.generated
     */
    private String marketName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.symbol
     *
     * @mbg.generated
     */
    private String symbol;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.anchor
     *
     * @mbg.generated
     */
    private String anchor;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.select_status
     *
     * @mbg.generated
     */
    private Byte selectStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_basic_info.context
     *
     * @mbg.generated
     */
    private String context;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.id
     *
     * @return the value of market_basic_info.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.id
     *
     * @param id the value for market_basic_info.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.logo
     *
     * @return the value of market_basic_info.logo
     *
     * @mbg.generated
     */
    public String getLogo() {
        return logo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.logo
     *
     * @param logo the value for market_basic_info.logo
     *
     * @mbg.generated
     */
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.exchange
     *
     * @return the value of market_basic_info.exchange
     *
     * @mbg.generated
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.exchange
     *
     * @param exchange the value for market_basic_info.exchange
     *
     * @mbg.generated
     */
    public void setExchange(String exchange) {
        this.exchange = exchange == null ? null : exchange.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.price
     *
     * @return the value of market_basic_info.price
     *
     * @mbg.generated
     */
    public String getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.price
     *
     * @param price the value for market_basic_info.price
     *
     * @mbg.generated
     */
    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.percent
     *
     * @return the value of market_basic_info.percent
     *
     * @mbg.generated
     */
    public String getPercent() {
        return percent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.percent
     *
     * @param percent the value for market_basic_info.percent
     *
     * @mbg.generated
     */
    public void setPercent(String percent) {
        this.percent = percent == null ? null : percent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.conver_coin
     *
     * @return the value of market_basic_info.conver_coin
     *
     * @mbg.generated
     */
    public String getConverCoin() {
        return converCoin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.conver_coin
     *
     * @param converCoin the value for market_basic_info.conver_coin
     *
     * @mbg.generated
     */
    public void setConverCoin(String converCoin) {
        this.converCoin = converCoin == null ? null : converCoin.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.conver_btc
     *
     * @return the value of market_basic_info.conver_btc
     *
     * @mbg.generated
     */
    public String getConverBtc() {
        return converBtc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.conver_btc
     *
     * @param converBtc the value for market_basic_info.conver_btc
     *
     * @mbg.generated
     */
    public void setConverBtc(String converBtc) {
        this.converBtc = converBtc == null ? null : converBtc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.title
     *
     * @return the value of market_basic_info.title
     *
     * @mbg.generated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.title
     *
     * @param title the value for market_basic_info.title
     *
     * @mbg.generated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.amount_24h
     *
     * @return the value of market_basic_info.amount_24h
     *
     * @mbg.generated
     */
    public String getAmount24h() {
        return amount24h;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.amount_24h
     *
     * @param amount24h the value for market_basic_info.amount_24h
     *
     * @mbg.generated
     */
    public void setAmount24h(String amount24h) {
        this.amount24h = amount24h == null ? null : amount24h.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.volume_24h
     *
     * @return the value of market_basic_info.volume_24h
     *
     * @mbg.generated
     */
    public String getVolume24h() {
        return volume24h;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.volume_24h
     *
     * @param volume24h the value for market_basic_info.volume_24h
     *
     * @mbg.generated
     */
    public void setVolume24h(String volume24h) {
        this.volume24h = volume24h == null ? null : volume24h.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.tradedvalue
     *
     * @return the value of market_basic_info.tradedvalue
     *
     * @mbg.generated
     */
    public String getTradedvalue() {
        return tradedvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.tradedvalue
     *
     * @param tradedvalue the value for market_basic_info.tradedvalue
     *
     * @mbg.generated
     */
    public void setTradedvalue(String tradedvalue) {
        this.tradedvalue = tradedvalue == null ? null : tradedvalue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.global_market
     *
     * @return the value of market_basic_info.global_market
     *
     * @mbg.generated
     */
    public String getGlobalMarket() {
        return globalMarket;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.global_market
     *
     * @param globalMarket the value for market_basic_info.global_market
     *
     * @mbg.generated
     */
    public void setGlobalMarket(String globalMarket) {
        this.globalMarket = globalMarket == null ? null : globalMarket.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.createtime
     *
     * @return the value of market_basic_info.createtime
     *
     * @mbg.generated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.createtime
     *
     * @param createtime the value for market_basic_info.createtime
     *
     * @mbg.generated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.kline_link
     *
     * @return the value of market_basic_info.kline_link
     *
     * @mbg.generated
     */
    public String getKlineLink() {
        return klineLink;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.kline_link
     *
     * @param klineLink the value for market_basic_info.kline_link
     *
     * @mbg.generated
     */
    public void setKlineLink(String klineLink) {
        this.klineLink = klineLink == null ? null : klineLink.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.currency_on_market_id
     *
     * @return the value of market_basic_info.currency_on_market_id
     *
     * @mbg.generated
     */
    public String getCurrencyOnMarketId() {
        return currencyOnMarketId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.currency_on_market_id
     *
     * @param currencyOnMarketId the value for market_basic_info.currency_on_market_id
     *
     * @mbg.generated
     */
    public void setCurrencyOnMarketId(String currencyOnMarketId) {
        this.currencyOnMarketId = currencyOnMarketId == null ? null : currencyOnMarketId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.market_id
     *
     * @return the value of market_basic_info.market_id
     *
     * @mbg.generated
     */
    public String getMarketId() {
        return marketId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.market_id
     *
     * @param marketId the value for market_basic_info.market_id
     *
     * @mbg.generated
     */
    public void setMarketId(String marketId) {
        this.marketId = marketId == null ? null : marketId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.market_name
     *
     * @return the value of market_basic_info.market_name
     *
     * @mbg.generated
     */
    public String getMarketName() {
        return marketName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.market_name
     *
     * @param marketName the value for market_basic_info.market_name
     *
     * @mbg.generated
     */
    public void setMarketName(String marketName) {
        this.marketName = marketName == null ? null : marketName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.symbol
     *
     * @return the value of market_basic_info.symbol
     *
     * @mbg.generated
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.symbol
     *
     * @param symbol the value for market_basic_info.symbol
     *
     * @mbg.generated
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.anchor
     *
     * @return the value of market_basic_info.anchor
     *
     * @mbg.generated
     */
    public String getAnchor() {
        return anchor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.anchor
     *
     * @param anchor the value for market_basic_info.anchor
     *
     * @mbg.generated
     */
    public void setAnchor(String anchor) {
        this.anchor = anchor == null ? null : anchor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.select_status
     *
     * @return the value of market_basic_info.select_status
     *
     * @mbg.generated
     */
    public Byte getSelectStatus() {
        return selectStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.select_status
     *
     * @param selectStatus the value for market_basic_info.select_status
     *
     * @mbg.generated
     */
    public void setSelectStatus(Byte selectStatus) {
        this.selectStatus = selectStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_basic_info.context
     *
     * @return the value of market_basic_info.context
     *
     * @mbg.generated
     */
    public String getContext() {
        return context;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_basic_info.context
     *
     * @param context the value for market_basic_info.context
     *
     * @mbg.generated
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }
}