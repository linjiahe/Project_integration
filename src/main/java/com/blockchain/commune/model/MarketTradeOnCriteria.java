package com.blockchain.commune.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MarketTradeOnCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table market_trade_on
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table market_trade_on
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table market_trade_on
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_trade_on
     *
     * @mbg.generated
     */
    public MarketTradeOnCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_trade_on
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_trade_on
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_trade_on
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_trade_on
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_trade_on
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_trade_on
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_trade_on
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
     * This method corresponds to the database table market_trade_on
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
     * This method corresponds to the database table market_trade_on
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_trade_on
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
     * This class corresponds to the database table market_trade_on
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSymbolIsNull() {
            addCriterion("symbol is null");
            return (Criteria) this;
        }

        public Criteria andSymbolIsNotNull() {
            addCriterion("symbol is not null");
            return (Criteria) this;
        }

        public Criteria andSymbolEqualTo(String value) {
            addCriterion("symbol =", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotEqualTo(String value) {
            addCriterion("symbol <>", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThan(String value) {
            addCriterion("symbol >", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThanOrEqualTo(String value) {
            addCriterion("symbol >=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThan(String value) {
            addCriterion("symbol <", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThanOrEqualTo(String value) {
            addCriterion("symbol <=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLike(String value) {
            addCriterion("symbol like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotLike(String value) {
            addCriterion("symbol not like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolIn(List<String> values) {
            addCriterion("symbol in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotIn(List<String> values) {
            addCriterion("symbol not in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolBetween(String value1, String value2) {
            addCriterion("symbol between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotBetween(String value1, String value2) {
            addCriterion("symbol not between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andMarketIdIsNull() {
            addCriterion("market_id is null");
            return (Criteria) this;
        }

        public Criteria andMarketIdIsNotNull() {
            addCriterion("market_id is not null");
            return (Criteria) this;
        }

        public Criteria andMarketIdEqualTo(String value) {
            addCriterion("market_id =", value, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketIdNotEqualTo(String value) {
            addCriterion("market_id <>", value, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketIdGreaterThan(String value) {
            addCriterion("market_id >", value, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketIdGreaterThanOrEqualTo(String value) {
            addCriterion("market_id >=", value, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketIdLessThan(String value) {
            addCriterion("market_id <", value, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketIdLessThanOrEqualTo(String value) {
            addCriterion("market_id <=", value, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketIdLike(String value) {
            addCriterion("market_id like", value, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketIdNotLike(String value) {
            addCriterion("market_id not like", value, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketIdIn(List<String> values) {
            addCriterion("market_id in", values, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketIdNotIn(List<String> values) {
            addCriterion("market_id not in", values, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketIdBetween(String value1, String value2) {
            addCriterion("market_id between", value1, value2, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketIdNotBetween(String value1, String value2) {
            addCriterion("market_id not between", value1, value2, "marketId");
            return (Criteria) this;
        }

        public Criteria andMarketNameIsNull() {
            addCriterion("market_name is null");
            return (Criteria) this;
        }

        public Criteria andMarketNameIsNotNull() {
            addCriterion("market_name is not null");
            return (Criteria) this;
        }

        public Criteria andMarketNameEqualTo(String value) {
            addCriterion("market_name =", value, "marketName");
            return (Criteria) this;
        }

        public Criteria andMarketNameNotEqualTo(String value) {
            addCriterion("market_name <>", value, "marketName");
            return (Criteria) this;
        }

        public Criteria andMarketNameGreaterThan(String value) {
            addCriterion("market_name >", value, "marketName");
            return (Criteria) this;
        }

        public Criteria andMarketNameGreaterThanOrEqualTo(String value) {
            addCriterion("market_name >=", value, "marketName");
            return (Criteria) this;
        }

        public Criteria andMarketNameLessThan(String value) {
            addCriterion("market_name <", value, "marketName");
            return (Criteria) this;
        }

        public Criteria andMarketNameLessThanOrEqualTo(String value) {
            addCriterion("market_name <=", value, "marketName");
            return (Criteria) this;
        }

        public Criteria andMarketNameLike(String value) {
            addCriterion("market_name like", value, "marketName");
            return (Criteria) this;
        }

        public Criteria andMarketNameNotLike(String value) {
            addCriterion("market_name not like", value, "marketName");
            return (Criteria) this;
        }

        public Criteria andMarketNameIn(List<String> values) {
            addCriterion("market_name in", values, "marketName");
            return (Criteria) this;
        }

        public Criteria andMarketNameNotIn(List<String> values) {
            addCriterion("market_name not in", values, "marketName");
            return (Criteria) this;
        }

        public Criteria andMarketNameBetween(String value1, String value2) {
            addCriterion("market_name between", value1, value2, "marketName");
            return (Criteria) this;
        }

        public Criteria andMarketNameNotBetween(String value1, String value2) {
            addCriterion("market_name not between", value1, value2, "marketName");
            return (Criteria) this;
        }

        public Criteria andPairIsNull() {
            addCriterion("pair is null");
            return (Criteria) this;
        }

        public Criteria andPairIsNotNull() {
            addCriterion("pair is not null");
            return (Criteria) this;
        }

        public Criteria andPairEqualTo(String value) {
            addCriterion("pair =", value, "pair");
            return (Criteria) this;
        }

        public Criteria andPairNotEqualTo(String value) {
            addCriterion("pair <>", value, "pair");
            return (Criteria) this;
        }

        public Criteria andPairGreaterThan(String value) {
            addCriterion("pair >", value, "pair");
            return (Criteria) this;
        }

        public Criteria andPairGreaterThanOrEqualTo(String value) {
            addCriterion("pair >=", value, "pair");
            return (Criteria) this;
        }

        public Criteria andPairLessThan(String value) {
            addCriterion("pair <", value, "pair");
            return (Criteria) this;
        }

        public Criteria andPairLessThanOrEqualTo(String value) {
            addCriterion("pair <=", value, "pair");
            return (Criteria) this;
        }

        public Criteria andPairLike(String value) {
            addCriterion("pair like", value, "pair");
            return (Criteria) this;
        }

        public Criteria andPairNotLike(String value) {
            addCriterion("pair not like", value, "pair");
            return (Criteria) this;
        }

        public Criteria andPairIn(List<String> values) {
            addCriterion("pair in", values, "pair");
            return (Criteria) this;
        }

        public Criteria andPairNotIn(List<String> values) {
            addCriterion("pair not in", values, "pair");
            return (Criteria) this;
        }

        public Criteria andPairBetween(String value1, String value2) {
            addCriterion("pair between", value1, value2, "pair");
            return (Criteria) this;
        }

        public Criteria andPairNotBetween(String value1, String value2) {
            addCriterion("pair not between", value1, value2, "pair");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayIsNull() {
            addCriterion("price_display is null");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayIsNotNull() {
            addCriterion("price_display is not null");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayEqualTo(String value) {
            addCriterion("price_display =", value, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayNotEqualTo(String value) {
            addCriterion("price_display <>", value, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayGreaterThan(String value) {
            addCriterion("price_display >", value, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayGreaterThanOrEqualTo(String value) {
            addCriterion("price_display >=", value, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayLessThan(String value) {
            addCriterion("price_display <", value, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayLessThanOrEqualTo(String value) {
            addCriterion("price_display <=", value, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayLike(String value) {
            addCriterion("price_display like", value, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayNotLike(String value) {
            addCriterion("price_display not like", value, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayIn(List<String> values) {
            addCriterion("price_display in", values, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayNotIn(List<String> values) {
            addCriterion("price_display not in", values, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayBetween(String value1, String value2) {
            addCriterion("price_display between", value1, value2, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andPriceDisplayNotBetween(String value1, String value2) {
            addCriterion("price_display not between", value1, value2, "priceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayIsNull() {
            addCriterion("hr_price_display is null");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayIsNotNull() {
            addCriterion("hr_price_display is not null");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayEqualTo(String value) {
            addCriterion("hr_price_display =", value, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayNotEqualTo(String value) {
            addCriterion("hr_price_display <>", value, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayGreaterThan(String value) {
            addCriterion("hr_price_display >", value, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayGreaterThanOrEqualTo(String value) {
            addCriterion("hr_price_display >=", value, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayLessThan(String value) {
            addCriterion("hr_price_display <", value, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayLessThanOrEqualTo(String value) {
            addCriterion("hr_price_display <=", value, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayLike(String value) {
            addCriterion("hr_price_display like", value, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayNotLike(String value) {
            addCriterion("hr_price_display not like", value, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayIn(List<String> values) {
            addCriterion("hr_price_display in", values, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayNotIn(List<String> values) {
            addCriterion("hr_price_display not in", values, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayBetween(String value1, String value2) {
            addCriterion("hr_price_display between", value1, value2, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andHrPriceDisplayNotBetween(String value1, String value2) {
            addCriterion("hr_price_display not between", value1, value2, "hrPriceDisplay");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayIsNull() {
            addCriterion("percent_change_display is null");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayIsNotNull() {
            addCriterion("percent_change_display is not null");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayEqualTo(BigDecimal value) {
            addCriterion("percent_change_display =", value, "percentChangeDisplay");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayNotEqualTo(BigDecimal value) {
            addCriterion("percent_change_display <>", value, "percentChangeDisplay");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayGreaterThan(BigDecimal value) {
            addCriterion("percent_change_display >", value, "percentChangeDisplay");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("percent_change_display >=", value, "percentChangeDisplay");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayLessThan(BigDecimal value) {
            addCriterion("percent_change_display <", value, "percentChangeDisplay");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("percent_change_display <=", value, "percentChangeDisplay");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayIn(List<BigDecimal> values) {
            addCriterion("percent_change_display in", values, "percentChangeDisplay");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayNotIn(List<BigDecimal> values) {
            addCriterion("percent_change_display not in", values, "percentChangeDisplay");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("percent_change_display between", value1, value2, "percentChangeDisplay");
            return (Criteria) this;
        }

        public Criteria andPercentChangeDisplayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("percent_change_display not between", value1, value2, "percentChangeDisplay");
            return (Criteria) this;
        }

        public Criteria andMarketAliasIsNull() {
            addCriterion("market_alias is null");
            return (Criteria) this;
        }

        public Criteria andMarketAliasIsNotNull() {
            addCriterion("market_alias is not null");
            return (Criteria) this;
        }

        public Criteria andMarketAliasEqualTo(String value) {
            addCriterion("market_alias =", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasNotEqualTo(String value) {
            addCriterion("market_alias <>", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasGreaterThan(String value) {
            addCriterion("market_alias >", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasGreaterThanOrEqualTo(String value) {
            addCriterion("market_alias >=", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasLessThan(String value) {
            addCriterion("market_alias <", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasLessThanOrEqualTo(String value) {
            addCriterion("market_alias <=", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasLike(String value) {
            addCriterion("market_alias like", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasNotLike(String value) {
            addCriterion("market_alias not like", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasIn(List<String> values) {
            addCriterion("market_alias in", values, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasNotIn(List<String> values) {
            addCriterion("market_alias not in", values, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasBetween(String value1, String value2) {
            addCriterion("market_alias between", value1, value2, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasNotBetween(String value1, String value2) {
            addCriterion("market_alias not between", value1, value2, "marketAlias");
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

        public Criteria andLogoIsNull() {
            addCriterion("logo is null");
            return (Criteria) this;
        }

        public Criteria andLogoIsNotNull() {
            addCriterion("logo is not null");
            return (Criteria) this;
        }

        public Criteria andLogoEqualTo(String value) {
            addCriterion("logo =", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotEqualTo(String value) {
            addCriterion("logo <>", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThan(String value) {
            addCriterion("logo >", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanOrEqualTo(String value) {
            addCriterion("logo >=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThan(String value) {
            addCriterion("logo <", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThanOrEqualTo(String value) {
            addCriterion("logo <=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLike(String value) {
            addCriterion("logo like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotLike(String value) {
            addCriterion("logo not like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoIn(List<String> values) {
            addCriterion("logo in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotIn(List<String> values) {
            addCriterion("logo not in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoBetween(String value1, String value2) {
            addCriterion("logo between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotBetween(String value1, String value2) {
            addCriterion("logo not between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table market_trade_on
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
     * This class corresponds to the database table market_trade_on
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