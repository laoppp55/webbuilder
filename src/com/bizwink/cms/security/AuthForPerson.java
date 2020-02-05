package com.bizwink.cms.security;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-16
 * Time: 23:10:41
 * To change this template use File | Settings | File Templates.
 */
public class AuthForPerson {
    private String userID;
    private int siteid;

    public AuthForPerson(String userID, int siteid) {
        this.userID = userID;
        this.siteid = siteid;
    }

    public String getUserID() {
        return userID;
    }

    public int getSiteID() {
        return siteid;
    }

    public void setSiteID(int siteid) {
        this.siteid = siteid;
    }
}
