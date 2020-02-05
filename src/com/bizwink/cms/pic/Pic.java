package com.bizwink.cms.pic;

import java.sql.Timestamp;


public class Pic {

    private int id;
    private int siteid;
    private int columnid;
    private int width;
    private int height;
    private int picsize;
    private String picname;
    private String imgurl;
    private Timestamp createdate;
    private String notes;
    private int infotype;
    private double latf;
    private double lngf;
    private String cammer;
    private String cammertype;
    private String filename;                       //图片文件名
    private String smallfilename;                  //相应小图片文件名

    public int getInfotype() {
        return infotype;
    }

    public void setInfotype(int infotype) {
        this.infotype = infotype;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getColumnid() {
        return columnid;
    }

    public void setColumnid(int columnid) {
        this.columnid = columnid;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPicsize() {
        return picsize;
    }

    public void setPicsize(int picsize) {
        this.picsize = picsize;
    }

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSmallfilename() {
        return smallfilename;
    }

    public void setSmallfilename(String smallfilename) {
        this.smallfilename = smallfilename;
    }

    public double getLatf() {
        return latf;
    }

    public void setLatf(double latf) {
        this.latf = latf;
    }

    public double getLngf() {
        return lngf;
    }

    public void setLngf(double lngf) {
        this.lngf = lngf;
    }

    public String getCammer() {
        return cammer;
    }

    public void setCammer(String cammer) {
        this.cammer = cammer;
    }

    public String getCammertype() {
        return cammertype;
    }

    public void setCammertype(String cammertype) {
        this.cammertype = cammertype;
    }
}