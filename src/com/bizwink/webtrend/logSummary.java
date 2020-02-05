/**
 * Marker.java
 */
package com.bizwink.webtrend;

import java.sql.*;

/**
 * Title:        cms
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      bizwink software inc
 * @author       Peter Song
 * @version 1.0
 */

public class logSummary {
    private int  ID;                              //编号
    private String  websitename;                  //站点名称
    private int pvnum;                            //总页面浏览数
    private int userSessions;                     //总用户会话数目
    private int averageSessionLength;             //平均用户会话时间长度
    private int uniqueUsers;                      //唯一用户数目
    private Timestamp createdate;                 //创建日期

    /**
     * Returns the id of the marker.
     */
    public int getID() {
        return ID;
    }

    /**
     * set the id of the marker.
     */
    public void setID(int ID) {
        this.ID = ID;
    }


    public String getWebsiteName() {
        return websitename;
    }

    public void setWebsiteName(String name) {
        this.websitename = name;
    }

    public int getPVNum() {
        return pvnum;
    }

    public void setPVNum(int num) {
        this.pvnum  = num;
    }

    public int getUserSessions() {
        return userSessions;
    }

    public void setUserSessions(int session) {
        this.userSessions = session;
    }

    public int getAverageSessionLength() {
        return averageSessionLength;
    }

    public void setAverageSessionLength(int sessionLength) {
        this.averageSessionLength = sessionLength;
    }

    public int getUniqueUsers() {
        return uniqueUsers;
    }

    public void setUniqueUsers(int users) {
        this.uniqueUsers = users;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp Createdate) {
        this.createdate = Createdate;
    }
}