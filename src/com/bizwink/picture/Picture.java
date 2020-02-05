package com.bizwink.picture;

import java.sql.*;

public class Picture{
    private int ID;                                //编号，PK，number(6,0)
    private int siteid;                            //站点编号
    private int columnid;                          //图片所属栏目ID
    private int articleid;                         //图片所在文章ID
    private int width;                             //图片宽度
    private int height;                            //图片高度
    private int picsize;                           //图片大小，以K为单位
    private String picname;                        //图片名称
    private String imgurl;                         //图片url
    private String filename;                       //图片文件名
    private String smallfilename;                  //相应小图片文件名
    private Timestamp createdate;                  //创建时间

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public int getSiteID()
    {
        return siteid;
    }

    public void setSiteID(int siteID)
    {
        this.siteid  = siteID;
    }

    public int getColumnID()
    {
        return columnid;
    }

    public void setColumnID(int columnID)
    {
        this.columnid = columnID;
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width  = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getPicSize()
    {
        return picsize;
    }

    public void setPicSize(int size)
    {
        this.picsize  = size;
    }

    public String getPicname()
    {
        return picname;
    }

    public void setPicname(String picname)
    {
        this.picname  = picname;
    }

    public String getImgUrl()
    {
        return imgurl;
    }

    public void setImgUrl(String imgurl)
    {
        this.imgurl  = imgurl;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String fname)
    {
        this.filename  = fname;
    }

    public String getSmallFilename()
    {
        return smallfilename;
    }

    public void setSmallFilename(String sfname)
    {
        this.smallfilename  = sfname;
    }

    public Timestamp getCreateDate()
    {
        return createdate;
    }

    public void setCreateDate(Timestamp createDate)
    {
        this.createdate = createDate;
    }
}