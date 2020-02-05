package com.bizwink.webtrend;

import java.sql.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: bizwink software inc</p>
 * @author peter song
 * @version 1.0
 */

public class logDetail {
    private int  ID;                              //编号
    private int  websiteID;                       //站点ID
    private String urlName;                       //URL名称
    private int pvnum;                            //该文章的页面浏览数
    private int userSessions;                     //该文章的会话数目
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


    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String name) {
        this.urlName = name;
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

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp Createdate) {
        this.createdate = Createdate;
    }
}