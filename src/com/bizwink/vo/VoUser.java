package com.bizwink.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 17-11-16.
 */
public class VoUser {
    private BigDecimal ID;
    private String USERID;
    private BigDecimal SITEID;
    private String SITENAME;
    private int SITEMANAGERFLAG;              //站点管理员标识，1站点管理员 0普通编辑
    private String NICKNAME;
    private BigDecimal SEX;
    private String EMAIL;
    private String MPHONE;
    private String PHONE;
    private BigDecimal USERTYPE;
    private Timestamp CREATEDATE;                   //用户被创建的时间
    private String COMPANY;                      //用户所属的公司名称
    private String ADDRESS;
    private String DEPARTMENT;                      //用户所属的部门名称
    private BigDecimal ORGID;                   //用户所属的组织架构ID
    private BigDecimal COMPANYID;               //用户所属的公司ID
    private BigDecimal DEPTID;                   //用户所属的部门
    private BigDecimal CREATERID;                //用户创建者ID，如果是注册用户为0
    private BigDecimal UNUSED;                    //用户删除标识，0标识用户被删除 1标识用户被使用
    private BigDecimal UNLOGIN;                   //用户被临时禁止登录 1允许用户登录 0禁止用户登录

    public BigDecimal getID() {
        return ID;
    }

    public void setID(BigDecimal ID) {
        this.ID = ID;
    }

    public String getUSERID() {
        return USERID;
    }

    public BigDecimal getSEX() {
        return SEX;
    }

    public void setSEX(BigDecimal SEX) {
        this.SEX = SEX;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public BigDecimal getSITEID() {
        return SITEID;
    }

    public void setSITEID(BigDecimal SITEID) {
        this.SITEID = SITEID;
    }

    public String getSITENAME() {
        return SITENAME;
    }

    public void setSITENAME(String SITENAME) {
        this.SITENAME = SITENAME;
    }

    public int getSITEMANAGERFLAG() {
        return SITEMANAGERFLAG;
    }

    public void setSITEMANAGERFLAG(int SITEMANAGERFLAG) {
        this.SITEMANAGERFLAG = SITEMANAGERFLAG;
    }

    public String getNICKNAME() {
        return NICKNAME;
    }

    public void setNICKNAME(String NICKNAME) {
        this.NICKNAME = NICKNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getMPHONE() {
        return MPHONE;
    }

    public void setMPHONE(String MPHONE) {
        this.MPHONE = MPHONE;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public BigDecimal getUSERTYPE() {
        return USERTYPE;
    }

    public void setUSERTYPE(BigDecimal USERTYPE) {
        this.USERTYPE = USERTYPE;
    }

    public Timestamp getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(Timestamp CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String getCOMPANY() {
        return COMPANY;
    }

    public void setCOMPANY(String COMPANY) {
        this.COMPANY = COMPANY;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }

    public BigDecimal getORGID() {
        return ORGID;
    }

    public void setORGID(BigDecimal ORGID) {
        this.ORGID = ORGID;
    }

    public BigDecimal getCOMPANYID() {
        return COMPANYID;
    }

    public void setCOMPANYID(BigDecimal COMPANYID) {
        this.COMPANYID = COMPANYID;
    }

    public BigDecimal getDEPTID() {
        return DEPTID;
    }

    public void setDEPTID(BigDecimal DEPTID) {
        this.DEPTID = DEPTID;
    }

    public BigDecimal getCREATERID() {
        return CREATERID;
    }

    public void setCREATERID(BigDecimal CREATERID) {
        this.CREATERID = CREATERID;
    }

    public BigDecimal getUNUSED() {
        return UNUSED;
    }

    public void setUNUSED(BigDecimal UNUSED) {
        this.UNUSED = UNUSED;
    }

    public BigDecimal getUNLOGIN() {
        return UNLOGIN;
    }

    public void setUNLOGIN(BigDecimal UNLOGIN) {
        this.UNLOGIN = UNLOGIN;
    }
}
