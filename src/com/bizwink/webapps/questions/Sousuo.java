package com.bizwink.webapps.questions;

import java.sql.*;

public class Sousuo {
    private int id;
    private String maintitle;
    private String dirname;
    private Timestamp createdate;
    private int cid;
    private int answernum;
    private int status;
    public int getAnswernum() {
        return answernum;
    }
    public void setAnswernum(int answernum) {
        this.answernum = answernum;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public String getMaintitle() {
        return maintitle;
    }
    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }
    public String getDirname() {
        return dirname;
    }
    public void setDirname(String dirname) {
        this.dirname = dirname;
    }
    public Timestamp getCreatedate() {
        return createdate;
    }
    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
