package com.bizwink.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogBotKeywordExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    public LogBotKeywordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
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
     * This method corresponds to the database table TLOG_BOTKEYWORD
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
     * This method corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
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
     * This class corresponds to the database table TLOG_BOTKEYWORD
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

        public Criteria andBOTIDIsNull() {
            addCriterion("BOTID is null");
            return (Criteria) this;
        }

        public Criteria andBOTIDIsNotNull() {
            addCriterion("BOTID is not null");
            return (Criteria) this;
        }

        public Criteria andBOTIDEqualTo(BigDecimal value) {
            addCriterion("BOTID =", value, "BOTID");
            return (Criteria) this;
        }

        public Criteria andBOTIDNotEqualTo(BigDecimal value) {
            addCriterion("BOTID <>", value, "BOTID");
            return (Criteria) this;
        }

        public Criteria andBOTIDGreaterThan(BigDecimal value) {
            addCriterion("BOTID >", value, "BOTID");
            return (Criteria) this;
        }

        public Criteria andBOTIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BOTID >=", value, "BOTID");
            return (Criteria) this;
        }

        public Criteria andBOTIDLessThan(BigDecimal value) {
            addCriterion("BOTID <", value, "BOTID");
            return (Criteria) this;
        }

        public Criteria andBOTIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BOTID <=", value, "BOTID");
            return (Criteria) this;
        }

        public Criteria andBOTIDIn(List<BigDecimal> values) {
            addCriterion("BOTID in", values, "BOTID");
            return (Criteria) this;
        }

        public Criteria andBOTIDNotIn(List<BigDecimal> values) {
            addCriterion("BOTID not in", values, "BOTID");
            return (Criteria) this;
        }

        public Criteria andBOTIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BOTID between", value1, value2, "BOTID");
            return (Criteria) this;
        }

        public Criteria andBOTIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BOTID not between", value1, value2, "BOTID");
            return (Criteria) this;
        }

        public Criteria andBOTNAMEIsNull() {
            addCriterion("BOTNAME is null");
            return (Criteria) this;
        }

        public Criteria andBOTNAMEIsNotNull() {
            addCriterion("BOTNAME is not null");
            return (Criteria) this;
        }

        public Criteria andBOTNAMEEqualTo(String value) {
            addCriterion("BOTNAME =", value, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andBOTNAMENotEqualTo(String value) {
            addCriterion("BOTNAME <>", value, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andBOTNAMEGreaterThan(String value) {
            addCriterion("BOTNAME >", value, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andBOTNAMEGreaterThanOrEqualTo(String value) {
            addCriterion("BOTNAME >=", value, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andBOTNAMELessThan(String value) {
            addCriterion("BOTNAME <", value, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andBOTNAMELessThanOrEqualTo(String value) {
            addCriterion("BOTNAME <=", value, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andBOTNAMELike(String value) {
            addCriterion("BOTNAME like", value, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andBOTNAMENotLike(String value) {
            addCriterion("BOTNAME not like", value, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andBOTNAMEIn(List<String> values) {
            addCriterion("BOTNAME in", values, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andBOTNAMENotIn(List<String> values) {
            addCriterion("BOTNAME not in", values, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andBOTNAMEBetween(String value1, String value2) {
            addCriterion("BOTNAME between", value1, value2, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andBOTNAMENotBetween(String value1, String value2) {
            addCriterion("BOTNAME not between", value1, value2, "BOTNAME");
            return (Criteria) this;
        }

        public Criteria andKEYWORDIsNull() {
            addCriterion("KEYWORD is null");
            return (Criteria) this;
        }

        public Criteria andKEYWORDIsNotNull() {
            addCriterion("KEYWORD is not null");
            return (Criteria) this;
        }

        public Criteria andKEYWORDEqualTo(String value) {
            addCriterion("KEYWORD =", value, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andKEYWORDNotEqualTo(String value) {
            addCriterion("KEYWORD <>", value, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andKEYWORDGreaterThan(String value) {
            addCriterion("KEYWORD >", value, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andKEYWORDGreaterThanOrEqualTo(String value) {
            addCriterion("KEYWORD >=", value, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andKEYWORDLessThan(String value) {
            addCriterion("KEYWORD <", value, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andKEYWORDLessThanOrEqualTo(String value) {
            addCriterion("KEYWORD <=", value, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andKEYWORDLike(String value) {
            addCriterion("KEYWORD like", value, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andKEYWORDNotLike(String value) {
            addCriterion("KEYWORD not like", value, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andKEYWORDIn(List<String> values) {
            addCriterion("KEYWORD in", values, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andKEYWORDNotIn(List<String> values) {
            addCriterion("KEYWORD not in", values, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andKEYWORDBetween(String value1, String value2) {
            addCriterion("KEYWORD between", value1, value2, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andKEYWORDNotBetween(String value1, String value2) {
            addCriterion("KEYWORD not between", value1, value2, "KEYWORD");
            return (Criteria) this;
        }

        public Criteria andLOGDATEIsNull() {
            addCriterion("LOGDATE is null");
            return (Criteria) this;
        }

        public Criteria andLOGDATEIsNotNull() {
            addCriterion("LOGDATE is not null");
            return (Criteria) this;
        }

        public Criteria andLOGDATEEqualTo(Date value) {
            addCriterion("LOGDATE =", value, "LOGDATE");
            return (Criteria) this;
        }

        public Criteria andLOGDATENotEqualTo(Date value) {
            addCriterion("LOGDATE <>", value, "LOGDATE");
            return (Criteria) this;
        }

        public Criteria andLOGDATEGreaterThan(Date value) {
            addCriterion("LOGDATE >", value, "LOGDATE");
            return (Criteria) this;
        }

        public Criteria andLOGDATEGreaterThanOrEqualTo(Date value) {
            addCriterion("LOGDATE >=", value, "LOGDATE");
            return (Criteria) this;
        }

        public Criteria andLOGDATELessThan(Date value) {
            addCriterion("LOGDATE <", value, "LOGDATE");
            return (Criteria) this;
        }

        public Criteria andLOGDATELessThanOrEqualTo(Date value) {
            addCriterion("LOGDATE <=", value, "LOGDATE");
            return (Criteria) this;
        }

        public Criteria andLOGDATEIn(List<Date> values) {
            addCriterion("LOGDATE in", values, "LOGDATE");
            return (Criteria) this;
        }

        public Criteria andLOGDATENotIn(List<Date> values) {
            addCriterion("LOGDATE not in", values, "LOGDATE");
            return (Criteria) this;
        }

        public Criteria andLOGDATEBetween(Date value1, Date value2) {
            addCriterion("LOGDATE between", value1, value2, "LOGDATE");
            return (Criteria) this;
        }

        public Criteria andLOGDATENotBetween(Date value1, Date value2) {
            addCriterion("LOGDATE not between", value1, value2, "LOGDATE");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTIsNull() {
            addCriterion("KWCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTIsNotNull() {
            addCriterion("KWCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTEqualTo(BigDecimal value) {
            addCriterion("KWCOUNT =", value, "KWCOUNT");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTNotEqualTo(BigDecimal value) {
            addCriterion("KWCOUNT <>", value, "KWCOUNT");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTGreaterThan(BigDecimal value) {
            addCriterion("KWCOUNT >", value, "KWCOUNT");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("KWCOUNT >=", value, "KWCOUNT");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTLessThan(BigDecimal value) {
            addCriterion("KWCOUNT <", value, "KWCOUNT");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTLessThanOrEqualTo(BigDecimal value) {
            addCriterion("KWCOUNT <=", value, "KWCOUNT");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTIn(List<BigDecimal> values) {
            addCriterion("KWCOUNT in", values, "KWCOUNT");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTNotIn(List<BigDecimal> values) {
            addCriterion("KWCOUNT not in", values, "KWCOUNT");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("KWCOUNT between", value1, value2, "KWCOUNT");
            return (Criteria) this;
        }

        public Criteria andKWCOUNTNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("KWCOUNT not between", value1, value2, "KWCOUNT");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TLOG_BOTKEYWORD
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
     * This class corresponds to the database table TLOG_BOTKEYWORD
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