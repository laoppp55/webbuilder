package com.bizwink.cms.toolkit.companyinfo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


public class Companyinfo {
    public Companyinfo(){

    }
    private int id;                 //id
    private int siteid;            //站点id
    private int companyclassid;
    private String companyname;    //名称
    private String companyaddress; //地址
    private String companyphone;   //电话
    private String companyfax;     //传真
    private String companywebsite; //网址
    private String companyemail;   //电子邮箱
    private String postcode;       //邮编
    private String classification;   //分类
    private String summary;           //简介
    private double companylatitude;  //纬度
    private double companylongitude; //经度
    private String companygooglecode;  //google码
    private List companypic;            //企业图片
    private List companymedia;          //企业视频信息
    private int publishflag;           //发布标志 0为未发布，1为发布
    private Timestamp createdate;
    private Timestamp lastupdated;
    private String PROVINCE;	             //VARCHAR2(12)	Y
    private String CITY;	                 //VARCHAR2(12)	Y
    private String ZONE;	                 //VARCHAR2(12)	Y
    private String TOWN;	                 //VARCHAR2(12)	Y
    private String VILLAGE;	             //VARCHAR2(12)	Y
    private String COUNTRY;	             //VARCHAR2(12)	Y
    private String MPHONE;	             //VARCHAR2(50)	Y
    private String BIZPHONE;	             //VARCHAR2(50)	Y
    private String CONTACTOR;	             //VARCHAR2(50)	Y
    private int SAMSITEID;	             //NUMBER	Y
    private int COMPANYLEVEL;	         //NUMBER	Y
    private String QQ;
    private String WEIXIN;
    private String WEIBO;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyclassid() {
        return companyclassid;
    }

    public void setCompanyclassid(int companyclassid) {
        this.companyclassid = companyclassid;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress;
    }

    public String getCompanyphone() {
        return companyphone;
    }

    public void setCompanyphone(String companyphone) {
        this.companyphone = companyphone;
    }

    public String getCompanyfax() {
        return companyfax;
    }

    public void setCompanyfax(String companyfax) {
        this.companyfax = companyfax;
    }

    public String getCompanywebsite() {
        return companywebsite;
    }

    public void setCompanywebsite(String companywebsite) {
        this.companywebsite = companywebsite;
    }

    public String getCompanyemail() {
        return companyemail;
    }

    public void setCompanyemail(String companyemail) {
        this.companyemail = companyemail;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }


    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public double getCompanylongitude() {
        return companylongitude;
    }

    public void setCompanylongitude(double companylongitude) {
        this.companylongitude = companylongitude;
    }

    public double getCompanylatitude() {
        return companylatitude;
    }

    public void setCompanylatitude(double companylatitude) {
        this.companylatitude = companylatitude;
    }

    public String getCompanygooglecode() {
        return companygooglecode;
    }

    public void setCompanygooglecode(String companygooglecode) {
        this.companygooglecode = companygooglecode;
    }

    public List getCompanypic() {
        return companypic;
    }

    public void setCompanypic(List companypic) {
        this.companypic = companypic;
    }

    public List getCompanyMedia() {
        return companymedia;
    }

    public void setCompanyMedia(List media) {
        this.companymedia = media;
    }

    public int getPublishflag() {
        return publishflag;
    }

    public void setPublishflag(int publishflag) {
        this.publishflag = publishflag;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp cdate) {
        this.createdate = cdate;
    }

    public Timestamp getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Timestamp lastupdated) {
        this.lastupdated = lastupdated;
    }

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getZONE() {
        return ZONE;
    }

    public void setZONE(String ZONE) {
        this.ZONE = ZONE;
    }

    public String getTOWN() {
        return TOWN;
    }

    public void setTOWN(String TOWN) {
        this.TOWN = TOWN;
    }

    public String getVILLAGE() {
        return VILLAGE;
    }

    public void setVILLAGE(String VILLAGE) {
        this.VILLAGE = VILLAGE;
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

    public int getSAMSITEID() {
        return SAMSITEID;
    }

    public void setSAMSITEID(int SAMSITEID) {
        this.SAMSITEID = SAMSITEID;
    }

    public int getCOMPANYLEVEL() {
        return COMPANYLEVEL;
    }

    public void setCOMPANYLEVEL(int COMPANYLEVEL) {
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
