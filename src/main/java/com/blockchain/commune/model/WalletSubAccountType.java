package com.blockchain.commune.model;

import java.util.Date;

public class WalletSubAccountType {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wallet_sub_account_type.sub_account_type
     *
     * @mbg.generated
     */
    private String subAccountType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wallet_sub_account_type.sub_account_type_desc
     *
     * @mbg.generated
     */
    private String subAccountTypeDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wallet_sub_account_type.category
     *
     * @mbg.generated
     */
    private String category;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wallet_sub_account_type.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wallet_sub_account_type.sub_account_type
     *
     * @return the value of wallet_sub_account_type.sub_account_type
     *
     * @mbg.generated
     */
    public String getSubAccountType() {
        return subAccountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wallet_sub_account_type.sub_account_type
     *
     * @param subAccountType the value for wallet_sub_account_type.sub_account_type
     *
     * @mbg.generated
     */
    public void setSubAccountType(String subAccountType) {
        this.subAccountType = subAccountType == null ? null : subAccountType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wallet_sub_account_type.sub_account_type_desc
     *
     * @return the value of wallet_sub_account_type.sub_account_type_desc
     *
     * @mbg.generated
     */
    public String getSubAccountTypeDesc() {
        return subAccountTypeDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wallet_sub_account_type.sub_account_type_desc
     *
     * @param subAccountTypeDesc the value for wallet_sub_account_type.sub_account_type_desc
     *
     * @mbg.generated
     */
    public void setSubAccountTypeDesc(String subAccountTypeDesc) {
        this.subAccountTypeDesc = subAccountTypeDesc == null ? null : subAccountTypeDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wallet_sub_account_type.category
     *
     * @return the value of wallet_sub_account_type.category
     *
     * @mbg.generated
     */
    public String getCategory() {
        return category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wallet_sub_account_type.category
     *
     * @param category the value for wallet_sub_account_type.category
     *
     * @mbg.generated
     */
    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wallet_sub_account_type.create_time
     *
     * @return the value of wallet_sub_account_type.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wallet_sub_account_type.create_time
     *
     * @param createTime the value for wallet_sub_account_type.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}