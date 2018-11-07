package com.blockchain.commune.custommodel;

import java.math.BigDecimal;
import java.util.Date;

public class PurseTranslogAdmin {

    private String transId;

    private String relaTransId;

    private String login_name;

    private String transType;

    private String accountId;

    private String subAccountId;

    private String subAccountType;

    private String transTitle;

    private BigDecimal transAmount;

    private String transDirector;

    private String partyUserid;

    private String transParty;

    private Byte upanddown;

    private BigDecimal sourceBalance;

    private BigDecimal tageetBalance;

    private Byte reverseStatus;

    private Byte displayStatus;

    private Byte isBlockTrans;

    private String transDetailId;

    private Date blockEndTime;

    private String remark;

    private String orderStatus;

    private Date createTime;

    private Date updateTime;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getRelaTransId() {
        return relaTransId;
    }

    public void setRelaTransId(String relaTransId) {
        this.relaTransId = relaTransId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(String subAccountId) {
        this.subAccountId = subAccountId;
    }

    public String getSubAccountType() {
        return subAccountType;
    }

    public void setSubAccountType(String subAccountType) {
        this.subAccountType = subAccountType;
    }

    public String getTransTitle() {
        return transTitle;
    }

    public void setTransTitle(String transTitle) {
        this.transTitle = transTitle;
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransDirector() {
        return transDirector;
    }

    public void setTransDirector(String transDirector) {
        this.transDirector = transDirector;
    }

    public String getPartyUserid() {
        return partyUserid;
    }

    public void setPartyUserid(String partyUserid) {
        this.partyUserid = partyUserid;
    }

    public String getTransParty() {
        return transParty;
    }

    public void setTransParty(String transParty) {
        this.transParty = transParty;
    }

    public Byte getUpanddown() {
        return upanddown;
    }

    public void setUpanddown(Byte upanddown) {
        this.upanddown = upanddown;
    }

    public BigDecimal getSourceBalance() {
        return sourceBalance;
    }

    public void setSourceBalance(BigDecimal sourceBalance) {
        this.sourceBalance = sourceBalance;
    }

    public BigDecimal getTageetBalance() {
        return tageetBalance;
    }

    public void setTageetBalance(BigDecimal tageetBalance) {
        this.tageetBalance = tageetBalance;
    }

    public Byte getReverseStatus() {
        return reverseStatus;
    }

    public void setReverseStatus(Byte reverseStatus) {
        this.reverseStatus = reverseStatus;
    }

    public Byte getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Byte displayStatus) {
        this.displayStatus = displayStatus;
    }

    public Byte getIsBlockTrans() {
        return isBlockTrans;
    }

    public void setIsBlockTrans(Byte isBlockTrans) {
        this.isBlockTrans = isBlockTrans;
    }

    public String getTransDetailId() {
        return transDetailId;
    }

    public void setTransDetailId(String transDetailId) {
        this.transDetailId = transDetailId;
    }

    public Date getBlockEndTime() {
        return blockEndTime;
    }

    public void setBlockEndTime(Date blockEndTime) {
        this.blockEndTime = blockEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }
}