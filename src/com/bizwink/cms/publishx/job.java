package com.bizwink.cms.publishx;

import java.sql.*;

public class job
{
    private int id;
    private int type;                   //0--发布文章  1--发布栏目页面   2--发布首页
    private int siteid;
    private int columnid;
    private int targetid;
    private int status;
    private int errcode;
    private int errnum;
    private String errmsg;
    private int priority;
    private Timestamp publishtime;
    private String maintitle;
    private int referArticleFlag;

    public job(int id,int type,int columnid,Timestamp publishtime,String maintitle,int rflag)
    {
        this.id = id;
        this.publishtime = publishtime;
        this.type = type;
        this.columnid = columnid;
        this.maintitle = maintitle;
        this.referArticleFlag = rflag;
    }

    public job(int id,int type,int columnid,Timestamp publishtime,String maintitle)
    {
        this.id = id;
        this.publishtime = publishtime;
        this.type = type;
        this.columnid = columnid;
        this.maintitle = maintitle;
    }

    public job() {

    }

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public int getColumnID() {
        return columnid;
    }

    public void setColumnID(int ColumnID) {
        this.columnid = ColumnID;
    }

    public int getSiteID() {
        return siteid;
    }

    public void setSiteID(int siteid) {
        this.siteid = siteid;
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

    public Timestamp getPublishTime() {
        return publishtime;
    }

    public void setPublishTime(Timestamp publishtime) {
        this.publishtime = publishtime;
    }

    public String getMaintitle() {
        return maintitle;
    }

    public void setMaintitle(String maintitle) {
        this.maintitle  = maintitle;
    }

    public int getReferArticleFlag() {
        return referArticleFlag;
    }

    public void setReferArticleFlag(int flag) {
        this.referArticleFlag = flag;
    }

}