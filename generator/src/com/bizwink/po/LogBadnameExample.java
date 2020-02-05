package com.bizwink.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LogBadnameExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    public LogBadnameExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
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
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
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

        public Criteria andIDIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIDIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIDEqualTo(BigDecimal value) {
            addCriterion("ID =", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDNotEqualTo(BigDecimal value) {
            addCriterion("ID <>", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDGreaterThan(BigDecimal value) {
            addCriterion("ID >", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ID >=", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDLessThan(BigDecimal value) {
            addCriterion("ID <", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ID <=", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDIn(List<BigDecimal> values) {
            addCriterion("ID in", values, "ID");
            return (Criteria) this;
        }

        public Criteria andIDNotIn(List<BigDecimal> values) {
            addCriterion("ID not in", values, "ID");
            return (Criteria) this;
        }

        public Criteria andIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID between", value1, value2, "ID");
            return (Criteria) this;
        }

        public Criteria andIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID not between", value1, value2, "ID");
            return (Criteria) this;
        }

        public Criteria andCODEIsNull() {
            addCriterion("CODE is null");
            return (Criteria) this;
        }

        public Criteria andCODEIsNotNull() {
            addCriterion("CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCODEEqualTo(String value) {
            addCriterion("CODE =", value, "CODE");
            return (Criteria) this;
        }

        public Criteria andCODENotEqualTo(String value) {
            addCriterion("CODE <>", value, "CODE");
            return (Criteria) this;
        }

        public Criteria andCODEGreaterThan(String value) {
            addCriterion("CODE >", value, "CODE");
            return (Criteria) this;
        }

        public Criteria andCODEGreaterThanOrEqualTo(String value) {
            addCriterion("CODE >=", value, "CODE");
            return (Criteria) this;
        }

        public Criteria andCODELessThan(String value) {
            addCriterion("CODE <", value, "CODE");
            return (Criteria) this;
        }

        public Criteria andCODELessThanOrEqualTo(String value) {
            addCriterion("CODE <=", value, "CODE");
            return (Criteria) this;
        }

        public Criteria andCODELike(String value) {
            addCriterion("CODE like", value, "CODE");
            return (Criteria) this;
        }

        public Criteria andCODENotLike(String value) {
            addCriterion("CODE not like", value, "CODE");
            return (Criteria) this;
        }

        public Criteria andCODEIn(List<String> values) {
            addCriterion("CODE in", values, "CODE");
            return (Criteria) this;
        }

        public Criteria andCODENotIn(List<String> values) {
            addCriterion("CODE not in", values, "CODE");
            return (Criteria) this;
        }

        public Criteria andCODEBetween(String value1, String value2) {
            addCriterion("CODE between", value1, value2, "CODE");
            return (Criteria) this;
        }

        public Criteria andCODENotBetween(String value1, String value2) {
            addCriterion("CODE not between", value1, value2, "CODE");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMEIsNull() {
            addCriterion("ERR_ENAME is null");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMEIsNotNull() {
            addCriterion("ERR_ENAME is not null");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMEEqualTo(String value) {
            addCriterion("ERR_ENAME =", value, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMENotEqualTo(String value) {
            addCriterion("ERR_ENAME <>", value, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMEGreaterThan(String value) {
            addCriterion("ERR_ENAME >", value, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMEGreaterThanOrEqualTo(String value) {
            addCriterion("ERR_ENAME >=", value, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMELessThan(String value) {
            addCriterion("ERR_ENAME <", value, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMELessThanOrEqualTo(String value) {
            addCriterion("ERR_ENAME <=", value, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMELike(String value) {
            addCriterion("ERR_ENAME like", value, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMENotLike(String value) {
            addCriterion("ERR_ENAME not like", value, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMEIn(List<String> values) {
            addCriterion("ERR_ENAME in", values, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMENotIn(List<String> values) {
            addCriterion("ERR_ENAME not in", values, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMEBetween(String value1, String value2) {
            addCriterion("ERR_ENAME between", value1, value2, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_ENAMENotBetween(String value1, String value2) {
            addCriterion("ERR_ENAME not between", value1, value2, "ERR_ENAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMEIsNull() {
            addCriterion("ERR_CHNAME is null");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMEIsNotNull() {
            addCriterion("ERR_CHNAME is not null");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMEEqualTo(String value) {
            addCriterion("ERR_CHNAME =", value, "ERR_CHNAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMENotEqualTo(String value) {
            addCriterion("ERR_CHNAME <>", value, "ERR_CHNAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMEGreaterThan(String value) {
            addCriterion("ERR_CHNAME >", value, "ERR_CHNAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMEGreaterThanOrEqualTo(String value) {
            addCriterion("ERR_CHNAME >=", value, "ERR_CHNAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMELessThan(String value) {
            addCriterion("ERR_CHNAME <", value, "ERR_CHNAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMELessThanOrEqualTo(String value) {
            addCriterion("ERR_CHNAME <=", value, "ERR_CHNAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMELike(String value) {
            addCriterion("ERR_CHNAME like", value, "ERR_CHNAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMENotLike(String value) {
            addCriterion("ERR_CHNAME not like", value, "ERR_CHNAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMEIn(List<String> values) {
            addCriterion("ERR_CHNAME in", values, "ERR_CHNAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMENotIn(List<String> values) {
            addCriterion("ERR_CHNAME not in", values, "ERR_CHNAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMEBetween(String value1, String value2) {
            addCriterion("ERR_CHNAME between", value1, value2, "ERR_CHNAME");
            return (Criteria) this;
        }

        public Criteria andERR_CHNAMENotBetween(String value1, String value2) {
            addCriterion("ERR_CHNAME not between", value1, value2, "ERR_CHNAME");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
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