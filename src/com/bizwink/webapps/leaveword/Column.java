package com.bizwink.webapps.leaveword;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-4-9
 * Time: 13:36:34
 * To change this template use File | Settings | File Templates.
 */
public class Column {
    private int id;
    private int siteid;
    private int parentid;
    private int orderid;
    private String cname;
    private String ename;
    private String dirname;
    private String editor;
    private String extname;
    private String summary;
    private int lwnum;                             //某个分类下的留言数量
    private Timestamp createdate;
    private Timestamp lastupdated;

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

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getDirname() {
        return dirname;
    }

    public void setDirname(String dirname) {
        this.dirname = dirname;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getExtname() {
        return extname;
    }

    public void setExtname(String extname) {
        this.extname = extname;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getLwnum() {
        return lwnum;
    }

    public void setLwnum(int lwnum) {
        this.lwnum = lwnum;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public Timestamp getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Timestamp lastupdated) {
        this.lastupdated = lastupdated;
    }
}
