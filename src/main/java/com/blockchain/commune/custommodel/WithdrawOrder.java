package com.blockchain.commune.custommodel;

import java.math.BigDecimal;
import java.util.Date;

public class WithdrawOrder {
    private String transId;
    private String loginName;
    private String accountId;
    private String fromAddress;
    private String toAddress;
    private String coinName;
    private String sourceBalance;
    private String tageetBalance;
    private BigDecimal amount;
    private String state;
    private Date createTime;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSourceBalance() {
        return sourceBalance;
    }

    public void setSourceBalance(String sourceBalance) {
        this.sourceBalance = sourceBalance;
    }

    public String getTageetBalance() {
        return tageetBalance;
    }

    public void setTageetBalance(String tageetBalance) {
        this.tageetBalance = tageetBalance;
    }
}
