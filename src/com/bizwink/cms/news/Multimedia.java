package com.bizwink.cms.news;

import java.sql.Timestamp;

/**
 * Created by Jhon on 2015/12/7.
 */
public class Multimedia {
    private int ID = 0;                //编号
    private int siteid;                //文章所属的站点
    private int articleid;            //所属文章id
    private String dirname;
    private String filepath;
    private String oldfilename;
    private String newfilename;
    private int ENCODEFLAG;
    private Timestamp CREATEDATE;
    private int INFOTYPE;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public String getDirname() {
        return dirname;
    }

    public void setDirname(String dirname) {
        this.dirname = dirname;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getOldfilename() {
        return oldfilename;
    }

    public void setOldfilename(String oldfilename) {
        this.oldfilename = oldfilename;
    }

    public String getNewfilename() {
        return newfilename;
    }

    public void setNewfilename(String newfilename) {
        this.newfilename = newfilename;
    }

    public int getENCODEFLAG() {
        return ENCODEFLAG;
    }

    public void setENCODEFLAG(int ENCODEFLAG) {
        this.ENCODEFLAG = ENCODEFLAG;
    }

    public Timestamp getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(Timestamp CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public int getINFOTYPE() {
        return INFOTYPE;
    }

    public void setINFOTYPE(int INFOTYPE) {
        this.INFOTYPE = INFOTYPE;
    }
}
