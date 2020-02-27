package com.bizwink.po;

import java.io.Serializable;
import java.math.BigDecimal;

public class Country implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EN_COUNTRY.ID
     *
     * @mbggenerated
     */
    private BigDecimal ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EN_COUNTRY.ORDERID
     *
     * @mbggenerated
     */
    private BigDecimal ORDERID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EN_COUNTRY.COUNTRYNAME
     *
     * @mbggenerated
     */
    private String COUNTRYNAME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EN_COUNTRY.VALID
     *
     * @mbggenerated
     */
    private BigDecimal VALID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EN_COUNTRY.CODE
     *
     * @mbggenerated
     */
    private String CODE;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EN_COUNTRY.ID
     *
     * @return the value of EN_COUNTRY.ID
     *
     * @mbggenerated
     */
    public BigDecimal getID() {
        return ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EN_COUNTRY.ID
     *
     * @param ID the value for EN_COUNTRY.ID
     *
     * @mbggenerated
     */
    public void setID(BigDecimal ID) {
        this.ID = ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EN_COUNTRY.ORDERID
     *
     * @return the value of EN_COUNTRY.ORDERID
     *
     * @mbggenerated
     */
    public BigDecimal getORDERID() {
        return ORDERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EN_COUNTRY.ORDERID
     *
     * @param ORDERID the value for EN_COUNTRY.ORDERID
     *
     * @mbggenerated
     */
    public void setORDERID(BigDecimal ORDERID) {
        this.ORDERID = ORDERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EN_COUNTRY.COUNTRYNAME
     *
     * @return the value of EN_COUNTRY.COUNTRYNAME
     *
     * @mbggenerated
     */
    public String getCOUNTRYNAME() {
        return COUNTRYNAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EN_COUNTRY.COUNTRYNAME
     *
     * @param COUNTRYNAME the value for EN_COUNTRY.COUNTRYNAME
     *
     * @mbggenerated
     */
    public void setCOUNTRYNAME(String COUNTRYNAME) {
        this.COUNTRYNAME = COUNTRYNAME == null ? null : COUNTRYNAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EN_COUNTRY.VALID
     *
     * @return the value of EN_COUNTRY.VALID
     *
     * @mbggenerated
     */
    public BigDecimal getVALID() {
        return VALID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EN_COUNTRY.VALID
     *
     * @param VALID the value for EN_COUNTRY.VALID
     *
     * @mbggenerated
     */
    public void setVALID(BigDecimal VALID) {
        this.VALID = VALID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EN_COUNTRY.CODE
     *
     * @return the value of EN_COUNTRY.CODE
     *
     * @mbggenerated
     */
    public String getCODE() {
        return CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EN_COUNTRY.CODE
     *
     * @param CODE the value for EN_COUNTRY.CODE
     *
     * @mbggenerated
     */
    public void setCODE(String CODE) {
        this.CODE = CODE == null ? null : CODE.trim();
    }
}