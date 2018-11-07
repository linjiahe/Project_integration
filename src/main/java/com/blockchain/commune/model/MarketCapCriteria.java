package com.blockchain.commune.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MarketCapCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table market_cap
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table market_cap
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table market_cap
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_cap
     *
     * @mbg.generated
     */
    public MarketCapCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_cap
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_cap
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_cap
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_cap
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_cap
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_cap
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_cap
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
     * This method corresponds to the database table market_cap
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
     * This method corresponds to the database table market_cap
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_cap
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
     * This class corresponds to the database table market_cap
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

        public Criteria andPriceUsdIsNull() {
            addCriterion("price_usd is null");
            return (Criteria) this;
        }

        public Criteria andPriceUsdIsNotNull() {
            addCriterion("price_usd is not null");
            return (Criteria) this;
        }

        public Criteria andPriceUsdEqualTo(BigDecimal value) {
            addCriterion("price_usd =", value, "priceUsd");
            return (Criteria) this;
        }

        public Criteria andPriceUsdNotEqualTo(BigDecimal value) {
            addCriterion("price_usd <>", value, "priceUsd");
            return (Criteria) this;
        }

        public Criteria andPriceUsdGreaterThan(BigDecimal value) {
            addCriterion("price_usd >", value, "priceUsd");
            return (Criteria) this;
        }

        public Criteria andPriceUsdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price_usd >=", value, "priceUsd");
            return (Criteria) this;
        }

        public Criteria andPriceUsdLessThan(BigDecimal value) {
            addCriterion("price_usd <", value, "priceUsd");
            return (Criteria) this;
        }

        public Criteria andPriceUsdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price_usd <=", value, "priceUsd");
            return (Criteria) this;
        }

        public Criteria andPriceUsdIn(List<BigDecimal> values) {
            addCriterion("price_usd in", values, "priceUsd");
            return (Criteria) this;
        }

        public Criteria andPriceUsdNotIn(List<BigDecimal> values) {
            addCriterion("price_usd not in", values, "priceUsd");
            return (Criteria) this;
        }

        public Criteria andPriceUsdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_usd between", value1, value2, "priceUsd");
            return (Criteria) this;
        }

        public Criteria andPriceUsdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_usd not between", value1, value2, "priceUsd");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyIsNull() {
            addCriterion("market_cap_display_cny is null");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyIsNotNull() {
            addCriterion("market_cap_display_cny is not null");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyEqualTo(BigDecimal value) {
            addCriterion("market_cap_display_cny =", value, "marketCapDisplayCny");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyNotEqualTo(BigDecimal value) {
            addCriterion("market_cap_display_cny <>", value, "marketCapDisplayCny");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyGreaterThan(BigDecimal value) {
            addCriterion("market_cap_display_cny >", value, "marketCapDisplayCny");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("market_cap_display_cny >=", value, "marketCapDisplayCny");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyLessThan(BigDecimal value) {
            addCriterion("market_cap_display_cny <", value, "marketCapDisplayCny");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("market_cap_display_cny <=", value, "marketCapDisplayCny");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyIn(List<BigDecimal> values) {
            addCriterion("market_cap_display_cny in", values, "marketCapDisplayCny");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyNotIn(List<BigDecimal> values) {
            addCriterion("market_cap_display_cny not in", values, "marketCapDisplayCny");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("market_cap_display_cny between", value1, value2, "marketCapDisplayCny");
            return (Criteria) this;
        }

        public Criteria andMarketCapDisplayCnyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("market_cap_display_cny not between", value1, value2, "marketCapDisplayCny");
            return (Criteria) this;
        }

        public Criteria andVolume24hIsNull() {
            addCriterion("volume_24h is null");
            return (Criteria) this;
        }

        public Criteria andVolume24hIsNotNull() {
            addCriterion("volume_24h is not null");
            return (Criteria) this;
        }

        public Criteria andVolume24hEqualTo(BigDecimal value) {
            addCriterion("volume_24h =", value, "volume24h");
            return (Criteria) this;
        }

        public Criteria andVolume24hNotEqualTo(BigDecimal value) {
            addCriterion("volume_24h <>", value, "volume24h");
            return (Criteria) this;
        }

        public Criteria andVolume24hGreaterThan(BigDecimal value) {
            addCriterion("volume_24h >", value, "volume24h");
            return (Criteria) this;
        }

        public Criteria andVolume24hGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("volume_24h >=", value, "volume24h");
            return (Criteria) this;
        }

        public Criteria andVolume24hLessThan(BigDecimal value) {
            addCriterion("volume_24h <", value, "volume24h");
            return (Criteria) this;
        }

        public Criteria andVolume24hLessThanOrEqualTo(BigDecimal value) {
            addCriterion("volume_24h <=", value, "volume24h");
            return (Criteria) this;
        }

        public Criteria andVolume24hIn(List<BigDecimal> values) {
            addCriterion("volume_24h in", values, "volume24h");
            return (Criteria) this;
        }

        public Criteria andVolume24hNotIn(List<BigDecimal> values) {
            addCriterion("volume_24h not in", values, "volume24h");
            return (Criteria) this;
        }

        public Criteria andVolume24hBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("volume_24h between", value1, value2, "volume24h");
            return (Criteria) this;
        }

        public Criteria andVolume24hNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("volume_24h not between", value1, value2, "volume24h");
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

        public Criteria andAliasIsNull() {
            addCriterion("alias is null");
            return (Criteria) this;
        }

        public Criteria andAliasIsNotNull() {
            addCriterion("alias is not null");
            return (Criteria) this;
        }

        public Criteria andAliasEqualTo(String value) {
            addCriterion("alias =", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasNotEqualTo(String value) {
            addCriterion("alias <>", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasGreaterThan(String value) {
            addCriterion("alias >", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasGreaterThanOrEqualTo(String value) {
            addCriterion("alias >=", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasLessThan(String value) {
            addCriterion("alias <", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasLessThanOrEqualTo(String value) {
            addCriterion("alias <=", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasLike(String value) {
            addCriterion("alias like", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasNotLike(String value) {
            addCriterion("alias not like", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasIn(List<String> values) {
            addCriterion("alias in", values, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasNotIn(List<String> values) {
            addCriterion("alias not in", values, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasBetween(String value1, String value2) {
            addCriterion("alias between", value1, value2, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasNotBetween(String value1, String value2) {
            addCriterion("alias not between", value1, value2, "alias");
            return (Criteria) this;
        }

        public Criteria andRankIsNull() {
            addCriterion("rank is null");
            return (Criteria) this;
        }

        public Criteria andRankIsNotNull() {
            addCriterion("rank is not null");
            return (Criteria) this;
        }

        public Criteria andRankEqualTo(Integer value) {
            addCriterion("rank =", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotEqualTo(Integer value) {
            addCriterion("rank <>", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThan(Integer value) {
            addCriterion("rank >", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThanOrEqualTo(Integer value) {
            addCriterion("rank >=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThan(Integer value) {
            addCriterion("rank <", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThanOrEqualTo(Integer value) {
            addCriterion("rank <=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankIn(List<Integer> values) {
            addCriterion("rank in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotIn(List<Integer> values) {
            addCriterion("rank not in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankBetween(Integer value1, Integer value2) {
            addCriterion("rank between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotBetween(Integer value1, Integer value2) {
            addCriterion("rank not between", value1, value2, "rank");
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

        public Criteria andVolume24hUsdIsNull() {
            addCriterion("volume_24h_usd is null");
            return (Criteria) this;
        }

        public Criteria andVolume24hUsdIsNotNull() {
            addCriterion("volume_24h_usd is not null");
            return (Criteria) this;
        }

        public Criteria andVolume24hUsdEqualTo(BigDecimal value) {
            addCriterion("volume_24h_usd =", value, "volume24hUsd");
            return (Criteria) this;
        }

        public Criteria andVolume24hUsdNotEqualTo(BigDecimal value) {
            addCriterion("volume_24h_usd <>", value, "volume24hUsd");
            return (Criteria) this;
        }

        public Criteria andVolume24hUsdGreaterThan(BigDecimal value) {
            addCriterion("volume_24h_usd >", value, "volume24hUsd");
            return (Criteria) this;
        }

        public Criteria andVolume24hUsdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("volume_24h_usd >=", value, "volume24hUsd");
            return (Criteria) this;
        }

        public Criteria andVolume24hUsdLessThan(BigDecimal value) {
            addCriterion("volume_24h_usd <", value, "volume24hUsd");
            return (Criteria) this;
        }

        public Criteria andVolume24hUsdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("volume_24h_usd <=", value, "volume24hUsd");
            return (Criteria) this;
        }

        public Criteria andVolume24hUsdIn(List<BigDecimal> values) {
            addCriterion("volume_24h_usd in", values, "volume24hUsd");
            return (Criteria) this;
        }

        public Criteria andVolume24hUsdNotIn(List<BigDecimal> values) {
            addCriterion("volume_24h_usd not in", values, "volume24hUsd");
            return (Criteria) this;
        }

        public Criteria andVolume24hUsdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("volume_24h_usd between", value1, value2, "volume24hUsd");
            return (Criteria) this;
        }

        public Criteria andVolume24hUsdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("volume_24h_usd not between", value1, value2, "volume24hUsd");
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

        public Criteria andBatchIsNull() {
            addCriterion("batch is null");
            return (Criteria) this;
        }

        public Criteria andBatchIsNotNull() {
            addCriterion("batch is not null");
            return (Criteria) this;
        }

        public Criteria andBatchEqualTo(String value) {
            addCriterion("batch =", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchNotEqualTo(String value) {
            addCriterion("batch <>", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchGreaterThan(String value) {
            addCriterion("batch >", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchGreaterThanOrEqualTo(String value) {
            addCriterion("batch >=", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchLessThan(String value) {
            addCriterion("batch <", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchLessThanOrEqualTo(String value) {
            addCriterion("batch <=", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchLike(String value) {
            addCriterion("batch like", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchNotLike(String value) {
            addCriterion("batch not like", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchIn(List<String> values) {
            addCriterion("batch in", values, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchNotIn(List<String> values) {
            addCriterion("batch not in", values, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchBetween(String value1, String value2) {
            addCriterion("batch between", value1, value2, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchNotBetween(String value1, String value2) {
            addCriterion("batch not between", value1, value2, "batch");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table market_cap
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
     * This class corresponds to the database table market_cap
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