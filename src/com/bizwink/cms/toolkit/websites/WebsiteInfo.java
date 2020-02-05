package com.bizwink.cms.toolkit.websites;

import java.sql.Timestamp;


public class WebsiteInfo {
    public WebsiteInfo(){

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
    private float companylatitude;  //纬度
    private float companylongitude; //经度
    private String companygooglecode; //google码
    private String companypic;   //企业图片
    private int publishflag; //发布标志 0为未发布，1为发布
    private Timestamp createdate;
    private Timestamp lastupdated;

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


    public float getCompanylongitude() {
        return companylongitude;
    }

    public void setCompanylongitude(float companylongitude) {
        this.companylongitude = companylongitude;
    }

    public float getCompanylatitude() {
        return companylatitude;
    }

    public void setCompanylatitude(float companylatitude) {
        this.companylatitude = companylatitude;
    }

    public String getCompanygooglecode() {
        return companygooglecode;
    }

    public void setCompanygooglecode(String companygooglecode) {
        this.companygooglecode = companygooglecode;
    }

    public String getCompanypic() {
        return companypic;
    }

    public void setCompanypic(String companypic) {
        this.companypic = companypic;
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
}
