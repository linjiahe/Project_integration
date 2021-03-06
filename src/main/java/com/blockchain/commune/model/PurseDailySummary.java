package com.blockchain.commune.model;

import java.math.BigDecimal;
import java.util.Date;

public class PurseDailySummary {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_daily_summary.daily_summary_id
     *
     * @mbg.generated
     */
    private String dailySummaryId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_daily_summary.account_id
     *
     * @mbg.generated
     */
    private String accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_daily_summary.daily_name
     *
     * @mbg.generated
     */
    private String dailyName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_daily_summary.trans_amount
     *
     * @mbg.generated
     */
    private BigDecimal transAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_daily_summary.trans_in_amount
     *
     * @mbg.generated
     */
    private BigDecimal transInAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_daily_summary.trans_out_amount
     *
     * @mbg.generated
     */
    private BigDecimal transOutAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_daily_summary.source_balance
     *
     * @mbg.generated
     */
    private BigDecimal sourceBalance;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_daily_summary.tageet_balance
     *
     * @mbg.generated
     */
    private BigDecimal tageetBalance;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_daily_summary.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_daily_summary.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_daily_summary.daily_summary_id
     *
     * @return the value of purse_daily_summary.daily_summary_id
     *
     * @mbg.generated
     */
    public String getDailySummaryId() {
        return dailySummaryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_daily_summary.daily_summary_id
     *
     * @param dailySummaryId the value for purse_daily_summary.daily_summary_id
     *
     * @mbg.generated
     */
    public void setDailySummaryId(String dailySummaryId) {
        this.dailySummaryId = dailySummaryId == null ? null : dailySummaryId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_daily_summary.account_id
     *
     * @return the value of purse_daily_summary.account_id
     *
     * @mbg.generated
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_daily_summary.account_id
     *
     * @param accountId the value for purse_daily_summary.account_id
     *
     * @mbg.generated
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_daily_summary.daily_name
     *
     * @return the value of purse_daily_summary.daily_name
     *
     * @mbg.generated
     */
    public String getDailyName() {
        return dailyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_daily_summary.daily_name
     *
     * @param dailyName the value for purse_daily_summary.daily_name
     *
     * @mbg.generated
     */
    public void setDailyName(String dailyName) {
        this.dailyName = dailyName == null ? null : dailyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_daily_summary.trans_amount
     *
     * @return the value of purse_daily_summary.trans_amount
     *
     * @mbg.generated
     */
    public BigDecimal getTransAmount() {
        return transAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_daily_summary.trans_amount
     *
     * @param transAmount the value for purse_daily_summary.trans_amount
     *
     * @mbg.generated
     */
    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_daily_summary.trans_in_amount
     *
     * @return the value of purse_daily_summary.trans_in_amount
     *
     * @mbg.generated
     */
    public BigDecimal getTransInAmount() {
        return transInAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_daily_summary.trans_in_amount
     *
     * @param transInAmount the value for purse_daily_summary.trans_in_amount
     *
     * @mbg.generated
     */
    public void setTransInAmount(BigDecimal transInAmount) {
        this.transInAmount = transInAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_daily_summary.trans_out_amount
     *
     * @return the value of purse_daily_summary.trans_out_amount
     *
     * @mbg.generated
     */
    public BigDecimal getTransOutAmount() {
        return transOutAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_daily_summary.trans_out_amount
     *
     * @param transOutAmount the value for purse_daily_summary.trans_out_amount
     *
     * @mbg.generated
     */
    public void setTransOutAmount(BigDecimal transOutAmount) {
        this.transOutAmount = transOutAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_daily_summary.source_balance
     *
     * @return the value of purse_daily_summary.source_balance
     *
     * @mbg.generated
     */
    public BigDecimal getSourceBalance() {
        return sourceBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_daily_summary.source_balance
     *
     * @param sourceBalance the value for purse_daily_summary.source_balance
     *
     * @mbg.generated
     */
    public void setSourceBalance(BigDecimal sourceBalance) {
        this.sourceBalance = sourceBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_daily_summary.tageet_balance
     *
     * @return the value of purse_daily_summary.tageet_balance
     *
     * @mbg.generated
     */
    public BigDecimal getTageetBalance() {
        return tageetBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_daily_summary.tageet_balance
     *
     * @param tageetBalance the value for purse_daily_summary.tageet_balance
     *
     * @mbg.generated
     */
    public void setTageetBalance(BigDecimal tageetBalance) {
        this.tageetBalance = tageetBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_daily_summary.create_time
     *
     * @return the value of purse_daily_summary.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_daily_summary.create_time
     *
     * @param createTime the value for purse_daily_summary.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_daily_summary.update_time
     *
     * @return the value of purse_daily_summary.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_daily_summary.update_time
     *
     * @param updateTime the value for purse_daily_summary.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}