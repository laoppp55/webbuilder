package com.bizwink.cms.publishx;

import java.sql.Timestamp;

public class Publish
{
    private int ID;
    private int siteID;
    private int columnid;
    private int targetID;
    private int objectType;
    private int status;
    private int errcode;
    private int errnum;
    private String errmsg;
    private int priority;
    private int type;
    private Timestamp createDate;
    private Timestamp publishDate;
    private String uniqueID;
    private String title;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSiteID() {
        return siteID;
    }

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    public int getColumnID() {
        return columnid;
    }

    public void setColumnID(int columnID) {
        this.columnid = columnID;
    }

    public int getTargetID() {
        return targetID;
    }

    public void setTargetID(int targetID) {
        this.targetID = targetID;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int code) {
        this.errcode = code;
    }

    public int getErrnum() {
        return errnum;
    }

    public void setErrnum(int num) {
        this.errnum = num;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int p) {
        this.priority = p;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String msg) {
        this.errmsg  = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
