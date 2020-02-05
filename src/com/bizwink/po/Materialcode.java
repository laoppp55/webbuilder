package com.bizwink.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class Materialcode  implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.ID
     *
     * @mbggenerated
     */
    private BigDecimal ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.PID
     *
     * @mbggenerated
     */
    private BigDecimal PID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.CUSTOMERID
     *
     * @mbggenerated
     */
    private BigDecimal CUSTOMERID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.NAME
     *
     * @mbggenerated
     */
    private String NAME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.CODE
     *
     * @mbggenerated
     */
    private String CODE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.PCODE
     *
     * @mbggenerated
     */
    private String PCODE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.ORDERID
     *
     * @mbggenerated
     */
    private BigDecimal ORDERID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.VALID
     *
     * @mbggenerated
     */
    private BigDecimal VALID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.CREATOR
     *
     * @mbggenerated
     */
    private String CREATOR;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.CREATEDATE
     *
     * @mbggenerated
     */
    private Timestamp CREATEDATE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.EDITOR
     *
     * @mbggenerated
     */
    private String EDITOR;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_MATERIAL_CODE.LASTUPDATE
     *
     * @mbggenerated
     */
    private Timestamp LASTUPDATE;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.ID
     *
     * @return the value of TBL_MATERIAL_CODE.ID
     *
     * @mbggenerated
     */
    public BigDecimal getID() {
        return ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.ID
     *
     * @param ID the value for TBL_MATERIAL_CODE.ID
     *
     * @mbggenerated
     */
    public void setID(BigDecimal ID) {
        this.ID = ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.PID
     *
     * @return the value of TBL_MATERIAL_CODE.PID
     *
     * @mbggenerated
     */
    public BigDecimal getPID() {
        return PID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.PID
     *
     * @param PID the value for TBL_MATERIAL_CODE.PID
     *
     * @mbggenerated
     */
    public void setPID(BigDecimal PID) {
        this.PID = PID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.CUSTOMERID
     *
     * @return the value of TBL_MATERIAL_CODE.CUSTOMERID
     *
     * @mbggenerated
     */
    public BigDecimal getCUSTOMERID() {
        return CUSTOMERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.CUSTOMERID
     *
     * @param CUSTOMERID the value for TBL_MATERIAL_CODE.CUSTOMERID
     *
     * @mbggenerated
     */
    public void setCUSTOMERID(BigDecimal CUSTOMERID) {
        this.CUSTOMERID = CUSTOMERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.NAME
     *
     * @return the value of TBL_MATERIAL_CODE.NAME
     *
     * @mbggenerated
     */
    public String getNAME() {
        return NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.NAME
     *
     * @param NAME the value for TBL_MATERIAL_CODE.NAME
     *
     * @mbggenerated
     */
    public void setNAME(String NAME) {
        this.NAME = NAME == null ? null : NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.CODE
     *
     * @return the value of TBL_MATERIAL_CODE.CODE
     *
     * @mbggenerated
     */
    public String getCODE() {
        return CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.CODE
     *
     * @param CODE the value for TBL_MATERIAL_CODE.CODE
     *
     * @mbggenerated
     */
    public void setCODE(String CODE) {
        this.CODE = CODE == null ? null : CODE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.PCODE
     *
     * @return the value of TBL_MATERIAL_CODE.PCODE
     *
     * @mbggenerated
     */
    public String getPCODE() {
        return PCODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.PCODE
     *
     * @param PCODE the value for TBL_MATERIAL_CODE.PCODE
     *
     * @mbggenerated
     */
    public void setPCODE(String PCODE) {
        this.PCODE = PCODE == null ? null : PCODE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.ORDERID
     *
     * @return the value of TBL_MATERIAL_CODE.ORDERID
     *
     * @mbggenerated
     */
    public BigDecimal getORDERID() {
        return ORDERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.ORDERID
     *
     * @param ORDERID the value for TBL_MATERIAL_CODE.ORDERID
     *
     * @mbggenerated
     */
    public void setORDERID(BigDecimal ORDERID) {
        this.ORDERID = ORDERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.VALID
     *
     * @return the value of TBL_MATERIAL_CODE.VALID
     *
     * @mbggenerated
     */
    public BigDecimal getVALID() {
        return VALID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.VALID
     *
     * @param VALID the value for TBL_MATERIAL_CODE.VALID
     *
     * @mbggenerated
     */
    public void setVALID(BigDecimal VALID) {
        this.VALID = VALID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.CREATOR
     *
     * @return the value of TBL_MATERIAL_CODE.CREATOR
     *
     * @mbggenerated
     */
    public String getCREATOR() {
        return CREATOR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.CREATOR
     *
     * @param CREATOR the value for TBL_MATERIAL_CODE.CREATOR
     *
     * @mbggenerated
     */
    public void setCREATOR(String CREATOR) {
        this.CREATOR = CREATOR == null ? null : CREATOR.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.CREATEDATE
     *
     * @return the value of TBL_MATERIAL_CODE.CREATEDATE
     *
     * @mbggenerated
     */
    public Timestamp getCREATEDATE() {
        return CREATEDATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.CREATEDATE
     *
     * @param CREATEDATE the value for TBL_MATERIAL_CODE.CREATEDATE
     *
     * @mbggenerated
     */
    public void setCREATEDATE(Timestamp CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.EDITOR
     *
     * @return the value of TBL_MATERIAL_CODE.EDITOR
     *
     * @mbggenerated
     */
    public String getEDITOR() {
        return EDITOR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.EDITOR
     *
     * @param EDITOR the value for TBL_MATERIAL_CODE.EDITOR
     *
     * @mbggenerated
     */
    public void setEDITOR(String EDITOR) {
        this.EDITOR = EDITOR == null ? null : EDITOR.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_MATERIAL_CODE.LASTUPDATE
     *
     * @return the value of TBL_MATERIAL_CODE.LASTUPDATE
     *
     * @mbggenerated
     */
    public Timestamp getLASTUPDATE() {
        return LASTUPDATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_MATERIAL_CODE.LASTUPDATE
     *
     * @param LASTUPDATE the value for TBL_MATERIAL_CODE.LASTUPDATE
     *
     * @mbggenerated
     */
    public void setLASTUPDATE(Timestamp LASTUPDATE) {
        this.LASTUPDATE = LASTUPDATE;
    }
}