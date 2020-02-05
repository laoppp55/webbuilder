package com.bizwink.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ColumnKeywordsExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TBL_COLUMN_KEYWORDS
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TBL_COLUMN_KEYWORDS
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TBL_COLUMN_KEYWORDS
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COLUMN_KEYWORDS
     *
     * @mbggenerated
     */
    public ColumnKeywordsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COLUMN_KEYWORDS
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COLUMN_KEYWORDS
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COLUMN_KEYWORDS
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COLUMN_KEYWORDS
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COLUMN_KEYWORDS
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COLUMN_KEYWORDS
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COLUMN_KEYWORDS
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
     * This method corresponds to the database table TBL_COLUMN_KEYWORDS
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
     * This method corresponds to the database table TBL_COLUMN_KEYWORDS
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COLUMN_KEYWORDS
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
     * This class corresponds to the database table TBL_COLUMN_KEYWORDS
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

        public Criteria andSCOLUMNIDIsNull() {
            addCriterion("SCOLUMNID is null");
            return (Criteria) this;
        }

        public Criteria andSCOLUMNIDIsNotNull() {
            addCriterion("SCOLUMNID is not null");
            return (Criteria) this;
        }

        public Criteria andSCOLUMNIDEqualTo(BigDecimal value) {
            addCriterion("SCOLUMNID =", value, "SCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andSCOLUMNIDNotEqualTo(BigDecimal value) {
            addCriterion("SCOLUMNID <>", value, "SCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andSCOLUMNIDGreaterThan(BigDecimal value) {
            addCriterion("SCOLUMNID >", value, "SCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andSCOLUMNIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SCOLUMNID >=", value, "SCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andSCOLUMNIDLessThan(BigDecimal value) {
            addCriterion("SCOLUMNID <", value, "SCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andSCOLUMNIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SCOLUMNID <=", value, "SCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andSCOLUMNIDIn(List<BigDecimal> values) {
            addCriterion("SCOLUMNID in", values, "SCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andSCOLUMNIDNotIn(List<BigDecimal> values) {
            addCriterion("SCOLUMNID not in", values, "SCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andSCOLUMNIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SCOLUMNID between", value1, value2, "SCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andSCOLUMNIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SCOLUMNID not between", value1, value2, "SCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDIsNull() {
            addCriterion("TCOLUMNID is null");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDIsNotNull() {
            addCriterion("TCOLUMNID is not null");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDEqualTo(BigDecimal value) {
            addCriterion("TCOLUMNID =", value, "TCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDNotEqualTo(BigDecimal value) {
            addCriterion("TCOLUMNID <>", value, "TCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDGreaterThan(BigDecimal value) {
            addCriterion("TCOLUMNID >", value, "TCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TCOLUMNID >=", value, "TCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDLessThan(BigDecimal value) {
            addCriterion("TCOLUMNID <", value, "TCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TCOLUMNID <=", value, "TCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDIn(List<BigDecimal> values) {
            addCriterion("TCOLUMNID in", values, "TCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDNotIn(List<BigDecimal> values) {
            addCriterion("TCOLUMNID not in", values, "TCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TCOLUMNID between", value1, value2, "TCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andTCOLUMNIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TCOLUMNID not between", value1, value2, "TCOLUMNID");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSIsNull() {
            addCriterion("KEYWORDS is null");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSIsNotNull() {
            addCriterion("KEYWORDS is not null");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSEqualTo(String value) {
            addCriterion("KEYWORDS =", value, "KEYWORDS");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSNotEqualTo(String value) {
            addCriterion("KEYWORDS <>", value, "KEYWORDS");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSGreaterThan(String value) {
            addCriterion("KEYWORDS >", value, "KEYWORDS");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSGreaterThanOrEqualTo(String value) {
            addCriterion("KEYWORDS >=", value, "KEYWORDS");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSLessThan(String value) {
            addCriterion("KEYWORDS <", value, "KEYWORDS");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSLessThanOrEqualTo(String value) {
            addCriterion("KEYWORDS <=", value, "KEYWORDS");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSLike(String value) {
            addCriterion("KEYWORDS like", value, "KEYWORDS");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSNotLike(String value) {
            addCriterion("KEYWORDS not like", value, "KEYWORDS");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSIn(List<String> values) {
            addCriterion("KEYWORDS in", values, "KEYWORDS");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSNotIn(List<String> values) {
            addCriterion("KEYWORDS not in", values, "KEYWORDS");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSBetween(String value1, String value2) {
            addCriterion("KEYWORDS between", value1, value2, "KEYWORDS");
            return (Criteria) this;
        }

        public Criteria andKEYWORDSNotBetween(String value1, String value2) {
            addCriterion("KEYWORDS not between", value1, value2, "KEYWORDS");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TBL_COLUMN_KEYWORDS
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
     * This class corresponds to the database table TBL_COLUMN_KEYWORDS
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