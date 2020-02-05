package com.bizwink.collectionmgr;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 2007-11-8
 * Time: 17:34:23
 */
public class GlobalConfig {
    private int id;
    private Timestamp startTime;
    private int interval;  //抓取时长
    private String proxyName;
    private String proxyPort;
    private int proxyflag;
    private int proxyloginflag;
    private String proxyloginuser;
    private String proxyloginpass;
    private int systemrun;
    private int keywordflag;
    private String tkeyword;
    private String bkeyword;
    private int tbrelation;

    public int getTbrelation() {
        return tbrelation;
    }

    public void setTbrelation(int tbrelation) {
        this.tbrelation = tbrelation;
    }

    public String getTkeyword() {
        return tkeyword;
    }

    public void setTkeyword(String tkeyword) {
        this.tkeyword = tkeyword;
    }

    public String getBkeyword() {
        return bkeyword;
    }

    public void setBkeyword(String bkeyword) {
        this.bkeyword = bkeyword;
    }

    public int getKeywordflag() {
        return keywordflag;
    }

    public void setKeywordflag(int keywordflag) {
        this.keywordflag = keywordflag;
    }

    public int getProxyflag() {
        return proxyflag;
    }

    public void setProxyflag(int proxyflag) {
        this.proxyflag = proxyflag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getProxyName() {
        return proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public int getProxyloginflag() {
        return proxyloginflag;
    }

    public void setProxyloginflag(int proxyloginflag) {
        this.proxyloginflag = proxyloginflag;
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

    public int getSystemrun() {
        return systemrun;
    }

    public void setSystemrun(int systemrun) {
        this.systemrun = systemrun;
    }
}
