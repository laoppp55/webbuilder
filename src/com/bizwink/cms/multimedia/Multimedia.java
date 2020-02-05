package com.bizwink.cms.multimedia;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-10-13
 * Time: 10:45:02
 * To change this template use File | Settings | File Templates.
 */
public class Multimedia {

    private int id;                 //主键
    private int siteid;             //站点id
    private int articleid;        //文章id
    private int infotype;          //0--正常文章中的多媒体文件 1-企业信息中的多媒体文件
    private String dirname;        //栏目路径
    private String filepath;       //文件路径
    private String oldfilename;     //原文件名称
    private String newfilename;     //新文件名称
    private int encodeflag;        //文件转换标志0-未转换 1-已经转换
    private Timestamp createdate;   //创建时间

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

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public int getInfotype() {
        return infotype;
    }

    public void setInfotype(int infotype) {
        this.infotype = infotype;
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

    public int getEncodeflag() {
        return encodeflag;
    }

    public void setEncodeflag(int encodeflag) {
        this.encodeflag = encodeflag;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }
}
