package com.bizwink.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogUniqueipinfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_UNIQUEIPINFO
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_UNIQUEIPINFO
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TLOG_UNIQUEIPINFO
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO
     *
     * @mbggenerated
     */
    public LogUniqueipinfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO
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
     * This method corresponds to the database table TLOG_UNIQUEIPINFO
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
     * This method corresponds to the database table TLOG_UNIQUEIPINFO
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO
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
     * This class corresponds to the database table TLOG_UNIQUEIPINFO
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

        public Criteria andIPIsNull() {
            addCriterion("IP is null");
            return (Criteria) this;
        }

        public Criteria andIPIsNotNull() {
            addCriterion("IP is not null");
            return (Criteria) this;
        }

        public Criteria andIPEqualTo(String value) {
            addCriterion("IP =", value, "IP");
            return (Criteria) this;
        }

        public Criteria andIPNotEqualTo(String value) {
            addCriterion("IP <>", value, "IP");
            return (Criteria) this;
        }

        public Criteria andIPGreaterThan(String value) {
            addCriterion("IP >", value, "IP");
            return (Criteria) this;
        }

        public Criteria andIPGreaterThanOrEqualTo(String value) {
            addCriterion("IP >=", value, "IP");
            return (Criteria) this;
        }

        public Criteria andIPLessThan(String value) {
            addCriterion("IP <", value, "IP");
            return (Criteria) this;
        }

        public Criteria andIPLessThanOrEqualTo(String value) {
            addCriterion("IP <=", value, "IP");
            return (Criteria) this;
        }

        public Criteria andIPLike(String value) {
            addCriterion("IP like", value, "IP");
            return (Criteria) this;
        }

        public Criteria andIPNotLike(String value) {
            addCriterion("IP not like", value, "IP");
            return (Criteria) this;
        }

        public Criteria andIPIn(List<String> values) {
            addCriterion("IP in", values, "IP");
            return (Criteria) this;
        }

        public Criteria andIPNotIn(List<String> values) {
            addCriterion("IP not in", values, "IP");
            return (Criteria) this;
        }

        public Criteria andIPBetween(String value1, String value2) {
            addCriterion("IP between", value1, value2, "IP");
            return (Criteria) this;
        }

        public Criteria andIPNotBetween(String value1, String value2) {
            addCriterion("IP not between", value1, value2, "IP");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMIsNull() {
            addCriterion("ACCESSNUM is null");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMIsNotNull() {
            addCriterion("ACCESSNUM is not null");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMEqualTo(BigDecimal value) {
            addCriterion("ACCESSNUM =", value, "ACCESSNUM");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMNotEqualTo(BigDecimal value) {
            addCriterion("ACCESSNUM <>", value, "ACCESSNUM");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMGreaterThan(BigDecimal value) {
            addCriterion("ACCESSNUM >", value, "ACCESSNUM");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACCESSNUM >=", value, "ACCESSNUM");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMLessThan(BigDecimal value) {
            addCriterion("ACCESSNUM <", value, "ACCESSNUM");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACCESSNUM <=", value, "ACCESSNUM");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMIn(List<BigDecimal> values) {
            addCriterion("ACCESSNUM in", values, "ACCESSNUM");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMNotIn(List<BigDecimal> values) {
            addCriterion("ACCESSNUM not in", values, "ACCESSNUM");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACCESSNUM between", value1, value2, "ACCESSNUM");
            return (Criteria) this;
        }

        public Criteria andACCESSNUMNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACCESSNUM not between", value1, value2, "ACCESSNUM");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDIsNull() {
            addCriterion("COUNTRYID is null");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDIsNotNull() {
            addCriterion("COUNTRYID is not null");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDEqualTo(BigDecimal value) {
            addCriterion("COUNTRYID =", value, "COUNTRYID");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDNotEqualTo(BigDecimal value) {
            addCriterion("COUNTRYID <>", value, "COUNTRYID");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDGreaterThan(BigDecimal value) {
            addCriterion("COUNTRYID >", value, "COUNTRYID");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COUNTRYID >=", value, "COUNTRYID");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDLessThan(BigDecimal value) {
            addCriterion("COUNTRYID <", value, "COUNTRYID");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COUNTRYID <=", value, "COUNTRYID");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDIn(List<BigDecimal> values) {
            addCriterion("COUNTRYID in", values, "COUNTRYID");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDNotIn(List<BigDecimal> values) {
            addCriterion("COUNTRYID not in", values, "COUNTRYID");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COUNTRYID between", value1, value2, "COUNTRYID");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COUNTRYID not between", value1, value2, "COUNTRYID");
            return (Criteria) this;
        }

        public Criteria andCITYIDIsNull() {
            addCriterion("CITYID is null");
            return (Criteria) this;
        }

        public Criteria andCITYIDIsNotNull() {
            addCriterion("CITYID is not null");
            return (Criteria) this;
        }

        public Criteria andCITYIDEqualTo(BigDecimal value) {
            addCriterion("CITYID =", value, "CITYID");
            return (Criteria) this;
        }

        public Criteria andCITYIDNotEqualTo(BigDecimal value) {
            addCriterion("CITYID <>", value, "CITYID");
            return (Criteria) this;
        }

        public Criteria andCITYIDGreaterThan(BigDecimal value) {
            addCriterion("CITYID >", value, "CITYID");
            return (Criteria) this;
        }

        public Criteria andCITYIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CITYID >=", value, "CITYID");
            return (Criteria) this;
        }

        public Criteria andCITYIDLessThan(BigDecimal value) {
            addCriterion("CITYID <", value, "CITYID");
            return (Criteria) this;
        }

        public Criteria andCITYIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CITYID <=", value, "CITYID");
            return (Criteria) this;
        }

        public Criteria andCITYIDIn(List<BigDecimal> values) {
            addCriterion("CITYID in", values, "CITYID");
            return (Criteria) this;
        }

        public Criteria andCITYIDNotIn(List<BigDecimal> values) {
            addCriterion("CITYID not in", values, "CITYID");
            return (Criteria) this;
        }

        public Criteria andCITYIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CITYID between", value1, value2, "CITYID");
            return (Criteria) this;
        }

        public Criteria andCITYIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CITYID not between", value1, value2, "CITYID");
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

        public Criteria andPROVIDIsNull() {
            addCriterion("PROVID is null");
            return (Criteria) this;
        }

        public Criteria andPROVIDIsNotNull() {
            addCriterion("PROVID is not null");
            return (Criteria) this;
        }

        public Criteria andPROVIDEqualTo(BigDecimal value) {
            addCriterion("PROVID =", value, "PROVID");
            return (Criteria) this;
        }

        public Criteria andPROVIDNotEqualTo(BigDecimal value) {
            addCriterion("PROVID <>", value, "PROVID");
            return (Criteria) this;
        }

        public Criteria andPROVIDGreaterThan(BigDecimal value) {
            addCriterion("PROVID >", value, "PROVID");
            return (Criteria) this;
        }

        public Criteria andPROVIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROVID >=", value, "PROVID");
            return (Criteria) this;
        }

        public Criteria andPROVIDLessThan(BigDecimal value) {
            addCriterion("PROVID <", value, "PROVID");
            return (Criteria) this;
        }

        public Criteria andPROVIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROVID <=", value, "PROVID");
            return (Criteria) this;
        }

        public Criteria andPROVIDIn(List<BigDecimal> values) {
            addCriterion("PROVID in", values, "PROVID");
            return (Criteria) this;
        }

        public Criteria andPROVIDNotIn(List<BigDecimal> values) {
            addCriterion("PROVID not in", values, "PROVID");
            return (Criteria) this;
        }

        public Criteria andPROVIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROVID between", value1, value2, "PROVID");
            return (Criteria) this;
        }

        public Criteria andPROVIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROVID not between", value1, value2, "PROVID");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIsNull() {
            addCriterion("COUNTRY is null");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIsNotNull() {
            addCriterion("COUNTRY is not null");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYEqualTo(String value) {
            addCriterion("COUNTRY =", value, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYNotEqualTo(String value) {
            addCriterion("COUNTRY <>", value, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYGreaterThan(String value) {
            addCriterion("COUNTRY >", value, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYGreaterThanOrEqualTo(String value) {
            addCriterion("COUNTRY >=", value, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYLessThan(String value) {
            addCriterion("COUNTRY <", value, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYLessThanOrEqualTo(String value) {
            addCriterion("COUNTRY <=", value, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYLike(String value) {
            addCriterion("COUNTRY like", value, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYNotLike(String value) {
            addCriterion("COUNTRY not like", value, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYIn(List<String> values) {
            addCriterion("COUNTRY in", values, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYNotIn(List<String> values) {
            addCriterion("COUNTRY not in", values, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYBetween(String value1, String value2) {
            addCriterion("COUNTRY between", value1, value2, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andCOUNTRYNotBetween(String value1, String value2) {
            addCriterion("COUNTRY not between", value1, value2, "COUNTRY");
            return (Criteria) this;
        }

        public Criteria andPROVINCEIsNull() {
            addCriterion("PROVINCE is null");
            return (Criteria) this;
        }

        public Criteria andPROVINCEIsNotNull() {
            addCriterion("PROVINCE is not null");
            return (Criteria) this;
        }

        public Criteria andPROVINCEEqualTo(String value) {
            addCriterion("PROVINCE =", value, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andPROVINCENotEqualTo(String value) {
            addCriterion("PROVINCE <>", value, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andPROVINCEGreaterThan(String value) {
            addCriterion("PROVINCE >", value, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andPROVINCEGreaterThanOrEqualTo(String value) {
            addCriterion("PROVINCE >=", value, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andPROVINCELessThan(String value) {
            addCriterion("PROVINCE <", value, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andPROVINCELessThanOrEqualTo(String value) {
            addCriterion("PROVINCE <=", value, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andPROVINCELike(String value) {
            addCriterion("PROVINCE like", value, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andPROVINCENotLike(String value) {
            addCriterion("PROVINCE not like", value, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andPROVINCEIn(List<String> values) {
            addCriterion("PROVINCE in", values, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andPROVINCENotIn(List<String> values) {
            addCriterion("PROVINCE not in", values, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andPROVINCEBetween(String value1, String value2) {
            addCriterion("PROVINCE between", value1, value2, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andPROVINCENotBetween(String value1, String value2) {
            addCriterion("PROVINCE not between", value1, value2, "PROVINCE");
            return (Criteria) this;
        }

        public Criteria andCITYIsNull() {
            addCriterion("CITY is null");
            return (Criteria) this;
        }

        public Criteria andCITYIsNotNull() {
            addCriterion("CITY is not null");
            return (Criteria) this;
        }

        public Criteria andCITYEqualTo(String value) {
            addCriterion("CITY =", value, "CITY");
            return (Criteria) this;
        }

        public Criteria andCITYNotEqualTo(String value) {
            addCriterion("CITY <>", value, "CITY");
            return (Criteria) this;
        }

        public Criteria andCITYGreaterThan(String value) {
            addCriterion("CITY >", value, "CITY");
            return (Criteria) this;
        }

        public Criteria andCITYGreaterThanOrEqualTo(String value) {
            addCriterion("CITY >=", value, "CITY");
            return (Criteria) this;
        }

        public Criteria andCITYLessThan(String value) {
            addCriterion("CITY <", value, "CITY");
            return (Criteria) this;
        }

        public Criteria andCITYLessThanOrEqualTo(String value) {
            addCriterion("CITY <=", value, "CITY");
            return (Criteria) this;
        }

        public Criteria andCITYLike(String value) {
            addCriterion("CITY like", value, "CITY");
            return (Criteria) this;
        }

        public Criteria andCITYNotLike(String value) {
            addCriterion("CITY not like", value, "CITY");
            return (Criteria) this;
        }

        public Criteria andCITYIn(List<String> values) {
            addCriterion("CITY in", values, "CITY");
            return (Criteria) this;
        }

        public Criteria andCITYNotIn(List<String> values) {
            addCriterion("CITY not in", values, "CITY");
            return (Criteria) this;
        }

        public Criteria andCITYBetween(String value1, String value2) {
            addCriterion("CITY between", value1, value2, "CITY");
            return (Criteria) this;
        }

        public Criteria andCITYNotBetween(String value1, String value2) {
            addCriterion("CITY not between", value1, value2, "CITY");
            return (Criteria) this;
        }

        public Criteria andZONEIsNull() {
            addCriterion("ZONE is null");
            return (Criteria) this;
        }

        public Criteria andZONEIsNotNull() {
            addCriterion("ZONE is not null");
            return (Criteria) this;
        }

        public Criteria andZONEEqualTo(String value) {
            addCriterion("ZONE =", value, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONENotEqualTo(String value) {
            addCriterion("ZONE <>", value, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONEGreaterThan(String value) {
            addCriterion("ZONE >", value, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONEGreaterThanOrEqualTo(String value) {
            addCriterion("ZONE >=", value, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONELessThan(String value) {
            addCriterion("ZONE <", value, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONELessThanOrEqualTo(String value) {
            addCriterion("ZONE <=", value, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONELike(String value) {
            addCriterion("ZONE like", value, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONENotLike(String value) {
            addCriterion("ZONE not like", value, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONEIn(List<String> values) {
            addCriterion("ZONE in", values, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONENotIn(List<String> values) {
            addCriterion("ZONE not in", values, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONEBetween(String value1, String value2) {
            addCriterion("ZONE between", value1, value2, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONENotBetween(String value1, String value2) {
            addCriterion("ZONE not between", value1, value2, "ZONE");
            return (Criteria) this;
        }

        public Criteria andZONEIDIsNull() {
            addCriterion("ZONEID is null");
            return (Criteria) this;
        }

        public Criteria andZONEIDIsNotNull() {
            addCriterion("ZONEID is not null");
            return (Criteria) this;
        }

        public Criteria andZONEIDEqualTo(BigDecimal value) {
            addCriterion("ZONEID =", value, "ZONEID");
            return (Criteria) this;
        }

        public Criteria andZONEIDNotEqualTo(BigDecimal value) {
            addCriterion("ZONEID <>", value, "ZONEID");
            return (Criteria) this;
        }

        public Criteria andZONEIDGreaterThan(BigDecimal value) {
            addCriterion("ZONEID >", value, "ZONEID");
            return (Criteria) this;
        }

        public Criteria andZONEIDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZONEID >=", value, "ZONEID");
            return (Criteria) this;
        }

        public Criteria andZONEIDLessThan(BigDecimal value) {
            addCriterion("ZONEID <", value, "ZONEID");
            return (Criteria) this;
        }

        public Criteria andZONEIDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZONEID <=", value, "ZONEID");
            return (Criteria) this;
        }

        public Criteria andZONEIDIn(List<BigDecimal> values) {
            addCriterion("ZONEID in", values, "ZONEID");
            return (Criteria) this;
        }

        public Criteria andZONEIDNotIn(List<BigDecimal> values) {
            addCriterion("ZONEID not in", values, "ZONEID");
            return (Criteria) this;
        }

        public Criteria andZONEIDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZONEID between", value1, value2, "ZONEID");
            return (Criteria) this;
        }

        public Criteria andZONEIDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZONEID not between", value1, value2, "ZONEID");
            return (Criteria) this;
        }

        public Criteria andNUM_IPIsNull() {
            addCriterion("NUM_IP is null");
            return (Criteria) this;
        }

        public Criteria andNUM_IPIsNotNull() {
            addCriterion("NUM_IP is not null");
            return (Criteria) this;
        }

        public Criteria andNUM_IPEqualTo(BigDecimal value) {
            addCriterion("NUM_IP =", value, "NUM_IP");
            return (Criteria) this;
        }

        public Criteria andNUM_IPNotEqualTo(BigDecimal value) {
            addCriterion("NUM_IP <>", value, "NUM_IP");
            return (Criteria) this;
        }

        public Criteria andNUM_IPGreaterThan(BigDecimal value) {
            addCriterion("NUM_IP >", value, "NUM_IP");
            return (Criteria) this;
        }

        public Criteria andNUM_IPGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NUM_IP >=", value, "NUM_IP");
            return (Criteria) this;
        }

        public Criteria andNUM_IPLessThan(BigDecimal value) {
            addCriterion("NUM_IP <", value, "NUM_IP");
            return (Criteria) this;
        }

        public Criteria andNUM_IPLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NUM_IP <=", value, "NUM_IP");
            return (Criteria) this;
        }

        public Criteria andNUM_IPIn(List<BigDecimal> values) {
            addCriterion("NUM_IP in", values, "NUM_IP");
            return (Criteria) this;
        }

        public Criteria andNUM_IPNotIn(List<BigDecimal> values) {
            addCriterion("NUM_IP not in", values, "NUM_IP");
            return (Criteria) this;
        }

        public Criteria andNUM_IPBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NUM_IP between", value1, value2, "NUM_IP");
            return (Criteria) this;
        }

        public Criteria andNUM_IPNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NUM_IP not between", value1, value2, "NUM_IP");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TLOG_UNIQUEIPINFO
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
     * This class corresponds to the database table TLOG_UNIQUEIPINFO
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