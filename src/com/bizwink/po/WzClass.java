package com.bizwink.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WzClass implements Serializable{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.ID
     *
     * @mbggenerated
     */
    private BigDecimal ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.CUSTOMERID
     *
     * @mbggenerated
     */
    private BigDecimal CUSTOMERID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.NAME
     *
     * @mbggenerated
     */
    private String NAME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.BRIEF
     *
     * @mbggenerated
     */
    private String BRIEF;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.UNIT
     *
     * @mbggenerated
     */
    private String UNIT;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.KEYWORD
     *
     * @mbggenerated
     */
    private String KEYWORD;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.CODE
     *
     * @mbggenerated
     */
    private String CODE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.PCODE
     *
     * @mbggenerated
     */
    private String PCODE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.ORDERID
     *
     * @mbggenerated
     */
    private BigDecimal ORDERID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.VALID
     *
     * @mbggenerated
     */
    private BigDecimal VALID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.DELFLAG
     *
     * @mbggenerated
     */
    private BigDecimal DELFLAG;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.CREATOR
     *
     * @mbggenerated
     */
    private String CREATOR;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.CREATEDATE
     *
     * @mbggenerated
     */
    private Date CREATEDATE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.CREATECOMP
     *
     * @mbggenerated
     */
    private BigDecimal CREATECOMP;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.EDITOR
     *
     * @mbggenerated
     */
    private String EDITOR;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.LASTUPDATE
     *
     * @mbggenerated
     */
    private Date LASTUPDATE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_WZCLASS.UPDATECOMP
     *
     * @mbggenerated
     */
    private BigDecimal UPDATECOMP;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.ID
     *
     * @return the value of TBL_WZCLASS.ID
     *
     * @mbggenerated
     */
    public BigDecimal getID() {
        return ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.ID
     *
     * @param ID the value for TBL_WZCLASS.ID
     *
     * @mbggenerated
     */
    public void setID(BigDecimal ID) {
        this.ID = ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.CUSTOMERID
     *
     * @return the value of TBL_WZCLASS.CUSTOMERID
     *
     * @mbggenerated
     */
    public BigDecimal getCUSTOMERID() {
        return CUSTOMERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.CUSTOMERID
     *
     * @param CUSTOMERID the value for TBL_WZCLASS.CUSTOMERID
     *
     * @mbggenerated
     */
    public void setCUSTOMERID(BigDecimal CUSTOMERID) {
        this.CUSTOMERID = CUSTOMERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.NAME
     *
     * @return the value of TBL_WZCLASS.NAME
     *
     * @mbggenerated
     */
    public String getNAME() {
        return NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.NAME
     *
     * @param NAME the value for TBL_WZCLASS.NAME
     *
     * @mbggenerated
     */
    public void setNAME(String NAME) {
        this.NAME = NAME == null ? null : NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.BRIEF
     *
     * @return the value of TBL_WZCLASS.BRIEF
     *
     * @mbggenerated
     */
    public String getBRIEF() {
        return BRIEF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.BRIEF
     *
     * @param BRIEF the value for TBL_WZCLASS.BRIEF
     *
     * @mbggenerated
     */
    public void setBRIEF(String BRIEF) {
        this.BRIEF = BRIEF == null ? null : BRIEF.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.UNIT
     *
     * @return the value of TBL_WZCLASS.UNIT
     *
     * @mbggenerated
     */
    public String getUNIT() {
        return UNIT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.UNIT
     *
     * @param UNIT the value for TBL_WZCLASS.UNIT
     *
     * @mbggenerated
     */
    public void setUNIT(String UNIT) {
        this.UNIT = UNIT == null ? null : UNIT.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.KEYWORD
     *
     * @return the value of TBL_WZCLASS.KEYWORD
     *
     * @mbggenerated
     */
    public String getKEYWORD() {
        return KEYWORD;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.KEYWORD
     *
     * @param KEYWORD the value for TBL_WZCLASS.KEYWORD
     *
     * @mbggenerated
     */
    public void setKEYWORD(String KEYWORD) {
        this.KEYWORD = KEYWORD == null ? null : KEYWORD.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.CODE
     *
     * @return the value of TBL_WZCLASS.CODE
     *
     * @mbggenerated
     */
    public String getCODE() {
        return CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.CODE
     *
     * @param CODE the value for TBL_WZCLASS.CODE
     *
     * @mbggenerated
     */
    public void setCODE(String CODE) {
        this.CODE = CODE == null ? null : CODE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.PCODE
     *
     * @return the value of TBL_WZCLASS.PCODE
     *
     * @mbggenerated
     */
    public String getPCODE() {
        return PCODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.PCODE
     *
     * @param PCODE the value for TBL_WZCLASS.PCODE
     *
     * @mbggenerated
     */
    public void setPCODE(String PCODE) {
        this.PCODE = PCODE == null ? null : PCODE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.ORDERID
     *
     * @return the value of TBL_WZCLASS.ORDERID
     *
     * @mbggenerated
     */
    public BigDecimal getORDERID() {
        return ORDERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.ORDERID
     *
     * @param ORDERID the value for TBL_WZCLASS.ORDERID
     *
     * @mbggenerated
     */
    public void setORDERID(BigDecimal ORDERID) {
        this.ORDERID = ORDERID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.VALID
     *
     * @return the value of TBL_WZCLASS.VALID
     *
     * @mbggenerated
     */
    public BigDecimal getVALID() {
        return VALID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.VALID
     *
     * @param VALID the value for TBL_WZCLASS.VALID
     *
     * @mbggenerated
     */
    public void setVALID(BigDecimal VALID) {
        this.VALID = VALID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.DELFLAG
     *
     * @return the value of TBL_WZCLASS.DELFLAG
     *
     * @mbggenerated
     */
    public BigDecimal getDELFLAG() {
        return DELFLAG;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.DELFLAG
     *
     * @param DELFLAG the value for TBL_WZCLASS.DELFLAG
     *
     * @mbggenerated
     */
    public void setDELFLAG(BigDecimal DELFLAG) {
        this.DELFLAG = DELFLAG;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.CREATOR
     *
     * @return the value of TBL_WZCLASS.CREATOR
     *
     * @mbggenerated
     */
    public String getCREATOR() {
        return CREATOR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.CREATOR
     *
     * @param CREATOR the value for TBL_WZCLASS.CREATOR
     *
     * @mbggenerated
     */
    public void setCREATOR(String CREATOR) {
        this.CREATOR = CREATOR == null ? null : CREATOR.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.CREATEDATE
     *
     * @return the value of TBL_WZCLASS.CREATEDATE
     *
     * @mbggenerated
     */
    public Date getCREATEDATE() {
        return CREATEDATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.CREATEDATE
     *
     * @param CREATEDATE the value for TBL_WZCLASS.CREATEDATE
     *
     * @mbggenerated
     */
    public void setCREATEDATE(Date CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.CREATECOMP
     *
     * @return the value of TBL_WZCLASS.CREATECOMP
     *
     * @mbggenerated
     */
    public BigDecimal getCREATECOMP() {
        return CREATECOMP;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.CREATECOMP
     *
     * @param CREATECOMP the value for TBL_WZCLASS.CREATECOMP
     *
     * @mbggenerated
     */
    public void setCREATECOMP(BigDecimal CREATECOMP) {
        this.CREATECOMP = CREATECOMP;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.EDITOR
     *
     * @return the value of TBL_WZCLASS.EDITOR
     *
     * @mbggenerated
     */
    public String getEDITOR() {
        return EDITOR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.EDITOR
     *
     * @param EDITOR the value for TBL_WZCLASS.EDITOR
     *
     * @mbggenerated
     */
    public void setEDITOR(String EDITOR) {
        this.EDITOR = EDITOR == null ? null : EDITOR.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.LASTUPDATE
     *
     * @return the value of TBL_WZCLASS.LASTUPDATE
     *
     * @mbggenerated
     */
    public Date getLASTUPDATE() {
        return LASTUPDATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.LASTUPDATE
     *
     * @param LASTUPDATE the value for TBL_WZCLASS.LASTUPDATE
     *
     * @mbggenerated
     */
    public void setLASTUPDATE(Date LASTUPDATE) {
        this.LASTUPDATE = LASTUPDATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_WZCLASS.UPDATECOMP
     *
     * @return the value of TBL_WZCLASS.UPDATECOMP
     *
     * @mbggenerated
     */
    public BigDecimal getUPDATECOMP() {
        return UPDATECOMP;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_WZCLASS.UPDATECOMP
     *
     * @param UPDATECOMP the value for TBL_WZCLASS.UPDATECOMP
     *
     * @mbggenerated
     */
    public void setUPDATECOMP(BigDecimal UPDATECOMP) {
        this.UPDATECOMP = UPDATECOMP;
    }
}