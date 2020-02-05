package com.bizwink.cms.publish;

/**
 * <p>Title: cms</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Beijing Bizwink Software Inc</p>
 * @author Peter Song
 *
 * @version 1.0
 */
import java.lang.*;
import java.sql.*;

public class publishedArticle {
    private int ID;                                            // 编号
    private Timestamp publishedtime;                           // 文章被发布的时间
    private String publishername;                              // 发布文章者的姓名
    private String dirname;                                    // 文章被发布的目标目录
    private String url;                                        // 文章的URL
    private int publishstatus;                                 // 文章的发布状态  0：文章已经发布  1：文章需要发布
    private Timestamp publishtime;                             // 发布文章的时间

    public publishedArticle() {

    }

    public void setID(int id) {
        this.ID = id;
    }

    public int getID() {
        return ID;
    }

    public void setPublishedtime(Timestamp t) {
        this.publishedtime = t;
    }

    public Timestamp getPublishedtime() {
        return publishedtime;
    }

    public void setPublishername(String name) {
        this.publishername = name;
    }

    public String getPublishername() {
        return publishername;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }

    public void setPublishstatus(int status) {
        this.publishstatus = status;
    }

    public int getPublishstatus() {
        return publishstatus;
    }

    public void setPublishtime(Timestamp t) {
        this.publishtime = t;
    }

    public Timestamp getPublishtime() {
        return publishtime;
    }
}