package com.bizwink.webapps.leaveword;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-26
 * Time: 22:19:44
 * To change this template use File | Settings | File Templates.
 */
public class authorizedform {
    private int siteid;
    private int lwid;
    private String userid;
    private String lwrole;
    private String lwname;
    private int contenttyep;

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getLwid() {
        return lwid;
    }

    public void setLwid(int lwid) {
        this.lwid = lwid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLwrole() {
        return lwrole;
    }

    public void setLwrole(String lwrole) {
        this.lwrole = lwrole;
    }

    public String getLwname() {
        return lwname;
    }

    public void setLwname(String lwname) {
        this.lwname = lwname;
    }

    public int getContenttyep() {
        return contenttyep;
    }

    public void setContenttyep(int contenttyep) {
        this.contenttyep = contenttyep;
    }
}
