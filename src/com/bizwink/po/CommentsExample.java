package com.bizwink.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CommentsExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TBL_COMMENTS
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TBL_COMMENTS
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TBL_COMMENTS
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMMENTS
     *
     * @mbggenerated
     */
    public CommentsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMMENTS
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMMENTS
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMMENTS
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMMENTS
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMMENTS
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMMENTS
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMMENTS
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
     * This method corresponds to the database table TBL_COMMENTS
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
     * This method corresponds to the database table TBL_COMMENTS
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMMENTS
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
     * This class corresponds to the database table TBL_COMMENTS
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(BigDecimal value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(BigDecimal value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(BigDecimal value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(BigDecimal value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<BigDecimal> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<BigDecimal> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSiteidIsNull() {
            addCriterion("SITEID is null");
            return (Criteria) this;
        }

        public Criteria andSiteidIsNotNull() {
            addCriterion("SITEID is not null");
            return (Criteria) this;
        }

        public Criteria andSiteidEqualTo(BigDecimal value) {
            addCriterion("SITEID =", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidNotEqualTo(BigDecimal value) {
            addCriterion("SITEID <>", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidGreaterThan(BigDecimal value) {
            addCriterion("SITEID >", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SITEID >=", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidLessThan(BigDecimal value) {
            addCriterion("SITEID <", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SITEID <=", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidIn(List<BigDecimal> values) {
            addCriterion("SITEID in", values, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidNotIn(List<BigDecimal> values) {
            addCriterion("SITEID not in", values, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SITEID between", value1, value2, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SITEID not between", value1, value2, "siteid");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("USERID is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("USERID is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(BigDecimal value) {
            addCriterion("USERID =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(BigDecimal value) {
            addCriterion("USERID <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(BigDecimal value) {
            addCriterion("USERID >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("USERID >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(BigDecimal value) {
            addCriterion("USERID <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(BigDecimal value) {
            addCriterion("USERID <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<BigDecimal> values) {
            addCriterion("USERID in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<BigDecimal> values) {
            addCriterion("USERID not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("USERID between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("USERID not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andCommentsIsNull() {
            addCriterion("COMMENTS is null");
            return (Criteria) this;
        }

        public Criteria andCommentsIsNotNull() {
            addCriterion("COMMENTS is not null");
            return (Criteria) this;
        }

        public Criteria andCommentsEqualTo(String value) {
            addCriterion("COMMENTS =", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotEqualTo(String value) {
            addCriterion("COMMENTS <>", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsGreaterThan(String value) {
            addCriterion("COMMENTS >", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsGreaterThanOrEqualTo(String value) {
            addCriterion("COMMENTS >=", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsLessThan(String value) {
            addCriterion("COMMENTS <", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsLessThanOrEqualTo(String value) {
            addCriterion("COMMENTS <=", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsLike(String value) {
            addCriterion("COMMENTS like", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotLike(String value) {
            addCriterion("COMMENTS not like", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsIn(List<String> values) {
            addCriterion("COMMENTS in", values, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotIn(List<String> values) {
            addCriterion("COMMENTS not in", values, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsBetween(String value1, String value2) {
            addCriterion("COMMENTS between", value1, value2, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotBetween(String value1, String value2) {
            addCriterion("COMMENTS not between", value1, value2, "comments");
            return (Criteria) this;
        }

        public Criteria andScoresIsNull() {
            addCriterion("SCORES is null");
            return (Criteria) this;
        }

        public Criteria andScoresIsNotNull() {
            addCriterion("SCORES is not null");
            return (Criteria) this;
        }

        public Criteria andScoresEqualTo(BigDecimal value) {
            addCriterion("SCORES =", value, "scores");
            return (Criteria) this;
        }

        public Criteria andScoresNotEqualTo(BigDecimal value) {
            addCriterion("SCORES <>", value, "scores");
            return (Criteria) this;
        }

        public Criteria andScoresGreaterThan(BigDecimal value) {
            addCriterion("SCORES >", value, "scores");
            return (Criteria) this;
        }

        public Criteria andScoresGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SCORES >=", value, "scores");
            return (Criteria) this;
        }

        public Criteria andScoresLessThan(BigDecimal value) {
            addCriterion("SCORES <", value, "scores");
            return (Criteria) this;
        }

        public Criteria andScoresLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SCORES <=", value, "scores");
            return (Criteria) this;
        }

        public Criteria andScoresIn(List<BigDecimal> values) {
            addCriterion("SCORES in", values, "scores");
            return (Criteria) this;
        }

        public Criteria andScoresNotIn(List<BigDecimal> values) {
            addCriterion("SCORES not in", values, "scores");
            return (Criteria) this;
        }

        public Criteria andScoresBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SCORES between", value1, value2, "scores");
            return (Criteria) this;
        }

        public Criteria andScoresNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SCORES not between", value1, value2, "scores");
            return (Criteria) this;
        }

        public Criteria andSupportIsNull() {
            addCriterion("SUPPORT is null");
            return (Criteria) this;
        }

        public Criteria andSupportIsNotNull() {
            addCriterion("SUPPORT is not null");
            return (Criteria) this;
        }

        public Criteria andSupportEqualTo(BigDecimal value) {
            addCriterion("SUPPORT =", value, "support");
            return (Criteria) this;
        }

        public Criteria andSupportNotEqualTo(BigDecimal value) {
            addCriterion("SUPPORT <>", value, "support");
            return (Criteria) this;
        }

        public Criteria andSupportGreaterThan(BigDecimal value) {
            addCriterion("SUPPORT >", value, "support");
            return (Criteria) this;
        }

        public Criteria andSupportGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPORT >=", value, "support");
            return (Criteria) this;
        }

        public Criteria andSupportLessThan(BigDecimal value) {
            addCriterion("SUPPORT <", value, "support");
            return (Criteria) this;
        }

        public Criteria andSupportLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPORT <=", value, "support");
            return (Criteria) this;
        }

        public Criteria andSupportIn(List<BigDecimal> values) {
            addCriterion("SUPPORT in", values, "support");
            return (Criteria) this;
        }

        public Criteria andSupportNotIn(List<BigDecimal> values) {
            addCriterion("SUPPORT not in", values, "support");
            return (Criteria) this;
        }

        public Criteria andSupportBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPORT between", value1, value2, "support");
            return (Criteria) this;
        }

        public Criteria andSupportNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPORT not between", value1, value2, "support");
            return (Criteria) this;
        }

        public Criteria andCleanIsNull() {
            addCriterion("CLEAN is null");
            return (Criteria) this;
        }

        public Criteria andCleanIsNotNull() {
            addCriterion("CLEAN is not null");
            return (Criteria) this;
        }

        public Criteria andCleanEqualTo(BigDecimal value) {
            addCriterion("CLEAN =", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanNotEqualTo(BigDecimal value) {
            addCriterion("CLEAN <>", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanGreaterThan(BigDecimal value) {
            addCriterion("CLEAN >", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLEAN >=", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanLessThan(BigDecimal value) {
            addCriterion("CLEAN <", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLEAN <=", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanIn(List<BigDecimal> values) {
            addCriterion("CLEAN in", values, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanNotIn(List<BigDecimal> values) {
            addCriterion("CLEAN not in", values, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLEAN between", value1, value2, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLEAN not between", value1, value2, "clean");
            return (Criteria) this;
        }

        public Criteria andServiceIsNull() {
            addCriterion("SERVICE is null");
            return (Criteria) this;
        }

        public Criteria andServiceIsNotNull() {
            addCriterion("SERVICE is not null");
            return (Criteria) this;
        }

        public Criteria andServiceEqualTo(BigDecimal value) {
            addCriterion("SERVICE =", value, "service");
            return (Criteria) this;
        }

        public Criteria andServiceNotEqualTo(BigDecimal value) {
            addCriterion("SERVICE <>", value, "service");
            return (Criteria) this;
        }

        public Criteria andServiceGreaterThan(BigDecimal value) {
            addCriterion("SERVICE >", value, "service");
            return (Criteria) this;
        }

        public Criteria andServiceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SERVICE >=", value, "service");
            return (Criteria) this;
        }

        public Criteria andServiceLessThan(BigDecimal value) {
            addCriterion("SERVICE <", value, "service");
            return (Criteria) this;
        }

        public Criteria andServiceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SERVICE <=", value, "service");
            return (Criteria) this;
        }

        public Criteria andServiceIn(List<BigDecimal> values) {
            addCriterion("SERVICE in", values, "service");
            return (Criteria) this;
        }

        public Criteria andServiceNotIn(List<BigDecimal> values) {
            addCriterion("SERVICE not in", values, "service");
            return (Criteria) this;
        }

        public Criteria andServiceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SERVICE between", value1, value2, "service");
            return (Criteria) this;
        }

        public Criteria andServiceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SERVICE not between", value1, value2, "service");
            return (Criteria) this;
        }

        public Criteria andAdvantageIsNull() {
            addCriterion("ADVANTAGE is null");
            return (Criteria) this;
        }

        public Criteria andAdvantageIsNotNull() {
            addCriterion("ADVANTAGE is not null");
            return (Criteria) this;
        }

        public Criteria andAdvantageEqualTo(BigDecimal value) {
            addCriterion("ADVANTAGE =", value, "advantage");
            return (Criteria) this;
        }

        public Criteria andAdvantageNotEqualTo(BigDecimal value) {
            addCriterion("ADVANTAGE <>", value, "advantage");
            return (Criteria) this;
        }

        public Criteria andAdvantageGreaterThan(BigDecimal value) {
            addCriterion("ADVANTAGE >", value, "advantage");
            return (Criteria) this;
        }

        public Criteria andAdvantageGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ADVANTAGE >=", value, "advantage");
            return (Criteria) this;
        }

        public Criteria andAdvantageLessThan(BigDecimal value) {
            addCriterion("ADVANTAGE <", value, "advantage");
            return (Criteria) this;
        }

        public Criteria andAdvantageLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ADVANTAGE <=", value, "advantage");
            return (Criteria) this;
        }

        public Criteria andAdvantageIn(List<BigDecimal> values) {
            addCriterion("ADVANTAGE in", values, "advantage");
            return (Criteria) this;
        }

        public Criteria andAdvantageNotIn(List<BigDecimal> values) {
            addCriterion("ADVANTAGE not in", values, "advantage");
            return (Criteria) this;
        }

        public Criteria andAdvantageBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADVANTAGE between", value1, value2, "advantage");
            return (Criteria) this;
        }

        public Criteria andAdvantageNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADVANTAGE not between", value1, value2, "advantage");
            return (Criteria) this;
        }

        public Criteria andFoodIsNull() {
            addCriterion("FOOD is null");
            return (Criteria) this;
        }

        public Criteria andFoodIsNotNull() {
            addCriterion("FOOD is not null");
            return (Criteria) this;
        }

        public Criteria andFoodEqualTo(BigDecimal value) {
            addCriterion("FOOD =", value, "food");
            return (Criteria) this;
        }

        public Criteria andFoodNotEqualTo(BigDecimal value) {
            addCriterion("FOOD <>", value, "food");
            return (Criteria) this;
        }

        public Criteria andFoodGreaterThan(BigDecimal value) {
            addCriterion("FOOD >", value, "food");
            return (Criteria) this;
        }

        public Criteria andFoodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FOOD >=", value, "food");
            return (Criteria) this;
        }

        public Criteria andFoodLessThan(BigDecimal value) {
            addCriterion("FOOD <", value, "food");
            return (Criteria) this;
        }

        public Criteria andFoodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FOOD <=", value, "food");
            return (Criteria) this;
        }

        public Criteria andFoodIn(List<BigDecimal> values) {
            addCriterion("FOOD in", values, "food");
            return (Criteria) this;
        }

        public Criteria andFoodNotIn(List<BigDecimal> values) {
            addCriterion("FOOD not in", values, "food");
            return (Criteria) this;
        }

        public Criteria andFoodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FOOD between", value1, value2, "food");
            return (Criteria) this;
        }

        public Criteria andFoodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FOOD not between", value1, value2, "food");
            return (Criteria) this;
        }

        public Criteria andAppropriateIsNull() {
            addCriterion("APPROPRIATE is null");
            return (Criteria) this;
        }

        public Criteria andAppropriateIsNotNull() {
            addCriterion("APPROPRIATE is not null");
            return (Criteria) this;
        }

        public Criteria andAppropriateEqualTo(BigDecimal value) {
            addCriterion("APPROPRIATE =", value, "appropriate");
            return (Criteria) this;
        }

        public Criteria andAppropriateNotEqualTo(BigDecimal value) {
            addCriterion("APPROPRIATE <>", value, "appropriate");
            return (Criteria) this;
        }

        public Criteria andAppropriateGreaterThan(BigDecimal value) {
            addCriterion("APPROPRIATE >", value, "appropriate");
            return (Criteria) this;
        }

        public Criteria andAppropriateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("APPROPRIATE >=", value, "appropriate");
            return (Criteria) this;
        }

        public Criteria andAppropriateLessThan(BigDecimal value) {
            addCriterion("APPROPRIATE <", value, "appropriate");
            return (Criteria) this;
        }

        public Criteria andAppropriateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("APPROPRIATE <=", value, "appropriate");
            return (Criteria) this;
        }

        public Criteria andAppropriateIn(List<BigDecimal> values) {
            addCriterion("APPROPRIATE in", values, "appropriate");
            return (Criteria) this;
        }

        public Criteria andAppropriateNotIn(List<BigDecimal> values) {
            addCriterion("APPROPRIATE not in", values, "appropriate");
            return (Criteria) this;
        }

        public Criteria andAppropriateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPROPRIATE between", value1, value2, "appropriate");
            return (Criteria) this;
        }

        public Criteria andAppropriateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPROPRIATE not between", value1, value2, "appropriate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNull() {
            addCriterion("CREATEDATE is null");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNotNull() {
            addCriterion("CREATEDATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedateEqualTo(Date value) {
            addCriterionForJDBCDate("CREATEDATE =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(Date value) {
            addCriterionForJDBCDate("CREATEDATE <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(Date value) {
            addCriterionForJDBCDate("CREATEDATE >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CREATEDATE >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(Date value) {
            addCriterionForJDBCDate("CREATEDATE <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CREATEDATE <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<Date> values) {
            addCriterionForJDBCDate("CREATEDATE in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<Date> values) {
            addCriterionForJDBCDate("CREATEDATE not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREATEDATE between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREATEDATE not between", value1, value2, "createdate");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TBL_COMMENTS
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
     * This class corresponds to the database table TBL_COMMENTS
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