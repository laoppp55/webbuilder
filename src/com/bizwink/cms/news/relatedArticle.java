package com.bizwink.cms.news;

import java.sql.*;

public class relatedArticle {
    private int id;
    private String cname;
    private int pageid;
    private int pagetype;
    private int contenttype;
    private String filename;
    private String summary;
    private String editor;
    private Timestamp createDate;
    private int jointedid;
    private String chineseName;
    private String title;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getContenttype() {
        return contenttype;
    }

    public void setContenttype(int contenttype) {
        this.contenttype = contenttype;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPageid() {
        return pageid;
    }

    public void setPageid(int pageid) {
        this.pageid = pageid;
    }

    public int getPagetype() {
        return pagetype;
    }

    public void setPagetype(int pagetype) {
        this.pagetype = pagetype;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public int getJointedID() {
        return jointedid;
    }

    public void setJointedID(int jointid) {
        this.jointedid = jointid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}