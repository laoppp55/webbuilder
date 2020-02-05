package com.bizwink.po;

import java.sql.Timestamp;

/**
 * Created by Administrator on 15-9-10.
 */
public class PublishJob {
    private int id;
    private int siteid;
    private int columnid;
    private int targetid;
    private int type;
    private int status;
    private int errcode;
    private int errnum;
    private String errmsg;
    private int priority;
    private Timestamp createdate;
    private Timestamp publishdate;
    private String title;
    private String uniqueid;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getSiteID() {
        return siteid;
    }

    public void setSiteID(int siteid) {
        this.siteid = siteid;
    }

    public int getColumnID() {
        return columnid;
    }

    public void setColumnID(int columnid) {
        this.columnid = columnid;
    }

    public int getTargetID() {
        return targetid;
    }

    public void setTargetID(int targetid) {
        this.targetid = targetid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public Timestamp getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Timestamp pdate) {
        this.publishdate = pdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

}
