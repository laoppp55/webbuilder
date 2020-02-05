package com.bizwink.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DatepriceExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    public DatepriceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
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
     * This method corresponds to the database table CS_DATE_PRICE
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
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
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
     * This class corresponds to the database table CS_DATE_PRICE
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

        public Criteria andRoomIdIsNull() {
            addCriterion("ROOM_ID is null");
            return (Criteria) this;
        }

        public Criteria andRoomIdIsNotNull() {
            addCriterion("ROOM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRoomIdEqualTo(BigDecimal value) {
            addCriterion("ROOM_ID =", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotEqualTo(BigDecimal value) {
            addCriterion("ROOM_ID <>", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdGreaterThan(BigDecimal value) {
            addCriterion("ROOM_ID >", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ROOM_ID >=", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdLessThan(BigDecimal value) {
            addCriterion("ROOM_ID <", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ROOM_ID <=", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdIn(List<BigDecimal> values) {
            addCriterion("ROOM_ID in", values, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotIn(List<BigDecimal> values) {
            addCriterion("ROOM_ID not in", values, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROOM_ID between", value1, value2, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROOM_ID not between", value1, value2, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomPriceIsNull() {
            addCriterion("ROOM_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andRoomPriceIsNotNull() {
            addCriterion("ROOM_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andRoomPriceEqualTo(BigDecimal value) {
            addCriterion("ROOM_PRICE =", value, "roomPrice");
            return (Criteria) this;
        }

        public Criteria andRoomPriceNotEqualTo(BigDecimal value) {
            addCriterion("ROOM_PRICE <>", value, "roomPrice");
            return (Criteria) this;
        }

        public Criteria andRoomPriceGreaterThan(BigDecimal value) {
            addCriterion("ROOM_PRICE >", value, "roomPrice");
            return (Criteria) this;
        }

        public Criteria andRoomPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ROOM_PRICE >=", value, "roomPrice");
            return (Criteria) this;
        }

        public Criteria andRoomPriceLessThan(BigDecimal value) {
            addCriterion("ROOM_PRICE <", value, "roomPrice");
            return (Criteria) this;
        }

        public Criteria andRoomPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ROOM_PRICE <=", value, "roomPrice");
            return (Criteria) this;
        }

        public Criteria andRoomPriceIn(List<BigDecimal> values) {
            addCriterion("ROOM_PRICE in", values, "roomPrice");
            return (Criteria) this;
        }

        public Criteria andRoomPriceNotIn(List<BigDecimal> values) {
            addCriterion("ROOM_PRICE not in", values, "roomPrice");
            return (Criteria) this;
        }

        public Criteria andRoomPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROOM_PRICE between", value1, value2, "roomPrice");
            return (Criteria) this;
        }

        public Criteria andRoomPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROOM_PRICE not between", value1, value2, "roomPrice");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceIsNull() {
            addCriterion("ROOMSPECIALPRICE is null");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceIsNotNull() {
            addCriterion("ROOMSPECIALPRICE is not null");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceEqualTo(BigDecimal value) {
            addCriterion("ROOMSPECIALPRICE =", value, "roomspecialprice");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceNotEqualTo(BigDecimal value) {
            addCriterion("ROOMSPECIALPRICE <>", value, "roomspecialprice");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceGreaterThan(BigDecimal value) {
            addCriterion("ROOMSPECIALPRICE >", value, "roomspecialprice");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ROOMSPECIALPRICE >=", value, "roomspecialprice");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceLessThan(BigDecimal value) {
            addCriterion("ROOMSPECIALPRICE <", value, "roomspecialprice");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ROOMSPECIALPRICE <=", value, "roomspecialprice");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceIn(List<BigDecimal> values) {
            addCriterion("ROOMSPECIALPRICE in", values, "roomspecialprice");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceNotIn(List<BigDecimal> values) {
            addCriterion("ROOMSPECIALPRICE not in", values, "roomspecialprice");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROOMSPECIALPRICE between", value1, value2, "roomspecialprice");
            return (Criteria) this;
        }

        public Criteria andRoomspecialpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROOMSPECIALPRICE not between", value1, value2, "roomspecialprice");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceIsNull() {
            addCriterion("ROOMLONGTIMEPRICE is null");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceIsNotNull() {
            addCriterion("ROOMLONGTIMEPRICE is not null");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceEqualTo(BigDecimal value) {
            addCriterion("ROOMLONGTIMEPRICE =", value, "roomlongtimeprice");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceNotEqualTo(BigDecimal value) {
            addCriterion("ROOMLONGTIMEPRICE <>", value, "roomlongtimeprice");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceGreaterThan(BigDecimal value) {
            addCriterion("ROOMLONGTIMEPRICE >", value, "roomlongtimeprice");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ROOMLONGTIMEPRICE >=", value, "roomlongtimeprice");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceLessThan(BigDecimal value) {
            addCriterion("ROOMLONGTIMEPRICE <", value, "roomlongtimeprice");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ROOMLONGTIMEPRICE <=", value, "roomlongtimeprice");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceIn(List<BigDecimal> values) {
            addCriterion("ROOMLONGTIMEPRICE in", values, "roomlongtimeprice");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceNotIn(List<BigDecimal> values) {
            addCriterion("ROOMLONGTIMEPRICE not in", values, "roomlongtimeprice");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROOMLONGTIMEPRICE between", value1, value2, "roomlongtimeprice");
            return (Criteria) this;
        }

        public Criteria andRoomlongtimepriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROOMLONGTIMEPRICE not between", value1, value2, "roomlongtimeprice");
            return (Criteria) this;
        }

        public Criteria andBedPriceIsNull() {
            addCriterion("BED_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andBedPriceIsNotNull() {
            addCriterion("BED_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andBedPriceEqualTo(BigDecimal value) {
            addCriterion("BED_PRICE =", value, "bedPrice");
            return (Criteria) this;
        }

        public Criteria andBedPriceNotEqualTo(BigDecimal value) {
            addCriterion("BED_PRICE <>", value, "bedPrice");
            return (Criteria) this;
        }

        public Criteria andBedPriceGreaterThan(BigDecimal value) {
            addCriterion("BED_PRICE >", value, "bedPrice");
            return (Criteria) this;
        }

        public Criteria andBedPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BED_PRICE >=", value, "bedPrice");
            return (Criteria) this;
        }

        public Criteria andBedPriceLessThan(BigDecimal value) {
            addCriterion("BED_PRICE <", value, "bedPrice");
            return (Criteria) this;
        }

        public Criteria andBedPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BED_PRICE <=", value, "bedPrice");
            return (Criteria) this;
        }

        public Criteria andBedPriceIn(List<BigDecimal> values) {
            addCriterion("BED_PRICE in", values, "bedPrice");
            return (Criteria) this;
        }

        public Criteria andBedPriceNotIn(List<BigDecimal> values) {
            addCriterion("BED_PRICE not in", values, "bedPrice");
            return (Criteria) this;
        }

        public Criteria andBedPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BED_PRICE between", value1, value2, "bedPrice");
            return (Criteria) this;
        }

        public Criteria andBedPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BED_PRICE not between", value1, value2, "bedPrice");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceIsNull() {
            addCriterion("BEDSPECIALPRICE is null");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceIsNotNull() {
            addCriterion("BEDSPECIALPRICE is not null");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceEqualTo(BigDecimal value) {
            addCriterion("BEDSPECIALPRICE =", value, "bedspecialprice");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceNotEqualTo(BigDecimal value) {
            addCriterion("BEDSPECIALPRICE <>", value, "bedspecialprice");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceGreaterThan(BigDecimal value) {
            addCriterion("BEDSPECIALPRICE >", value, "bedspecialprice");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BEDSPECIALPRICE >=", value, "bedspecialprice");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceLessThan(BigDecimal value) {
            addCriterion("BEDSPECIALPRICE <", value, "bedspecialprice");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BEDSPECIALPRICE <=", value, "bedspecialprice");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceIn(List<BigDecimal> values) {
            addCriterion("BEDSPECIALPRICE in", values, "bedspecialprice");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceNotIn(List<BigDecimal> values) {
            addCriterion("BEDSPECIALPRICE not in", values, "bedspecialprice");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BEDSPECIALPRICE between", value1, value2, "bedspecialprice");
            return (Criteria) this;
        }

        public Criteria andBedspecialpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BEDSPECIALPRICE not between", value1, value2, "bedspecialprice");
            return (Criteria) this;
        }

        public Criteria andThedateIsNull() {
            addCriterion("THEDATE is null");
            return (Criteria) this;
        }

        public Criteria andThedateIsNotNull() {
            addCriterion("THEDATE is not null");
            return (Criteria) this;
        }

        public Criteria andThedateEqualTo(Date value) {
            addCriterionForJDBCDate("THEDATE =", value, "thedate");
            return (Criteria) this;
        }

        public Criteria andThedateNotEqualTo(Date value) {
            addCriterionForJDBCDate("THEDATE <>", value, "thedate");
            return (Criteria) this;
        }

        public Criteria andThedateGreaterThan(Date value) {
            addCriterionForJDBCDate("THEDATE >", value, "thedate");
            return (Criteria) this;
        }

        public Criteria andThedateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("THEDATE >=", value, "thedate");
            return (Criteria) this;
        }

        public Criteria andThedateLessThan(Date value) {
            addCriterionForJDBCDate("THEDATE <", value, "thedate");
            return (Criteria) this;
        }

        public Criteria andThedateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("THEDATE <=", value, "thedate");
            return (Criteria) this;
        }

        public Criteria andThedateIn(List<Date> values) {
            addCriterionForJDBCDate("THEDATE in", values, "thedate");
            return (Criteria) this;
        }

        public Criteria andThedateNotIn(List<Date> values) {
            addCriterionForJDBCDate("THEDATE not in", values, "thedate");
            return (Criteria) this;
        }

        public Criteria andThedateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("THEDATE between", value1, value2, "thedate");
            return (Criteria) this;
        }

        public Criteria andThedateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("THEDATE not between", value1, value2, "thedate");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table CS_DATE_PRICE
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
     * This class corresponds to the database table CS_DATE_PRICE
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