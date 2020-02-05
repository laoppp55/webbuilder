package com.bizwink.log;

import java.sql.*;

public class Log {
    private long ID;                                 //编号，PK，number(6,0)
    private int articleID;                          //文章ID
    private int siteID;                             //站点ID
    private int columnid;                           //文章所属栏目ID
    private String editor;                           //编辑
    private int actType;                            //操作类型
    private Timestamp actTime;                       //操作时间
    private String maintitle;
    private Timestamp createdate;
    private int orgid;
    private int companyid;
    private int deptid;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getSiteID() {
        return siteID;
    }

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    public int getColumnid() {
        return columnid;
    }

    public void setColumnid(int columnid) {
        this.columnid = columnid;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public void setActType(int actType) {
        this.actType = actType;
    }

    public int getActType() {
        return actType;
    }

    public Timestamp getActTime() {
        return actTime;
    }

    public void setActTime(Timestamp actTime) {
        this.actTime = actTime;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public String getMaintitle() {
        return maintitle;
    }

    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public int getOrgid() {
        return orgid;
    }

    public void setOrgid(int orgid) {
        this.orgid = orgid;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public int getDeptid() {
        return deptid;
    }

    public void setDeptid(int deptid) {
        this.deptid = deptid;
    }
}