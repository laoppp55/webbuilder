package com.bizwink.collectionmgr;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 2007-11-8
 * Time: 17:34:49
 */
public class Basic_Attributes {
    private int id;
    private String siteName;
    private String startUrl;
    private int classId;
    private int urlNumber;
    private String posturl;
    private int loginflag;
    private String status;
    private String postdata;
    private int proxyflag;
    private String proxyloginuser;
    private String proxyloginpass;
    private String proxyurl;
    private String proxyport;
    private String cname;
    private int keywordflag;
    private int stopflag;
    private Timestamp createdate;
    private int spiderflag;
    private int startUrlId;
    private String tKeyword;//标题关键字
    private String bKeyword;//正文关键字
    private int tbRelation;//tkeyword 与 bkeyword 的联系

    public int getKeywordflag() {
        return keywordflag;
    }

    public void setKeywordflag(int keywordflag) {
        this.keywordflag = keywordflag;
    }

    public int getSpiderflag() {
        return spiderflag;
    }

    public void setSpiderflag(int spiderflag) {
        this.spiderflag = spiderflag;
    }

    public Basic_Attributes() {

    }

    public String getPostdata() {
        return postdata;
    }

    public void setPostdata(String postdata) {
        this.postdata = postdata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLoginflag() {
        return loginflag;
    }

    public void setLoginflag(int loginflag) {
        this.loginflag = loginflag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getUrlNumber() {
        return urlNumber;
    }

    public void setUrlNumber(int urlNumber) {
        this.urlNumber = urlNumber;
    }

    public String getPosturl() {
        return posturl;
    }

    public void setPosturl(String posturl) {
        this.posturl = posturl;
    }

    public int getProxyflag() {
        return proxyflag;
    }

    public void setProxyflag(int proxyflag) {
        this.proxyflag = proxyflag;
    }

    public String getProxyport() {
        return proxyport;
    }

    public void setProxyport(String proxyport) {
        this.proxyport = proxyport;
    }

    public String getProxyurl() {
        return proxyurl;
    }

    public void setProxyurl(String proxyurl) {
        this.proxyurl = proxyurl;
    }

    public int getStartUrlId() {
        return startUrlId;
    }

    public void setStartUrlId(int startUrlId) {
        this.startUrlId = startUrlId;
    }

    public String getTKeyword() {
        return tKeyword;
    }

    public void setTKeyword(String tKeyword) {
        this.tKeyword = tKeyword;
    }

    public String getBKeyword() {
        return bKeyword;
    }

    public void setBKeyword(String bKeyword) {
        this.bKeyword = bKeyword;
    }

    public int getTbRelation() {
        return tbRelation;
    }

    public void setTbRelation(int tbRelation) {
        this.tbRelation = tbRelation;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getStopflag() {
        return stopflag;
    }

    public void setStopflag(int stopflag) {
        this.stopflag = stopflag;
    }

    public String getProxyloginuser() {
        return proxyloginuser;
    }

    public void setProxyloginuser(String proxyloginuser) {
        this.proxyloginuser = proxyloginuser;
    }

    public String getProxyloginpass() {
        return proxyloginpass;
    }

    public void setProxyloginpass(String proxyloginpass) {
        this.proxyloginpass = proxyloginpass;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }
}
