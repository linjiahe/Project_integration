package com.blockchain.commune.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletSubAccountTypeCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wallet_sub_account_type
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wallet_sub_account_type
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wallet_sub_account_type
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_sub_account_type
     *
     * @mbg.generated
     */
    public WalletSubAccountTypeCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_sub_account_type
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_sub_account_type
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_sub_account_type
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_sub_account_type
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_sub_account_type
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_sub_account_type
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_sub_account_type
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
     * This method corresponds to the database table wallet_sub_account_type
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
     * This method corresponds to the database table wallet_sub_account_type
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wallet_sub_account_type
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
     * This class corresponds to the database table wallet_sub_account_type
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

        public Criteria andSubAccountTypeIsNull() {
            addCriterion("sub_account_type is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeIsNotNull() {
            addCriterion("sub_account_type is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeEqualTo(String value) {
            addCriterion("sub_account_type =", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeNotEqualTo(String value) {
            addCriterion("sub_account_type <>", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeGreaterThan(String value) {
            addCriterion("sub_account_type >", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeGreaterThanOrEqualTo(String value) {
            addCriterion("sub_account_type >=", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeLessThan(String value) {
            addCriterion("sub_account_type <", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeLessThanOrEqualTo(String value) {
            addCriterion("sub_account_type <=", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeLike(String value) {
            addCriterion("sub_account_type like", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeNotLike(String value) {
            addCriterion("sub_account_type not like", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeIn(List<String> values) {
            addCriterion("sub_account_type in", values, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeNotIn(List<String> values) {
            addCriterion("sub_account_type not in", values, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeBetween(String value1, String value2) {
            addCriterion("sub_account_type between", value1, value2, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeNotBetween(String value1, String value2) {
            addCriterion("sub_account_type not between", value1, value2, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescIsNull() {
            addCriterion("sub_account_type_desc is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescIsNotNull() {
            addCriterion("sub_account_type_desc is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescEqualTo(String value) {
            addCriterion("sub_account_type_desc =", value, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescNotEqualTo(String value) {
            addCriterion("sub_account_type_desc <>", value, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescGreaterThan(String value) {
            addCriterion("sub_account_type_desc >", value, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescGreaterThanOrEqualTo(String value) {
            addCriterion("sub_account_type_desc >=", value, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescLessThan(String value) {
            addCriterion("sub_account_type_desc <", value, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescLessThanOrEqualTo(String value) {
            addCriterion("sub_account_type_desc <=", value, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescLike(String value) {
            addCriterion("sub_account_type_desc like", value, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescNotLike(String value) {
            addCriterion("sub_account_type_desc not like", value, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescIn(List<String> values) {
            addCriterion("sub_account_type_desc in", values, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescNotIn(List<String> values) {
            addCriterion("sub_account_type_desc not in", values, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescBetween(String value1, String value2) {
            addCriterion("sub_account_type_desc between", value1, value2, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeDescNotBetween(String value1, String value2) {
            addCriterion("sub_account_type_desc not between", value1, value2, "subAccountTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("category like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("category not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("category not between", value1, value2, "category");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table wallet_sub_account_type
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
     * This class corresponds to the database table wallet_sub_account_type
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