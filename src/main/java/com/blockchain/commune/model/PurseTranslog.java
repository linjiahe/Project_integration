package com.blockchain.commune.model;

import java.math.BigDecimal;
import java.util.Date;

public class PurseTranslog {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.trans_id
     *
     * @mbg.generated
     */
    private String transId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.rela_trans_id
     *
     * @mbg.generated
     */
    private String relaTransId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.trans_type
     *
     * @mbg.generated
     */
    private String transType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.account_id
     *
     * @mbg.generated
     */
    private String accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.sub_account_id
     *
     * @mbg.generated
     */
    private String subAccountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.sub_account_type
     *
     * @mbg.generated
     */
    private String subAccountType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.trans_title
     *
     * @mbg.generated
     */
    private String transTitle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.trans_amount
     *
     * @mbg.generated
     */
    private BigDecimal transAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.trans_director
     *
     * @mbg.generated
     */
    private String transDirector;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.party_userId
     *
     * @mbg.generated
     */
    private String partyUserid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.trans_party
     *
     * @mbg.generated
     */
    private String transParty;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.upAndDown
     *
     * @mbg.generated
     */
    private Byte upanddown;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.source_balance
     *
     * @mbg.generated
     */
    private BigDecimal sourceBalance;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.tageet_balance
     *
     * @mbg.generated
     */
    private BigDecimal tageetBalance;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.reverse_status
     *
     * @mbg.generated
     */
    private Byte reverseStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.display_status
     *
     * @mbg.generated
     */
    private Byte displayStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.is_block_trans
     *
     * @mbg.generated
     */
    private Byte isBlockTrans;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.trans_detail_id
     *
     * @mbg.generated
     */
    private String transDetailId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.block_end_time
     *
     * @mbg.generated
     */
    private Date blockEndTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.order_status
     *
     * @mbg.generated
     */
    private String orderStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column purse_translog.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.trans_id
     *
     * @return the value of purse_translog.trans_id
     *
     * @mbg.generated
     */
    public String getTransId() {
        return transId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.trans_id
     *
     * @param transId the value for purse_translog.trans_id
     *
     * @mbg.generated
     */
    public void setTransId(String transId) {
        this.transId = transId == null ? null : transId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.rela_trans_id
     *
     * @return the value of purse_translog.rela_trans_id
     *
     * @mbg.generated
     */
    public String getRelaTransId() {
        return relaTransId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.rela_trans_id
     *
     * @param relaTransId the value for purse_translog.rela_trans_id
     *
     * @mbg.generated
     */
    public void setRelaTransId(String relaTransId) {
        this.relaTransId = relaTransId == null ? null : relaTransId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.trans_type
     *
     * @return the value of purse_translog.trans_type
     *
     * @mbg.generated
     */
    public String getTransType() {
        return transType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.trans_type
     *
     * @param transType the value for purse_translog.trans_type
     *
     * @mbg.generated
     */
    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.account_id
     *
     * @return the value of purse_translog.account_id
     *
     * @mbg.generated
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.account_id
     *
     * @param accountId the value for purse_translog.account_id
     *
     * @mbg.generated
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.sub_account_id
     *
     * @return the value of purse_translog.sub_account_id
     *
     * @mbg.generated
     */
    public String getSubAccountId() {
        return subAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.sub_account_id
     *
     * @param subAccountId the value for purse_translog.sub_account_id
     *
     * @mbg.generated
     */
    public void setSubAccountId(String subAccountId) {
        this.subAccountId = subAccountId == null ? null : subAccountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.sub_account_type
     *
     * @return the value of purse_translog.sub_account_type
     *
     * @mbg.generated
     */
    public String getSubAccountType() {
        return subAccountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.sub_account_type
     *
     * @param subAccountType the value for purse_translog.sub_account_type
     *
     * @mbg.generated
     */
    public void setSubAccountType(String subAccountType) {
        this.subAccountType = subAccountType == null ? null : subAccountType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.trans_title
     *
     * @return the value of purse_translog.trans_title
     *
     * @mbg.generated
     */
    public String getTransTitle() {
        return transTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.trans_title
     *
     * @param transTitle the value for purse_translog.trans_title
     *
     * @mbg.generated
     */
    public void setTransTitle(String transTitle) {
        this.transTitle = transTitle == null ? null : transTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.trans_amount
     *
     * @return the value of purse_translog.trans_amount
     *
     * @mbg.generated
     */
    public BigDecimal getTransAmount() {
        return transAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.trans_amount
     *
     * @param transAmount the value for purse_translog.trans_amount
     *
     * @mbg.generated
     */
    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.trans_director
     *
     * @return the value of purse_translog.trans_director
     *
     * @mbg.generated
     */
    public String getTransDirector() {
        return transDirector;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.trans_director
     *
     * @param transDirector the value for purse_translog.trans_director
     *
     * @mbg.generated
     */
    public void setTransDirector(String transDirector) {
        this.transDirector = transDirector == null ? null : transDirector.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.party_userId
     *
     * @return the value of purse_translog.party_userId
     *
     * @mbg.generated
     */
    public String getPartyUserid() {
        return partyUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.party_userId
     *
     * @param partyUserid the value for purse_translog.party_userId
     *
     * @mbg.generated
     */
    public void setPartyUserid(String partyUserid) {
        this.partyUserid = partyUserid == null ? null : partyUserid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.trans_party
     *
     * @return the value of purse_translog.trans_party
     *
     * @mbg.generated
     */
    public String getTransParty() {
        return transParty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.trans_party
     *
     * @param transParty the value for purse_translog.trans_party
     *
     * @mbg.generated
     */
    public void setTransParty(String transParty) {
        this.transParty = transParty == null ? null : transParty.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.upAndDown
     *
     * @return the value of purse_translog.upAndDown
     *
     * @mbg.generated
     */
    public Byte getUpanddown() {
        return upanddown;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.upAndDown
     *
     * @param upanddown the value for purse_translog.upAndDown
     *
     * @mbg.generated
     */
    public void setUpanddown(Byte upanddown) {
        this.upanddown = upanddown;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.source_balance
     *
     * @return the value of purse_translog.source_balance
     *
     * @mbg.generated
     */
    public BigDecimal getSourceBalance() {
        return sourceBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.source_balance
     *
     * @param sourceBalance the value for purse_translog.source_balance
     *
     * @mbg.generated
     */
    public void setSourceBalance(BigDecimal sourceBalance) {
        this.sourceBalance = sourceBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.tageet_balance
     *
     * @return the value of purse_translog.tageet_balance
     *
     * @mbg.generated
     */
    public BigDecimal getTageetBalance() {
        return tageetBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.tageet_balance
     *
     * @param tageetBalance the value for purse_translog.tageet_balance
     *
     * @mbg.generated
     */
    public void setTageetBalance(BigDecimal tageetBalance) {
        this.tageetBalance = tageetBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.reverse_status
     *
     * @return the value of purse_translog.reverse_status
     *
     * @mbg.generated
     */
    public Byte getReverseStatus() {
        return reverseStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.reverse_status
     *
     * @param reverseStatus the value for purse_translog.reverse_status
     *
     * @mbg.generated
     */
    public void setReverseStatus(Byte reverseStatus) {
        this.reverseStatus = reverseStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.display_status
     *
     * @return the value of purse_translog.display_status
     *
     * @mbg.generated
     */
    public Byte getDisplayStatus() {
        return displayStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.display_status
     *
     * @param displayStatus the value for purse_translog.display_status
     *
     * @mbg.generated
     */
    public void setDisplayStatus(Byte displayStatus) {
        this.displayStatus = displayStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.is_block_trans
     *
     * @return the value of purse_translog.is_block_trans
     *
     * @mbg.generated
     */
    public Byte getIsBlockTrans() {
        return isBlockTrans;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.is_block_trans
     *
     * @param isBlockTrans the value for purse_translog.is_block_trans
     *
     * @mbg.generated
     */
    public void setIsBlockTrans(Byte isBlockTrans) {
        this.isBlockTrans = isBlockTrans;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.trans_detail_id
     *
     * @return the value of purse_translog.trans_detail_id
     *
     * @mbg.generated
     */
    public String getTransDetailId() {
        return transDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.trans_detail_id
     *
     * @param transDetailId the value for purse_translog.trans_detail_id
     *
     * @mbg.generated
     */
    public void setTransDetailId(String transDetailId) {
        this.transDetailId = transDetailId == null ? null : transDetailId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.block_end_time
     *
     * @return the value of purse_translog.block_end_time
     *
     * @mbg.generated
     */
    public Date getBlockEndTime() {
        return blockEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.block_end_time
     *
     * @param blockEndTime the value for purse_translog.block_end_time
     *
     * @mbg.generated
     */
    public void setBlockEndTime(Date blockEndTime) {
        this.blockEndTime = blockEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.remark
     *
     * @return the value of purse_translog.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.remark
     *
     * @param remark the value for purse_translog.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.order_status
     *
     * @return the value of purse_translog.order_status
     *
     * @mbg.generated
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.order_status
     *
     * @param orderStatus the value for purse_translog.order_status
     *
     * @mbg.generated
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.create_time
     *
     * @return the value of purse_translog.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.create_time
     *
     * @param createTime the value for purse_translog.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column purse_translog.update_time
     *
     * @return the value of purse_translog.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column purse_translog.update_time
     *
     * @param updateTime the value for purse_translog.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}