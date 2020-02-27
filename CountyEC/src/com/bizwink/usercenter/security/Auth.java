package com.bizwink.usercenter.security;

/**
 * Created by petersong on 16-1-24.
 */
public class Auth {
    private String userid;
    private String username;
    private int usertype;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }
}
