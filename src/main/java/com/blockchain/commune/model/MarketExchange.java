package com.blockchain.commune.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketExchange {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_exchange.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_exchange.market_id
     *
     * @mbg.generated
     */
    @JsonProperty("market_id")
    private String marketId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_exchange.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_exchange.alias
     *
     * @mbg.generated
     */
    private String alias;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_exchange.website
     *
     * @mbg.generated
     */
    private String website;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_exchange.language
     *
     * @mbg.generated
     */
    private String language;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_exchange.logo
     *
     * @mbg.generated
     */
    private String logo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column market_exchange.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_exchange.id
     *
     * @return the value of market_exchange.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_exchange.id
     *
     * @param id the value for market_exchange.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_exchange.market_id
     *
     * @return the value of market_exchange.market_id
     *
     * @mbg.generated
     */
    public String getMarketId() {
        return marketId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_exchange.market_id
     *
     * @param marketId the value for market_exchange.market_id
     *
     * @mbg.generated
     */
    public void setMarketId(String marketId) {
        this.marketId = marketId == null ? null : marketId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_exchange.name
     *
     * @return the value of market_exchange.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_exchange.name
     *
     * @param name the value for market_exchange.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_exchange.alias
     *
     * @return the value of market_exchange.alias
     *
     * @mbg.generated
     */
    public String getAlias() {
        return alias;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_exchange.alias
     *
     * @param alias the value for market_exchange.alias
     *
     * @mbg.generated
     */
    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_exchange.website
     *
     * @return the value of market_exchange.website
     *
     * @mbg.generated
     */
    public String getWebsite() {
        return website;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_exchange.website
     *
     * @param website the value for market_exchange.website
     *
     * @mbg.generated
     */
    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_exchange.language
     *
     * @return the value of market_exchange.language
     *
     * @mbg.generated
     */
    public String getLanguage() {
        return language;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_exchange.language
     *
     * @param language the value for market_exchange.language
     *
     * @mbg.generated
     */
    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_exchange.logo
     *
     * @return the value of market_exchange.logo
     *
     * @mbg.generated
     */
    public String getLogo() {
        return logo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_exchange.logo
     *
     * @param logo the value for market_exchange.logo
     *
     * @mbg.generated
     */
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column market_exchange.create_time
     *
     * @return the value of market_exchange.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column market_exchange.create_time
     *
     * @param createTime the value for market_exchange.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}