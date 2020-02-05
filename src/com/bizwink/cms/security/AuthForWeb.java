package com.bizwink.cms.security;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-7-16
 * Time: 20:46:28
 * To change this template use File | Settings | File Templates.
 */
public class AuthForWeb {
    private int id;
    private String userID;
    private int siteid;
    private int userlevel;
    private int userflag;

    public AuthForWeb(int id,int userlevel,String userID, int siteid) {
        this.userID = userID;
        this.siteid = siteid;
        this.userlevel = userlevel;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userid) {
        this.userID = userid;
    }

    public int getSiteID() {
        return siteid;
    }

    public void setSiteID(int siteid) {
        this.siteid = siteid;
    }

    public int getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(int userlevel) {
        this.userlevel = userlevel;
    }

    public int getUserflag() {
        return userflag;
    }

    public void setUserflag(int userflag) {
        this.userflag = userflag;
    }
}
