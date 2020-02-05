package com.bizwink.webapps.comment;

import java.sql.Date;
import java.sql.Timestamp;

public class webComment {
    private int about;
    private String name;
    private String link;
    private String content;
    private String ip;
    private Timestamp createdate;
    private int id;
    private int siteid;
    private String usrid;

    public int getAbout() {
        return about;
    }

    public void setAbout(int about) {
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIP() {
        return ip;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public Timestamp getCreateDate() {
        return createdate;
    }

    public void setCreateDate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }
}