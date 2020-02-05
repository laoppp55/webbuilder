package com.bizwink.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class OrdersDetail implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.ID
     *
     * @mbggenerated
     */
    private long ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.ORDERID
     *
     * @mbggenerated
     */
    private BigDecimal ORDERID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.SUPPLIERID
     *
     * @mbggenerated
     */
    private BigDecimal SUPPLIERID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.SUPPLIERNAME
     *
     * @mbggenerated
     */
    private String SUPPLIERNAME;

    private BigDecimal BUYERID;
    private String BUYERNAME;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.PRODUCTID
     *
     * @mbggenerated
     */
    private BigDecimal PRODUCTID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.ORDERNUM
     *
     * @mbggenerated
     */
    private BigDecimal ORDERNUM;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.SALEPRICE
     *
     * @mbggenerated
     */
    private BigDecimal SALEPRICE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.WEIGHT
     *
     * @mbggenerated
     */
    private BigDecimal WEIGHT;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.CREATEDATE
     *
     * @mbggenerated
     */
    private Timestamp CREATEDATE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.PRODUCTNAME
     *
     * @mbggenerated
     */
    private String PRODUCTNAME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.TYPE
     *
     * @mbggenerated
     */
    private String TYPE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_ORDERS_DETAIL.CARDID
     *
     * @mbggenerated
     */
    private BigDecimal CARDID;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.ID
     *
     * @return the value of TBL_ORDERS_DETAIL.ID
     *
     * @mbggenerated
     */
    public long getID() {
        return ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.ID
     *
     * @param:ID the value for TBL_ORDERS_DETAIL.ID
     *
     * @mbggenerated
     */
    public void setID(long ID) {
        this.ID = ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.ORDERID
     *
     * @return the value of TBL_ORDERS_DETAIL.ORDERID
     *
     * @mbggenerated
     */
    public BigDecimal getORDERID() {
        return ORDERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.ORDERID
     *
     * @param ORDERID the value for TBL_ORDERS_DETAIL.ORDERID
     *
     * @mbggenerated
     */
    public void setORDERID(BigDecimal ORDERID) {
        this.ORDERID = ORDERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.SUPPLIERID
     *
     * @return the value of TBL_ORDERS_DETAIL.SUPPLIERID
     *
     * @mbggenerated
     */
    public BigDecimal getSUPPLIERID() {
        return SUPPLIERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.SUPPLIERID
     *
     * @param SUPPLIERID the value for TBL_ORDERS_DETAIL.SUPPLIERID
     *
     * @mbggenerated
     */
    public void setSUPPLIERID(BigDecimal SUPPLIERID) {
        this.SUPPLIERID = SUPPLIERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.SUPPLIERNAME
     *
     * @return the value of TBL_ORDERS_DETAIL.SUPPLIERNAME
     *
     * @mbggenerated
     */
    public String getSUPPLIERNAME() {
        return SUPPLIERNAME;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.SUPPLIERNAME
     *
     * @param SUPPLIERNAME the value for TBL_ORDERS_DETAIL.SUPPLIERNAME
     *
     * @mbggenerated
     */
    public void setSUPPLIERNAME(String SUPPLIERNAME) {
        this.SUPPLIERNAME = SUPPLIERNAME == null ? null : SUPPLIERNAME.trim();
    }

    public BigDecimal getBUYERID() {
        return BUYERID;
    }

    public void setBUYERID(BigDecimal BUYERID) {
        this.BUYERID = BUYERID;
    }

    public String getBUYERNAME() {
        return BUYERNAME;
    }

    public void setBUYERNAME(String BUYERNAME) {
        this.BUYERNAME = BUYERNAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.PRODUCTID
     *
     * @return the value of TBL_ORDERS_DETAIL.PRODUCTID
     *
     * @mbggenerated
     */
    public BigDecimal getPRODUCTID() {
        return PRODUCTID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.PRODUCTID
     *
     * @param PRODUCTID the value for TBL_ORDERS_DETAIL.PRODUCTID
     *
     * @mbggenerated
     */
    public void setPRODUCTID(BigDecimal PRODUCTID) {
        this.PRODUCTID = PRODUCTID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.ORDERNUM
     *
     * @return the value of TBL_ORDERS_DETAIL.ORDERNUM
     *
     * @mbggenerated
     */
    public BigDecimal getORDERNUM() {
        return ORDERNUM;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.ORDERNUM
     *
     * @param ORDERNUM the value for TBL_ORDERS_DETAIL.ORDERNUM
     *
     * @mbggenerated
     */
    public void setORDERNUM(BigDecimal ORDERNUM) {
        this.ORDERNUM = ORDERNUM;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.SALEPRICE
     *
     * @return the value of TBL_ORDERS_DETAIL.SALEPRICE
     *
     * @mbggenerated
     */
    public BigDecimal getSALEPRICE() {
        return SALEPRICE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.SALEPRICE
     *
     * @param SALEPRICE the value for TBL_ORDERS_DETAIL.SALEPRICE
     *
     * @mbggenerated
     */
    public void setSALEPRICE(BigDecimal SALEPRICE) {
        this.SALEPRICE = SALEPRICE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.WEIGHT
     *
     * @return the value of TBL_ORDERS_DETAIL.WEIGHT
     *
     * @mbggenerated
     */
    public BigDecimal getWEIGHT() {
        return WEIGHT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.WEIGHT
     *
     * @param WEIGHT the value for TBL_ORDERS_DETAIL.WEIGHT
     *
     * @mbggenerated
     */
    public void setWEIGHT(BigDecimal WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.CREATEDATE
     *
     * @return the value of TBL_ORDERS_DETAIL.CREATEDATE
     *
     * @mbggenerated
     */
    public Timestamp getCREATEDATE() {
        return CREATEDATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.CREATEDATE
     *
     * @param CREATEDATE the value for TBL_ORDERS_DETAIL.CREATEDATE
     *
     * @mbggenerated
     */
    public void setCREATEDATE(Timestamp CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.PRODUCTNAME
     *
     * @return the value of TBL_ORDERS_DETAIL.PRODUCTNAME
     *
     * @mbggenerated
     */
    public String getPRODUCTNAME() {
        return PRODUCTNAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.PRODUCTNAME
     *
     * @param PRODUCTNAME the value for TBL_ORDERS_DETAIL.PRODUCTNAME
     *
     * @mbggenerated
     */
    public void setPRODUCTNAME(String PRODUCTNAME) {
        this.PRODUCTNAME = PRODUCTNAME == null ? null : PRODUCTNAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.TYPE
     *
     * @return the value of TBL_ORDERS_DETAIL.TYPE
     *
     * @mbggenerated
     */
    public String getTYPE() {
        return TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.TYPE
     *
     * @param TYPE the value for TBL_ORDERS_DETAIL.TYPE
     *
     * @mbggenerated
     */
    public void setTYPE(String TYPE) {
        this.TYPE = TYPE == null ? null : TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_ORDERS_DETAIL.CARDID
     *
     * @return the value of TBL_ORDERS_DETAIL.CARDID
     *
     * @mbggenerated
     */
    public BigDecimal getCARDID() {
        return CARDID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_ORDERS_DETAIL.CARDID
     *
     * @param CARDID the value for TBL_ORDERS_DETAIL.CARDID
     *
     * @mbggenerated
     */
    public void setCARDID(BigDecimal CARDID) {
        this.CARDID = CARDID;
    }
}