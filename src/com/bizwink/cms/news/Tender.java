package com.bizwink.cms.news;

import java.sql.Timestamp;

/**
 * Created by Jhon on 2015/12/4.
 */
public class Tender {
    private int id;                 //主键ID
    private int articleid;         //文章ID
    private int userid;             //userID
    private String name;            //投标人名称
    private String depttitle;      //投标人所在单位
    private Timestamp createDate;  //入库日期
    private int fileid;            //文件id
    private int num;
    private String maintitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepttitle() {
        return depttitle;
    }

    public void setDepttitle(String depttitle) {
        this.depttitle = depttitle;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMaintitle() {
        return maintitle;
    }

    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }
}
