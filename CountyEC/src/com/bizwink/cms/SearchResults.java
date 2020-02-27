package com.bizwink.cms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 18-4-16.
 */
public class SearchResults implements Serializable {
    private BigDecimal ID;
    private BigDecimal SITEID;
    private String COMPANYNAME;
    private String COMPANYADDRESS;
    private String COMPANYPHONE;
    private String COMPANYFAX;
    private String COMPANYWEBSITE;
    private String COMPANYEMAIL;
    private String POSTCODE;
    private BigDecimal COMPANYCLASSID;
    private String CLASSIFICATION;
    private String SUMMARY;
    private BigDecimal COMPANYLATITUDE;
    private BigDecimal COMPANYLONGITUDE;
    private String COMPANYPIC;
    private BigDecimal PUBLISHFLAG;
    private BigDecimal STATUS;
    private Timestamp CREATEDATE;
    private Timestamp UPDATEDATE;
    private String PROVINCE;	             //VARCHAR2(12)	Y
    private String ZHPROVINCE;	             //VARCHAR2(12)	Y
    private String CITY;	                 //VARCHAR2(12)	Y
    private String ZHCITY;	                 //VARCHAR2(12)	Y
    private String ZONE;	                 //VARCHAR2(12)	Y
    private String ZHZONE;	                 //VARCHAR2(12)	Y
    private String TOWN;	                 //VARCHAR2(12)	Y
    private String ZHTOWN;	                 //VARCHAR2(12)	Y
    private String VILLAGE;	             //VARCHAR2(12)	Y
    private String ZHVILLAGE;	             //VARCHAR2(12)	Y
    private String COUNTRY;	             //VARCHAR2(12)	Y
    private String MPHONE;	             //VARCHAR2(50)	Y
    private String BIZPHONE;	             //VARCHAR2(50)	Y
    private String CONTACTOR;	             //VARCHAR2(50)	Y
    private BigDecimal SAMSITEID;	         //NUMBER	Y
    private BigDecimal COMPANYLEVEL;	     //NUMBER	Y
    private String QQ;
    private String WEIXIN;
    private String WEIBO;

    public BigDecimal getID() {
        return ID;
    }

    public void setID(BigDecimal ID) {
        this.ID = ID;
    }

    public BigDecimal getSITEID() {
        return SITEID;
    }

    public void setSITEID(BigDecimal SITEID) {
        this.SITEID = SITEID;
    }

    public String getCOMPANYNAME() {
        return COMPANYNAME;
    }

    public void setCOMPANYNAME(String COMPANYNAME) {
        this.COMPANYNAME = COMPANYNAME;
    }

    public String getCOMPANYADDRESS() {
        return COMPANYADDRESS;
    }

    public void setCOMPANYADDRESS(String COMPANYADDRESS) {
        this.COMPANYADDRESS = COMPANYADDRESS;
    }

    public String getCOMPANYPHONE() {
        return COMPANYPHONE;
    }

    public void setCOMPANYPHONE(String COMPANYPHONE) {
        this.COMPANYPHONE = COMPANYPHONE;
    }

    public String getCOMPANYFAX() {
        return COMPANYFAX;
    }

    public void setCOMPANYFAX(String COMPANYFAX) {
        this.COMPANYFAX = COMPANYFAX;
    }

    public String getCOMPANYWEBSITE() {
        return COMPANYWEBSITE;
    }

    public void setCOMPANYWEBSITE(String COMPANYWEBSITE) {
        this.COMPANYWEBSITE = COMPANYWEBSITE;
    }

    public String getCOMPANYEMAIL() {
        return COMPANYEMAIL;
    }

    public void setCOMPANYEMAIL(String COMPANYEMAIL) {
        this.COMPANYEMAIL = COMPANYEMAIL;
    }

    public String getPOSTCODE() {
        return POSTCODE;
    }

    public void setPOSTCODE(String POSTCODE) {
        this.POSTCODE = POSTCODE;
    }

    public BigDecimal getCOMPANYCLASSID() {
        return COMPANYCLASSID;
    }

    public void setCOMPANYCLASSID(BigDecimal COMPANYCLASSID) {
        this.COMPANYCLASSID = COMPANYCLASSID;
    }

    public String getCLASSIFICATION() {
        return CLASSIFICATION;
    }

    public void setCLASSIFICATION(String CLASSIFICATION) {
        this.CLASSIFICATION = CLASSIFICATION;
    }

    public String getSUMMARY() {
        return SUMMARY;
    }

    public void setSUMMARY(String SUMMARY) {
        this.SUMMARY = SUMMARY;
    }

    public BigDecimal getCOMPANYLATITUDE() {
        return COMPANYLATITUDE;
    }

    public void setCOMPANYLATITUDE(BigDecimal COMPANYLATITUDE) {
        this.COMPANYLATITUDE = COMPANYLATITUDE;
    }

    public BigDecimal getCOMPANYLONGITUDE() {
        return COMPANYLONGITUDE;
    }

    public void setCOMPANYLONGITUDE(BigDecimal COMPANYLONGITUDE) {
        this.COMPANYLONGITUDE = COMPANYLONGITUDE;
    }

    public String getCOMPANYPIC() {
        return COMPANYPIC;
    }

    public void setCOMPANYPIC(String COMPANYPIC) {
        this.COMPANYPIC = COMPANYPIC;
    }

    public BigDecimal getPUBLISHFLAG() {
        return PUBLISHFLAG;
    }

    public void setPUBLISHFLAG(BigDecimal PUBLISHFLAG) {
        this.PUBLISHFLAG = PUBLISHFLAG;
    }

    public BigDecimal getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(BigDecimal STATUS) {
        this.STATUS = STATUS;
    }

    public Timestamp getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(Timestamp CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public Timestamp getUPDATEDATE() {
        return UPDATEDATE;
    }

    public void setUPDATEDATE(Timestamp UPDATEDATE) {
        this.UPDATEDATE = UPDATEDATE;
    }

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }

    public String getZHPROVINCE() {
        return ZHPROVINCE;
    }

    public void setZHPROVINCE(String ZHPROVINCE) {
        this.ZHPROVINCE = ZHPROVINCE;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getZHCITY() {
        return ZHCITY;
    }

    public void setZHCITY(String ZHCITY) {
        this.ZHCITY = ZHCITY;
    }

    public String getZONE() {
        return ZONE;
    }

    public void setZONE(String ZONE) {
        this.ZONE = ZONE;
    }

    public String getZHZONE() {
        return ZHZONE;
    }

    public void setZHZONE(String ZHZONE) {
        this.ZHZONE = ZHZONE;
    }

    public String getTOWN() {
        return TOWN;
    }

    public void setTOWN(String TOWN) {
        this.TOWN = TOWN;
    }

    public String getZHTOWN() {
        return ZHTOWN;
    }

    public void setZHTOWN(String ZHTOWN) {
        this.ZHTOWN = ZHTOWN;
    }

    public String getVILLAGE() {
        return VILLAGE;
    }

    public void setVILLAGE(String VILLAGE) {
        this.VILLAGE = VILLAGE;
    }

    public String getZHVILLAGE() {
        return ZHVILLAGE;
    }

    public void setZHVILLAGE(String ZHVILLAGE) {
        this.ZHVILLAGE = ZHVILLAGE;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }

    public String getMPHONE() {
        return MPHONE;
    }

    public void setMPHONE(String MPHONE) {
        this.MPHONE = MPHONE;
    }

    public String getBIZPHONE() {
        return BIZPHONE;
    }

    public void setBIZPHONE(String BIZPHONE) {
        this.BIZPHONE = BIZPHONE;
    }

    public String getCONTACTOR() {
        return CONTACTOR;
    }

    public void setCONTACTOR(String CONTACTOR) {
        this.CONTACTOR = CONTACTOR;
    }

    public BigDecimal getSAMSITEID() {
        return SAMSITEID;
    }

    public void setSAMSITEID(BigDecimal SAMSITEID) {
        this.SAMSITEID = SAMSITEID;
    }

    public BigDecimal getCOMPANYLEVEL() {
        return COMPANYLEVEL;
    }

    public void setCOMPANYLEVEL(BigDecimal COMPANYLEVEL) {
        this.COMPANYLEVEL = COMPANYLEVEL;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getWEIXIN() {
        return WEIXIN;
    }

    public void setWEIXIN(String WEIXIN) {
        this.WEIXIN = WEIXIN;
    }

    public String getWEIBO() {
        return WEIBO;
    }

    public void setWEIBO(String WEIBO) {
        this.WEIBO = WEIBO;
    }
}
