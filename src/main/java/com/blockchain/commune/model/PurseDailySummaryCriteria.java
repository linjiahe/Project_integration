package com.blockchain.commune.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurseDailySummaryCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    public PurseDailySummaryCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andDailySummaryIdIsNull() {
            addCriterion("daily_summary_id is null");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdIsNotNull() {
            addCriterion("daily_summary_id is not null");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdEqualTo(String value) {
            addCriterion("daily_summary_id =", value, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdNotEqualTo(String value) {
            addCriterion("daily_summary_id <>", value, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdGreaterThan(String value) {
            addCriterion("daily_summary_id >", value, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdGreaterThanOrEqualTo(String value) {
            addCriterion("daily_summary_id >=", value, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdLessThan(String value) {
            addCriterion("daily_summary_id <", value, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdLessThanOrEqualTo(String value) {
            addCriterion("daily_summary_id <=", value, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdLike(String value) {
            addCriterion("daily_summary_id like", value, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdNotLike(String value) {
            addCriterion("daily_summary_id not like", value, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdIn(List<String> values) {
            addCriterion("daily_summary_id in", values, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdNotIn(List<String> values) {
            addCriterion("daily_summary_id not in", values, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdBetween(String value1, String value2) {
            addCriterion("daily_summary_id between", value1, value2, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andDailySummaryIdNotBetween(String value1, String value2) {
            addCriterion("daily_summary_id not between", value1, value2, "dailySummaryId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(String value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(String value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(String value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(String value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(String value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLike(String value) {
            addCriterion("account_id like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotLike(String value) {
            addCriterion("account_id not like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<String> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<String> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(String value1, String value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(String value1, String value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andDailyNameIsNull() {
            addCriterion("daily_name is null");
            return (Criteria) this;
        }

        public Criteria andDailyNameIsNotNull() {
            addCriterion("daily_name is not null");
            return (Criteria) this;
        }

        public Criteria andDailyNameEqualTo(String value) {
            addCriterion("daily_name =", value, "dailyName");
            return (Criteria) this;
        }

        public Criteria andDailyNameNotEqualTo(String value) {
            addCriterion("daily_name <>", value, "dailyName");
            return (Criteria) this;
        }

        public Criteria andDailyNameGreaterThan(String value) {
            addCriterion("daily_name >", value, "dailyName");
            return (Criteria) this;
        }

        public Criteria andDailyNameGreaterThanOrEqualTo(String value) {
            addCriterion("daily_name >=", value, "dailyName");
            return (Criteria) this;
        }

        public Criteria andDailyNameLessThan(String value) {
            addCriterion("daily_name <", value, "dailyName");
            return (Criteria) this;
        }

        public Criteria andDailyNameLessThanOrEqualTo(String value) {
            addCriterion("daily_name <=", value, "dailyName");
            return (Criteria) this;
        }

        public Criteria andDailyNameLike(String value) {
            addCriterion("daily_name like", value, "dailyName");
            return (Criteria) this;
        }

        public Criteria andDailyNameNotLike(String value) {
            addCriterion("daily_name not like", value, "dailyName");
            return (Criteria) this;
        }

        public Criteria andDailyNameIn(List<String> values) {
            addCriterion("daily_name in", values, "dailyName");
            return (Criteria) this;
        }

        public Criteria andDailyNameNotIn(List<String> values) {
            addCriterion("daily_name not in", values, "dailyName");
            return (Criteria) this;
        }

        public Criteria andDailyNameBetween(String value1, String value2) {
            addCriterion("daily_name between", value1, value2, "dailyName");
            return (Criteria) this;
        }

        public Criteria andDailyNameNotBetween(String value1, String value2) {
            addCriterion("daily_name not between", value1, value2, "dailyName");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNull() {
            addCriterion("trans_amount is null");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNotNull() {
            addCriterion("trans_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTransAmountEqualTo(BigDecimal value) {
            addCriterion("trans_amount =", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotEqualTo(BigDecimal value) {
            addCriterion("trans_amount <>", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThan(BigDecimal value) {
            addCriterion("trans_amount >", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_amount >=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThan(BigDecimal value) {
            addCriterion("trans_amount <", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_amount <=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountIn(List<BigDecimal> values) {
            addCriterion("trans_amount in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotIn(List<BigDecimal> values) {
            addCriterion("trans_amount not in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_amount between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_amount not between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransInAmountIsNull() {
            addCriterion("trans_in_amount is null");
            return (Criteria) this;
        }

        public Criteria andTransInAmountIsNotNull() {
            addCriterion("trans_in_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTransInAmountEqualTo(BigDecimal value) {
            addCriterion("trans_in_amount =", value, "transInAmount");
            return (Criteria) this;
        }

        public Criteria andTransInAmountNotEqualTo(BigDecimal value) {
            addCriterion("trans_in_amount <>", value, "transInAmount");
            return (Criteria) this;
        }

        public Criteria andTransInAmountGreaterThan(BigDecimal value) {
            addCriterion("trans_in_amount >", value, "transInAmount");
            return (Criteria) this;
        }

        public Criteria andTransInAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_in_amount >=", value, "transInAmount");
            return (Criteria) this;
        }

        public Criteria andTransInAmountLessThan(BigDecimal value) {
            addCriterion("trans_in_amount <", value, "transInAmount");
            return (Criteria) this;
        }

        public Criteria andTransInAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_in_amount <=", value, "transInAmount");
            return (Criteria) this;
        }

        public Criteria andTransInAmountIn(List<BigDecimal> values) {
            addCriterion("trans_in_amount in", values, "transInAmount");
            return (Criteria) this;
        }

        public Criteria andTransInAmountNotIn(List<BigDecimal> values) {
            addCriterion("trans_in_amount not in", values, "transInAmount");
            return (Criteria) this;
        }

        public Criteria andTransInAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_in_amount between", value1, value2, "transInAmount");
            return (Criteria) this;
        }

        public Criteria andTransInAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_in_amount not between", value1, value2, "transInAmount");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountIsNull() {
            addCriterion("trans_out_amount is null");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountIsNotNull() {
            addCriterion("trans_out_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountEqualTo(BigDecimal value) {
            addCriterion("trans_out_amount =", value, "transOutAmount");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountNotEqualTo(BigDecimal value) {
            addCriterion("trans_out_amount <>", value, "transOutAmount");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountGreaterThan(BigDecimal value) {
            addCriterion("trans_out_amount >", value, "transOutAmount");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_out_amount >=", value, "transOutAmount");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountLessThan(BigDecimal value) {
            addCriterion("trans_out_amount <", value, "transOutAmount");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_out_amount <=", value, "transOutAmount");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountIn(List<BigDecimal> values) {
            addCriterion("trans_out_amount in", values, "transOutAmount");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountNotIn(List<BigDecimal> values) {
            addCriterion("trans_out_amount not in", values, "transOutAmount");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_out_amount between", value1, value2, "transOutAmount");
            return (Criteria) this;
        }

        public Criteria andTransOutAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_out_amount not between", value1, value2, "transOutAmount");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceIsNull() {
            addCriterion("source_balance is null");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceIsNotNull() {
            addCriterion("source_balance is not null");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceEqualTo(BigDecimal value) {
            addCriterion("source_balance =", value, "sourceBalance");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceNotEqualTo(BigDecimal value) {
            addCriterion("source_balance <>", value, "sourceBalance");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceGreaterThan(BigDecimal value) {
            addCriterion("source_balance >", value, "sourceBalance");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("source_balance >=", value, "sourceBalance");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceLessThan(BigDecimal value) {
            addCriterion("source_balance <", value, "sourceBalance");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("source_balance <=", value, "sourceBalance");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceIn(List<BigDecimal> values) {
            addCriterion("source_balance in", values, "sourceBalance");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceNotIn(List<BigDecimal> values) {
            addCriterion("source_balance not in", values, "sourceBalance");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("source_balance between", value1, value2, "sourceBalance");
            return (Criteria) this;
        }

        public Criteria andSourceBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("source_balance not between", value1, value2, "sourceBalance");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceIsNull() {
            addCriterion("tageet_balance is null");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceIsNotNull() {
            addCriterion("tageet_balance is not null");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceEqualTo(BigDecimal value) {
            addCriterion("tageet_balance =", value, "tageetBalance");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceNotEqualTo(BigDecimal value) {
            addCriterion("tageet_balance <>", value, "tageetBalance");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceGreaterThan(BigDecimal value) {
            addCriterion("tageet_balance >", value, "tageetBalance");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tageet_balance >=", value, "tageetBalance");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceLessThan(BigDecimal value) {
            addCriterion("tageet_balance <", value, "tageetBalance");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tageet_balance <=", value, "tageetBalance");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceIn(List<BigDecimal> values) {
            addCriterion("tageet_balance in", values, "tageetBalance");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceNotIn(List<BigDecimal> values) {
            addCriterion("tageet_balance not in", values, "tageetBalance");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tageet_balance between", value1, value2, "tageetBalance");
            return (Criteria) this;
        }

        public Criteria andTageetBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tageet_balance not between", value1, value2, "tageetBalance");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table purse_daily_summary
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table purse_daily_summary
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}