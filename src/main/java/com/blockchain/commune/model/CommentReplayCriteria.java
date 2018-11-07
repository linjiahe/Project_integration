package com.blockchain.commune.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentReplayCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table comment_replay
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table comment_replay
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table comment_replay
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_replay
     *
     * @mbg.generated
     */
    public CommentReplayCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_replay
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_replay
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_replay
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_replay
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_replay
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_replay
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_replay
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
     * This method corresponds to the database table comment_replay
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
     * This method corresponds to the database table comment_replay
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_replay
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
     * This class corresponds to the database table comment_replay
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

        public Criteria andReplayIdIsNull() {
            addCriterion("replay_id is null");
            return (Criteria) this;
        }

        public Criteria andReplayIdIsNotNull() {
            addCriterion("replay_id is not null");
            return (Criteria) this;
        }

        public Criteria andReplayIdEqualTo(String value) {
            addCriterion("replay_id =", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdNotEqualTo(String value) {
            addCriterion("replay_id <>", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdGreaterThan(String value) {
            addCriterion("replay_id >", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdGreaterThanOrEqualTo(String value) {
            addCriterion("replay_id >=", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdLessThan(String value) {
            addCriterion("replay_id <", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdLessThanOrEqualTo(String value) {
            addCriterion("replay_id <=", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdLike(String value) {
            addCriterion("replay_id like", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdNotLike(String value) {
            addCriterion("replay_id not like", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdIn(List<String> values) {
            addCriterion("replay_id in", values, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdNotIn(List<String> values) {
            addCriterion("replay_id not in", values, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdBetween(String value1, String value2) {
            addCriterion("replay_id between", value1, value2, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdNotBetween(String value1, String value2) {
            addCriterion("replay_id not between", value1, value2, "replayId");
            return (Criteria) this;
        }

        public Criteria andCommentIdIsNull() {
            addCriterion("comment_id is null");
            return (Criteria) this;
        }

        public Criteria andCommentIdIsNotNull() {
            addCriterion("comment_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommentIdEqualTo(String value) {
            addCriterion("comment_id =", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdNotEqualTo(String value) {
            addCriterion("comment_id <>", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdGreaterThan(String value) {
            addCriterion("comment_id >", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdGreaterThanOrEqualTo(String value) {
            addCriterion("comment_id >=", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdLessThan(String value) {
            addCriterion("comment_id <", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdLessThanOrEqualTo(String value) {
            addCriterion("comment_id <=", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdLike(String value) {
            addCriterion("comment_id like", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdNotLike(String value) {
            addCriterion("comment_id not like", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdIn(List<String> values) {
            addCriterion("comment_id in", values, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdNotIn(List<String> values) {
            addCriterion("comment_id not in", values, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdBetween(String value1, String value2) {
            addCriterion("comment_id between", value1, value2, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdNotBetween(String value1, String value2) {
            addCriterion("comment_id not between", value1, value2, "commentId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
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

        public Criteria andReplayContextIsNull() {
            addCriterion("replay_context is null");
            return (Criteria) this;
        }

        public Criteria andReplayContextIsNotNull() {
            addCriterion("replay_context is not null");
            return (Criteria) this;
        }

        public Criteria andReplayContextEqualTo(String value) {
            addCriterion("replay_context =", value, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReplayContextNotEqualTo(String value) {
            addCriterion("replay_context <>", value, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReplayContextGreaterThan(String value) {
            addCriterion("replay_context >", value, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReplayContextGreaterThanOrEqualTo(String value) {
            addCriterion("replay_context >=", value, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReplayContextLessThan(String value) {
            addCriterion("replay_context <", value, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReplayContextLessThanOrEqualTo(String value) {
            addCriterion("replay_context <=", value, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReplayContextLike(String value) {
            addCriterion("replay_context like", value, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReplayContextNotLike(String value) {
            addCriterion("replay_context not like", value, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReplayContextIn(List<String> values) {
            addCriterion("replay_context in", values, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReplayContextNotIn(List<String> values) {
            addCriterion("replay_context not in", values, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReplayContextBetween(String value1, String value2) {
            addCriterion("replay_context between", value1, value2, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReplayContextNotBetween(String value1, String value2) {
            addCriterion("replay_context not between", value1, value2, "replayContext");
            return (Criteria) this;
        }

        public Criteria andReUserIdIsNull() {
            addCriterion("re_user_id is null");
            return (Criteria) this;
        }

        public Criteria andReUserIdIsNotNull() {
            addCriterion("re_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andReUserIdEqualTo(String value) {
            addCriterion("re_user_id =", value, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReUserIdNotEqualTo(String value) {
            addCriterion("re_user_id <>", value, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReUserIdGreaterThan(String value) {
            addCriterion("re_user_id >", value, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("re_user_id >=", value, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReUserIdLessThan(String value) {
            addCriterion("re_user_id <", value, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReUserIdLessThanOrEqualTo(String value) {
            addCriterion("re_user_id <=", value, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReUserIdLike(String value) {
            addCriterion("re_user_id like", value, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReUserIdNotLike(String value) {
            addCriterion("re_user_id not like", value, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReUserIdIn(List<String> values) {
            addCriterion("re_user_id in", values, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReUserIdNotIn(List<String> values) {
            addCriterion("re_user_id not in", values, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReUserIdBetween(String value1, String value2) {
            addCriterion("re_user_id between", value1, value2, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReUserIdNotBetween(String value1, String value2) {
            addCriterion("re_user_id not between", value1, value2, "reUserId");
            return (Criteria) this;
        }

        public Criteria andReNameIsNull() {
            addCriterion("re_name is null");
            return (Criteria) this;
        }

        public Criteria andReNameIsNotNull() {
            addCriterion("re_name is not null");
            return (Criteria) this;
        }

        public Criteria andReNameEqualTo(String value) {
            addCriterion("re_name =", value, "reName");
            return (Criteria) this;
        }

        public Criteria andReNameNotEqualTo(String value) {
            addCriterion("re_name <>", value, "reName");
            return (Criteria) this;
        }

        public Criteria andReNameGreaterThan(String value) {
            addCriterion("re_name >", value, "reName");
            return (Criteria) this;
        }

        public Criteria andReNameGreaterThanOrEqualTo(String value) {
            addCriterion("re_name >=", value, "reName");
            return (Criteria) this;
        }

        public Criteria andReNameLessThan(String value) {
            addCriterion("re_name <", value, "reName");
            return (Criteria) this;
        }

        public Criteria andReNameLessThanOrEqualTo(String value) {
            addCriterion("re_name <=", value, "reName");
            return (Criteria) this;
        }

        public Criteria andReNameLike(String value) {
            addCriterion("re_name like", value, "reName");
            return (Criteria) this;
        }

        public Criteria andReNameNotLike(String value) {
            addCriterion("re_name not like", value, "reName");
            return (Criteria) this;
        }

        public Criteria andReNameIn(List<String> values) {
            addCriterion("re_name in", values, "reName");
            return (Criteria) this;
        }

        public Criteria andReNameNotIn(List<String> values) {
            addCriterion("re_name not in", values, "reName");
            return (Criteria) this;
        }

        public Criteria andReNameBetween(String value1, String value2) {
            addCriterion("re_name between", value1, value2, "reName");
            return (Criteria) this;
        }

        public Criteria andReNameNotBetween(String value1, String value2) {
            addCriterion("re_name not between", value1, value2, "reName");
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

        public Criteria andUserIconIsNull() {
            addCriterion("user_icon is null");
            return (Criteria) this;
        }

        public Criteria andUserIconIsNotNull() {
            addCriterion("user_icon is not null");
            return (Criteria) this;
        }

        public Criteria andUserIconEqualTo(String value) {
            addCriterion("user_icon =", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconNotEqualTo(String value) {
            addCriterion("user_icon <>", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconGreaterThan(String value) {
            addCriterion("user_icon >", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconGreaterThanOrEqualTo(String value) {
            addCriterion("user_icon >=", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconLessThan(String value) {
            addCriterion("user_icon <", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconLessThanOrEqualTo(String value) {
            addCriterion("user_icon <=", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconLike(String value) {
            addCriterion("user_icon like", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconNotLike(String value) {
            addCriterion("user_icon not like", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconIn(List<String> values) {
            addCriterion("user_icon in", values, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconNotIn(List<String> values) {
            addCriterion("user_icon not in", values, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconBetween(String value1, String value2) {
            addCriterion("user_icon between", value1, value2, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconNotBetween(String value1, String value2) {
            addCriterion("user_icon not between", value1, value2, "userIcon");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table comment_replay
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
     * This class corresponds to the database table comment_replay
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