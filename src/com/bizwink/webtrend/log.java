package com.bizwink.webtrend;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-7-31
 * Time: 17:43:55
 * To change this template use File | Settings | File Templates.
 */
public class log {
    private String server_ip;
    private Timestamp accesstime;
    private String method;
    private String url;
    private String httpversion;
    private int code;
    private int infosize;
    private String refer_url;
    private String agent;
    private String keyword;

    public String getServer_ip() {
        return server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }

    public Timestamp getAccesstime() {
        return accesstime;
    }

    public void setAccesstime(Timestamp accesstime) {
        this.accesstime = accesstime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpversion() {
        return httpversion;
    }

    public void setHttpversion(String httpversion) {
        this.httpversion = httpversion;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getInfosize() {
        return infosize;
    }

    public void setInfosize(int infosize) {
        this.infosize = infosize;
    }

    public String getRefer_url() {
        return refer_url;
    }

    public void setRefer_url(String refer_url) {
        this.refer_url = refer_url;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
