package com.bizwink.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LogColumnExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_COLUMN
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_COLUMN
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_COLUMN
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_COLUMN
     *
     * @mbggenerated
     */
    public LogColumnExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_COLUMN
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_COLUMN
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_COLUMN
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_COLUMN
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_COLUMN
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_COLUMN
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_COLUMN
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
     * This method corresponds to the database table TLOG_COLUMN
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
     * This method corresponds to the database table TLOG_COLUMN
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_COLUMN
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
     * This class corresponds to the database table TLOG_COLUMN
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

        public Criteria andDOMAINIDIsNull() {
            addCriterion("DOMAINID is null");
            return (Criteria) this;
        }

        public Criteria andDOMAINIDIsNotNull() {
            addCriterion("DOMAINID is not null");
            return (Criteria) this;
        }

        public Criteria andDOMAINIDEqualTo(BigDecimal value) {
            addCriterion("DOMAINID =", value, "DOMAINID");
            return (Criteria) this;
        }

        public Criteria andDOMAINIDNotEqualTo(BigDecimal value) {
            addCriterion("DOMAINID <>", value, "DOMAINID");
            return (Criteria) this;
        }

        public Criteria andDOMAINIDGreaterThan(BigDecimal value) {
            addCriterion("DOMAINID >", value, "DOMAINID");
            return (Criteria) this;
        }

        public Criteria andDOMAINIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DOMAINID >=", value, "DOMAINID");
            return (Criteria) this;
        }

        public Criteria andDOMAINIDLessThan(BigDecimal value) {
            addCriterion("DOMAINID <", value, "DOMAINID");
            return (Criteria) this;
        }

        public Criteria andDOMAINIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DOMAINID <=", value, "DOMAINID");
            return (Criteria) this;
        }

        public Criteria andDOMAINIDIn(List<BigDecimal> values) {
            addCriterion("DOMAINID in", values, "DOMAINID");
            return (Criteria) this;
        }

        public Criteria andDOMAINIDNotIn(List<BigDecimal> values) {
            addCriterion("DOMAINID not in", values, "DOMAINID");
            return (Criteria) this;
        }

        public Criteria andDOMAINIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DOMAINID between", value1, value2, "DOMAINID");
            return (Criteria) this;
        }

        public Criteria andDOMAINIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DOMAINID not between", value1, value2, "DOMAINID");
            return (Criteria) this;
        }

        public Criteria andHOSTIDIsNull() {
            addCriterion("HOSTID is null");
            return (Criteria) this;
        }

        public Criteria andHOSTIDIsNotNull() {
            addCriterion("HOSTID is not null");
            return (Criteria) this;
        }

        public Criteria andHOSTIDEqualTo(BigDecimal value) {
            addCriterion("HOSTID =", value, "HOSTID");
            return (Criteria) this;
        }

        public Criteria andHOSTIDNotEqualTo(BigDecimal value) {
            addCriterion("HOSTID <>", value, "HOSTID");
            return (Criteria) this;
        }

        public Criteria andHOSTIDGreaterThan(BigDecimal value) {
            addCriterion("HOSTID >", value, "HOSTID");
            return (Criteria) this;
        }

        public Criteria andHOSTIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("HOSTID >=", value, "HOSTID");
            return (Criteria) this;
        }

        public Criteria andHOSTIDLessThan(BigDecimal value) {
            addCriterion("HOSTID <", value, "HOSTID");
            return (Criteria) this;
        }

        public Criteria andHOSTIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("HOSTID <=", value, "HOSTID");
            return (Criteria) this;
        }

        public Criteria andHOSTIDIn(List<BigDecimal> values) {
            addCriterion("HOSTID in", values, "HOSTID");
            return (Criteria) this;
        }

        public Criteria andHOSTIDNotIn(List<BigDecimal> values) {
            addCriterion("HOSTID not in", values, "HOSTID");
            return (Criteria) this;
        }

        public Criteria andHOSTIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("HOSTID between", value1, value2, "HOSTID");
            return (Criteria) this;
        }

        public Criteria andHOSTIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("HOSTID not between", value1, value2, "HOSTID");
            return (Criteria) this;
        }

        public Criteria andCOLIDIsNull() {
            addCriterion("COLID is null");
            return (Criteria) this;
        }

        public Criteria andCOLIDIsNotNull() {
            addCriterion("COLID is not null");
            return (Criteria) this;
        }

        public Criteria andCOLIDEqualTo(BigDecimal value) {
            addCriterion("COLID =", value, "COLID");
            return (Criteria) this;
        }

        public Criteria andCOLIDNotEqualTo(BigDecimal value) {
            addCriterion("COLID <>", value, "COLID");
            return (Criteria) this;
        }

        public Criteria andCOLIDGreaterThan(BigDecimal value) {
            addCriterion("COLID >", value, "COLID");
            return (Criteria) this;
        }

        public Criteria andCOLIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COLID >=", value, "COLID");
            return (Criteria) this;
        }

        public Criteria andCOLIDLessThan(BigDecimal value) {
            addCriterion("COLID <", value, "COLID");
            return (Criteria) this;
        }

        public Criteria andCOLIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COLID <=", value, "COLID");
            return (Criteria) this;
        }

        public Criteria andCOLIDIn(List<BigDecimal> values) {
            addCriterion("COLID in", values, "COLID");
            return (Criteria) this;
        }

        public Criteria andCOLIDNotIn(List<BigDecimal> values) {
            addCriterion("COLID not in", values, "COLID");
            return (Criteria) this;
        }

        public Criteria andCOLIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COLID between", value1, value2, "COLID");
            return (Criteria) this;
        }

        public Criteria andCOLIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COLID not between", value1, value2, "COLID");
            return (Criteria) this;
        }

        public Criteria andPCOLIDIsNull() {
            addCriterion("PCOLID is null");
            return (Criteria) this;
        }

        public Criteria andPCOLIDIsNotNull() {
            addCriterion("PCOLID is not null");
            return (Criteria) this;
        }

        public Criteria andPCOLIDEqualTo(BigDecimal value) {
            addCriterion("PCOLID =", value, "PCOLID");
            return (Criteria) this;
        }

        public Criteria andPCOLIDNotEqualTo(BigDecimal value) {
            addCriterion("PCOLID <>", value, "PCOLID");
            return (Criteria) this;
        }

        public Criteria andPCOLIDGreaterThan(BigDecimal value) {
            addCriterion("PCOLID >", value, "PCOLID");
            return (Criteria) this;
        }

        public Criteria andPCOLIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PCOLID >=", value, "PCOLID");
            return (Criteria) this;
        }

        public Criteria andPCOLIDLessThan(BigDecimal value) {
            addCriterion("PCOLID <", value, "PCOLID");
            return (Criteria) this;
        }

        public Criteria andPCOLIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PCOLID <=", value, "PCOLID");
            return (Criteria) this;
        }

        public Criteria andPCOLIDIn(List<BigDecimal> values) {
            addCriterion("PCOLID in", values, "PCOLID");
            return (Criteria) this;
        }

        public Criteria andPCOLIDNotIn(List<BigDecimal> values) {
            addCriterion("PCOLID not in", values, "PCOLID");
            return (Criteria) this;
        }

        public Criteria andPCOLIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PCOLID between", value1, value2, "PCOLID");
            return (Criteria) this;
        }

        public Criteria andPCOLIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PCOLID not between", value1, value2, "PCOLID");
            return (Criteria) this;
        }

        public Criteria andCOLNAMEIsNull() {
            addCriterion("COLNAME is null");
            return (Criteria) this;
        }

        public Criteria andCOLNAMEIsNotNull() {
            addCriterion("COLNAME is not null");
            return (Criteria) this;
        }

        public Criteria andCOLNAMEEqualTo(String value) {
            addCriterion("COLNAME =", value, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLNAMENotEqualTo(String value) {
            addCriterion("COLNAME <>", value, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLNAMEGreaterThan(String value) {
            addCriterion("COLNAME >", value, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLNAMEGreaterThanOrEqualTo(String value) {
            addCriterion("COLNAME >=", value, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLNAMELessThan(String value) {
            addCriterion("COLNAME <", value, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLNAMELessThanOrEqualTo(String value) {
            addCriterion("COLNAME <=", value, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLNAMELike(String value) {
            addCriterion("COLNAME like", value, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLNAMENotLike(String value) {
            addCriterion("COLNAME not like", value, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLNAMEIn(List<String> values) {
            addCriterion("COLNAME in", values, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLNAMENotIn(List<String> values) {
            addCriterion("COLNAME not in", values, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLNAMEBetween(String value1, String value2) {
            addCriterion("COLNAME between", value1, value2, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLNAMENotBetween(String value1, String value2) {
            addCriterion("COLNAME not between", value1, value2, "COLNAME");
            return (Criteria) this;
        }

        public Criteria andCOLDIRIsNull() {
            addCriterion("COLDIR is null");
            return (Criteria) this;
        }

        public Criteria andCOLDIRIsNotNull() {
            addCriterion("COLDIR is not null");
            return (Criteria) this;
        }

        public Criteria andCOLDIREqualTo(String value) {
            addCriterion("COLDIR =", value, "COLDIR");
            return (Criteria) this;
        }

        public Criteria andCOLDIRNotEqualTo(String value) {
            addCriterion("COLDIR <>", value, "COLDIR");
            return (Criteria) this;
        }

        public Criteria andCOLDIRGreaterThan(String value) {
            addCriterion("COLDIR >", value, "COLDIR");
            return (Criteria) this;
        }

        public Criteria andCOLDIRGreaterThanOrEqualTo(String value) {
            addCriterion("COLDIR >=", value, "COLDIR");
            return (Criteria) this;
        }

        public Criteria andCOLDIRLessThan(String value) {
            addCriterion("COLDIR <", value, "COLDIR");
            return (Criteria) this;
        }

        public Criteria andCOLDIRLessThanOrEqualTo(String value) {
            addCriterion("COLDIR <=", value, "COLDIR");
            return (Criteria) this;
        }

        public Criteria andCOLDIRLike(String value) {
            addCriterion("COLDIR like", value, "COLDIR");
            return (Criteria) this;
        }

        public Criteria andCOLDIRNotLike(String value) {
            addCriterion("COLDIR not like", value, "COLDIR");
            return (Criteria) this;
        }

        public Criteria andCOLDIRIn(List<String> values) {
            addCriterion("COLDIR in", values, "COLDIR");
            return (Criteria) this;
        }

        public Criteria andCOLDIRNotIn(List<String> values) {
            addCriterion("COLDIR not in", values, "COLDIR");
            return (Criteria) this;
        }

        public Criteria andCOLDIRBetween(String value1, String value2) {
            addCriterion("COLDIR between", value1, value2, "COLDIR");
            return (Criteria) this;
        }

        public Criteria andCOLDIRNotBetween(String value1, String value2) {
            addCriterion("COLDIR not between", value1, value2, "COLDIR");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TLOG_COLUMN
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
     * This class corresponds to the database table TLOG_COLUMN
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