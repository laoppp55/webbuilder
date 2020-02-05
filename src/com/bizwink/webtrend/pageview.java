package com.bizwink.webtrend;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-7-31
 * Time: 18:27:22
 * To change this template use File | Settings | File Templates.
 */
public class pageview {
    private String url;
    private String cnname;
    private String filename;
    private String dirname;
    private Timestamp accesstime;
    private int pv;
    private int url_uniqueip;
    private int url_usersession;
    private ArrayList uniqueiplist;
    private ArrayList logsByURL;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDirname() {
        return dirname;
    }

    public void setDirname(String dirname) {
        this.dirname = dirname;
    }

    public Timestamp getAccesstime() {
        return accesstime;
    }

    public void setAccesstime(Timestamp accesstime) {
        this.accesstime = accesstime;
    }

    public int getUrl_uniqueip() {
        return url_uniqueip;
    }

    public void setUrl_uniqueip(int url_uniqueip) {
        this.url_uniqueip = url_uniqueip;
    }

    public int getUrl_usersession() {
        return url_usersession;
    }

    public void setUrl_usersession(int url_usersession) {
        this.url_usersession = url_usersession;
    }

    public ArrayList getUniqueiplist() {
        return uniqueiplist;
    }

    public void setUniqueiplist(ArrayList uniqueiplist) {
        this.uniqueiplist = uniqueiplist;
    }

    public ArrayList getLogsByURL() {
        return logsByURL;
    }

    public void setLogsByURL(ArrayList logsByURL) {
        this.logsByURL = logsByURL;
    }
}
