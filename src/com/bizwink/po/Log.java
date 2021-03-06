package com.bizwink.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class Log  implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_LOG.ID
     *
     * @mbggenerated
     */
    private BigDecimal ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_LOG.ARTICLEID
     *
     * @mbggenerated
     */
    private BigDecimal ARTICLEID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_LOG.SITEID
     *
     * @mbggenerated
     */
    private BigDecimal SITEID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_LOG.EDITOR
     *
     * @mbggenerated
     */
    private String EDITOR;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_LOG.ACTTYPE
     *
     * @mbggenerated
     */
    private Short ACTTYPE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_LOG.ACTTIME
     *
     * @mbggenerated
     */
    private Timestamp ACTTIME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_LOG.COLUMNID
     *
     * @mbggenerated
     */
    private BigDecimal COLUMNID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_LOG.MAINTITLE
     *
     * @mbggenerated
     */
    private String MAINTITLE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_LOG.CREATEDATE
     *
     * @mbggenerated
     */
    private Timestamp CREATEDATE;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_LOG.ID
     *
     * @return the value of TBL_LOG.ID
     *
     * @mbggenerated
     */
    public BigDecimal getID() {
        return ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_LOG.ID
     *
     * @param ID the value for TBL_LOG.ID
     *
     * @mbggenerated
     */
    public void setID(BigDecimal ID) {
        this.ID = ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_LOG.ARTICLEID
     *
     * @return the value of TBL_LOG.ARTICLEID
     *
     * @mbggenerated
     */
    public BigDecimal getARTICLEID() {
        return ARTICLEID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_LOG.ARTICLEID
     *
     * @param ARTICLEID the value for TBL_LOG.ARTICLEID
     *
     * @mbggenerated
     */
    public void setARTICLEID(BigDecimal ARTICLEID) {
        this.ARTICLEID = ARTICLEID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_LOG.SITEID
     *
     * @return the value of TBL_LOG.SITEID
     *
     * @mbggenerated
     */
    public BigDecimal getSITEID() {
        return SITEID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_LOG.SITEID
     *
     * @param SITEID the value for TBL_LOG.SITEID
     *
     * @mbggenerated
     */
    public void setSITEID(BigDecimal SITEID) {
        this.SITEID = SITEID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_LOG.EDITOR
     *
     * @return the value of TBL_LOG.EDITOR
     *
     * @mbggenerated
     */
    public String getEDITOR() {
        return EDITOR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_LOG.EDITOR
     *
     * @param EDITOR the value for TBL_LOG.EDITOR
     *
     * @mbggenerated
     */
    public void setEDITOR(String EDITOR) {
        this.EDITOR = EDITOR == null ? null : EDITOR.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_LOG.ACTTYPE
     *
     * @return the value of TBL_LOG.ACTTYPE
     *
     * @mbggenerated
     */
    public Short getACTTYPE() {
        return ACTTYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_LOG.ACTTYPE
     *
     * @param ACTTYPE the value for TBL_LOG.ACTTYPE
     *
     * @mbggenerated
     */
    public void setACTTYPE(Short ACTTYPE) {
        this.ACTTYPE = ACTTYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_LOG.ACTTIME
     *
     * @return the value of TBL_LOG.ACTTIME
     *
     * @mbggenerated
     */
    public Timestamp getACTTIME() {
        return ACTTIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_LOG.ACTTIME
     *
     * @param ACTTIME the value for TBL_LOG.ACTTIME
     *
     * @mbggenerated
     */
    public void setACTTIME(Timestamp ACTTIME) {
        this.ACTTIME = ACTTIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_LOG.COLUMNID
     *
     * @return the value of TBL_LOG.COLUMNID
     *
     * @mbggenerated
     */
    public BigDecimal getCOLUMNID() {
        return COLUMNID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_LOG.COLUMNID
     *
     * @param COLUMNID the value for TBL_LOG.COLUMNID
     *
     * @mbggenerated
     */
    public void setCOLUMNID(BigDecimal COLUMNID) {
        this.COLUMNID = COLUMNID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_LOG.MAINTITLE
     *
     * @return the value of TBL_LOG.MAINTITLE
     *
     * @mbggenerated
     */
    public String getMAINTITLE() {
        return MAINTITLE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_LOG.MAINTITLE
     *
     * @param MAINTITLE the value for TBL_LOG.MAINTITLE
     *
     * @mbggenerated
     */
    public void setMAINTITLE(String MAINTITLE) {
        this.MAINTITLE = MAINTITLE == null ? null : MAINTITLE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_LOG.CREATEDATE
     *
     * @return the value of TBL_LOG.CREATEDATE
     *
     * @mbggenerated
     */
    public Timestamp getCREATEDATE() {
        return CREATEDATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_LOG.CREATEDATE
     *
     * @param CREATEDATE the value for TBL_LOG.CREATEDATE
     *
     * @mbggenerated
     */
    public void setCREATEDATE(Timestamp CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }
}