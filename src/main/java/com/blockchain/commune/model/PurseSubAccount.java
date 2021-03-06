package com.blockchain.commune.model;

import java.math.BigDecimal;
import java.util.Date;

public class PurseSubAccount {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_sub_account.sub_account_id
     *
     * @mbg.generated
     */
    private String subAccountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_sub_account.account_id
     *
     * @mbg.generated
     */
    private String accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_sub_account.sub_account_type
     *
     * @mbg.generated
     */
    private String subAccountType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_sub_account.purse_address
     *
     * @mbg.generated
     */
    private String purseAddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_sub_account.purse_address_qrcode
     *
     * @mbg.generated
     */
    private String purseAddressQrcode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_sub_account.total_aivilable
     *
     * @mbg.generated
     */
    private BigDecimal totalAivilable;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_sub_account.available_aivilable
     *
     * @mbg.generated
     */
    private BigDecimal availableAivilable;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_sub_account.block_aivilable
     *
     * @mbg.generated
     */
    private BigDecimal blockAivilable;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_sub_account.sub_account_status
     *
     * @mbg.generated
     */
    private Byte subAccountStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_sub_account.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_sub_account.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_sub_account.sub_account_id
     *
     * @return the value of purse_sub_account.sub_account_id
     *
     * @mbg.generated
     */
    public String getSubAccountId() {
        return subAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_sub_account.sub_account_id
     *
     * @param subAccountId the value for purse_sub_account.sub_account_id
     *
     * @mbg.generated
     */
    public void setSubAccountId(String subAccountId) {
        this.subAccountId = subAccountId == null ? null : subAccountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_sub_account.account_id
     *
     * @return the value of purse_sub_account.account_id
     *
     * @mbg.generated
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_sub_account.account_id
     *
     * @param accountId the value for purse_sub_account.account_id
     *
     * @mbg.generated
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_sub_account.sub_account_type
     *
     * @return the value of purse_sub_account.sub_account_type
     *
     * @mbg.generated
     */
    public String getSubAccountType() {
        return subAccountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_sub_account.sub_account_type
     *
     * @param subAccountType the value for purse_sub_account.sub_account_type
     *
     * @mbg.generated
     */
    public void setSubAccountType(String subAccountType) {
        this.subAccountType = subAccountType == null ? null : subAccountType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_sub_account.purse_address
     *
     * @return the value of purse_sub_account.purse_address
     *
     * @mbg.generated
     */
    public String getPurseAddress() {
        return purseAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_sub_account.purse_address
     *
     * @param purseAddress the value for purse_sub_account.purse_address
     *
     * @mbg.generated
     */
    public void setPurseAddress(String purseAddress) {
        this.purseAddress = purseAddress == null ? null : purseAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_sub_account.purse_address_qrcode
     *
     * @return the value of purse_sub_account.purse_address_qrcode
     *
     * @mbg.generated
     */
    public String getPurseAddressQrcode() {
        return purseAddressQrcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_sub_account.purse_address_qrcode
     *
     * @param purseAddressQrcode the value for purse_sub_account.purse_address_qrcode
     *
     * @mbg.generated
     */
    public void setPurseAddressQrcode(String purseAddressQrcode) {
        this.purseAddressQrcode = purseAddressQrcode == null ? null : purseAddressQrcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_sub_account.total_aivilable
     *
     * @return the value of purse_sub_account.total_aivilable
     *
     * @mbg.generated
     */
    public BigDecimal getTotalAivilable() {
        return totalAivilable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_sub_account.total_aivilable
     *
     * @param totalAivilable the value for purse_sub_account.total_aivilable
     *
     * @mbg.generated
     */
    public void setTotalAivilable(BigDecimal totalAivilable) {
        this.totalAivilable = totalAivilable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_sub_account.available_aivilable
     *
     * @return the value of purse_sub_account.available_aivilable
     *
     * @mbg.generated
     */
    public BigDecimal getAvailableAivilable() {
        return availableAivilable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_sub_account.available_aivilable
     *
     * @param availableAivilable the value for purse_sub_account.available_aivilable
     *
     * @mbg.generated
     */
    public void setAvailableAivilable(BigDecimal availableAivilable) {
        this.availableAivilable = availableAivilable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_sub_account.block_aivilable
     *
     * @return the value of purse_sub_account.block_aivilable
     *
     * @mbg.generated
     */
    public BigDecimal getBlockAivilable() {
        return blockAivilable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_sub_account.block_aivilable
     *
     * @param blockAivilable the value for purse_sub_account.block_aivilable
     *
     * @mbg.generated
     */
    public void setBlockAivilable(BigDecimal blockAivilable) {
        this.blockAivilable = blockAivilable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_sub_account.sub_account_status
     *
     * @return the value of purse_sub_account.sub_account_status
     *
     * @mbg.generated
     */
    public Byte getSubAccountStatus() {
        return subAccountStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_sub_account.sub_account_status
     *
     * @param subAccountStatus the value for purse_sub_account.sub_account_status
     *
     * @mbg.generated
     */
    public void setSubAccountStatus(Byte subAccountStatus) {
        this.subAccountStatus = subAccountStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_sub_account.create_time
     *
     * @return the value of purse_sub_account.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_sub_account.create_time
     *
     * @param createTime the value for purse_sub_account.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_sub_account.update_time
     *
     * @return the value of purse_sub_account.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_sub_account.update_time
     *
     * @param updateTime the value for purse_sub_account.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}