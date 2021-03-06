package com.blockchain.commune.model;

import java.util.Date;

public class CoinDetail {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coin_detail.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coin_detail.coin_name
     *
     * @mbg.generated
     */
    private String coinName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coin_detail.currency_code
     *
     * @mbg.generated
     */
    private String currencyCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coin_detail.coin_introduction
     *
     * @mbg.generated
     */
    private String coinIntroduction;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coin_detail.base_info_list
     *
     * @mbg.generated
     */
    private String baseInfoList;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coin_detail.batch
     *
     * @mbg.generated
     */
    private Integer batch;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coin_detail.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coin_detail.id
     *
     * @return the value of coin_detail.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coin_detail.id
     *
     * @param id the value for coin_detail.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coin_detail.coin_name
     *
     * @return the value of coin_detail.coin_name
     *
     * @mbg.generated
     */
    public String getCoinName() {
        return coinName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coin_detail.coin_name
     *
     * @param coinName the value for coin_detail.coin_name
     *
     * @mbg.generated
     */
    public void setCoinName(String coinName) {
        this.coinName = coinName == null ? null : coinName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coin_detail.currency_code
     *
     * @return the value of coin_detail.currency_code
     *
     * @mbg.generated
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coin_detail.currency_code
     *
     * @param currencyCode the value for coin_detail.currency_code
     *
     * @mbg.generated
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coin_detail.coin_introduction
     *
     * @return the value of coin_detail.coin_introduction
     *
     * @mbg.generated
     */
    public String getCoinIntroduction() {
        return coinIntroduction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coin_detail.coin_introduction
     *
     * @param coinIntroduction the value for coin_detail.coin_introduction
     *
     * @mbg.generated
     */
    public void setCoinIntroduction(String coinIntroduction) {
        this.coinIntroduction = coinIntroduction == null ? null : coinIntroduction.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coin_detail.base_info_list
     *
     * @return the value of coin_detail.base_info_list
     *
     * @mbg.generated
     */
    public String getBaseInfoList() {
        return baseInfoList;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coin_detail.base_info_list
     *
     * @param baseInfoList the value for coin_detail.base_info_list
     *
     * @mbg.generated
     */
    public void setBaseInfoList(String baseInfoList) {
        this.baseInfoList = baseInfoList == null ? null : baseInfoList.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coin_detail.batch
     *
     * @return the value of coin_detail.batch
     *
     * @mbg.generated
     */
    public Integer getBatch() {
        return batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coin_detail.batch
     *
     * @param batch the value for coin_detail.batch
     *
     * @mbg.generated
     */
    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coin_detail.create_time
     *
     * @return the value of coin_detail.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coin_detail.create_time
     *
     * @param createTime the value for coin_detail.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}