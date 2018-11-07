package com.blockchain.commune.model;

import java.util.Date;

public class Team {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team.member_amount
     *
     * @mbg.generated
     */
    private Integer memberAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team.direct_member_amount
     *
     * @mbg.generated
     */
    private Integer directMemberAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team.member_level
     *
     * @mbg.generated
     */
    private Integer memberLevel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team.president_amount
     *
     * @mbg.generated
     */
    private Integer presidentAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team.direct_president_amount
     *
     * @mbg.generated
     */
    private Integer directPresidentAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column team.user_id
     *
     * @return the value of team.user_id
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column team.user_id
     *
     * @param userId the value for team.user_id
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column team.member_amount
     *
     * @return the value of team.member_amount
     *
     * @mbg.generated
     */
    public Integer getMemberAmount() {
        return memberAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column team.member_amount
     *
     * @param memberAmount the value for team.member_amount
     *
     * @mbg.generated
     */
    public void setMemberAmount(Integer memberAmount) {
        this.memberAmount = memberAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column team.direct_member_amount
     *
     * @return the value of team.direct_member_amount
     *
     * @mbg.generated
     */
    public Integer getDirectMemberAmount() {
        return directMemberAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column team.direct_member_amount
     *
     * @param directMemberAmount the value for team.direct_member_amount
     *
     * @mbg.generated
     */
    public void setDirectMemberAmount(Integer directMemberAmount) {
        this.directMemberAmount = directMemberAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column team.member_level
     *
     * @return the value of team.member_level
     *
     * @mbg.generated
     */
    public Integer getMemberLevel() {
        return memberLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column team.member_level
     *
     * @param memberLevel the value for team.member_level
     *
     * @mbg.generated
     */
    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column team.president_amount
     *
     * @return the value of team.president_amount
     *
     * @mbg.generated
     */
    public Integer getPresidentAmount() {
        return presidentAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column team.president_amount
     *
     * @param presidentAmount the value for team.president_amount
     *
     * @mbg.generated
     */
    public void setPresidentAmount(Integer presidentAmount) {
        this.presidentAmount = presidentAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column team.direct_president_amount
     *
     * @return the value of team.direct_president_amount
     *
     * @mbg.generated
     */
    public Integer getDirectPresidentAmount() {
        return directPresidentAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column team.direct_president_amount
     *
     * @param directPresidentAmount the value for team.direct_president_amount
     *
     * @mbg.generated
     */
    public void setDirectPresidentAmount(Integer directPresidentAmount) {
        this.directPresidentAmount = directPresidentAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column team.create_time
     *
     * @return the value of team.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column team.create_time
     *
     * @param createTime the value for team.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column team.update_time
     *
     * @return the value of team.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column team.update_time
     *
     * @param updateTime the value for team.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}