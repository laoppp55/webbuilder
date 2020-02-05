package com.transferBjrabData;

import java.sql.*;

public class Data{

    private int id;
    private int provid;
    private int orderid;
    private String cityname;
    private int cityid;
    private String zonename;
    private String provname;
    private String title;
    private String phone;
    private String zip;
    private String number;
    private String country;
    private String city;
    private String area;
    private int siteid;
    private int columnid;
    private int sortid;
    private int emptycontentflag;
    private Timestamp publictime;
    private Timestamp createtime;
    private Timestamp lastupdate;
    private String dirname;
    private String editor;
    private int status;
    private int doclevel;
    private int vicedoclevel;
    private int pubflag;
    private int auditflag;
    private int subscriber;
    private int lockeflag;
    private int ispublished;
    private int indexflag;
    private int isjoinrss;
    private int clicknum;
    private int referid;
    private int modelid;
    private int pagesize;
    private int articleid;
    private int ettrid;
    private String ename;
    private String stringvalue;
    private int type;

    private String columnids;

  public Data(){
  }


    public int getId(){
        return id;
    }
    public void setId(int id ){
        this.id = id;
    }

    public int getProvid(){
        return provid;
    }
    public void setProvid(int provid){
        this.provid = provid;
    }

    public int getOrderid(){
        return orderid;
    }
    public void setOrderid(int orderid){
        this.orderid = orderid;
    }

    public String getCityname(){
        return cityname;
    }
    public void setCityname(String cityname){
        this.cityname = cityname;
    }

    public int getCityid(){
        return cityid;
    }
    public void setCityid(int cityid){
        this.cityid = cityid;
    }

    public String getZonename(){
        return zonename;
    }
    public void setZonename(String zonename){
        this.zonename = zonename;
    }

    public String getProvname(){
        return provname;
    }
    public void setProvname(String provname){
        this.provname = provname;
    }

    public String getColumnids() {
        return columnids;
    }

    public void setColumnids(String columnids) {
        this.columnids = columnids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }

    public int getEmptycontentflag() {
        return emptycontentflag;
    }

    public void setEmptycontentflag(int emptycontentflag) {
        this.emptycontentflag = emptycontentflag;
    }

    public Timestamp getPublictime() {
        return publictime;
    }

    public void setPublictime(Timestamp publictime) {
        this.publictime = publictime;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Timestamp getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Timestamp lastupdate) {
        this.lastupdate = lastupdate;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDoclevel() {
        return doclevel;
    }

    public void setDoclevel(int doclevel) {
        this.doclevel = doclevel;
    }

    public int getVicedoclevel() {
        return vicedoclevel;
    }

    public void setVicedoclevel(int vicedoclevel) {
        this.vicedoclevel = vicedoclevel;
    }

    public int getPubflag() {
        return pubflag;
    }

    public void setPubflag(int pubflag) {
        this.pubflag = pubflag;
    }

    public int getAuditflag() {
        return auditflag;
    }

    public void setAuditflag(int auditflag) {
        this.auditflag = auditflag;
    }

    public int getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(int subscriber) {
        this.subscriber = subscriber;
    }

    public int getLockeflag() {
        return lockeflag;
    }

    public void setLockeflag(int lockeflag) {
        this.lockeflag = lockeflag;
    }

    public int getIspublished() {
        return ispublished;
    }

    public void setIspublished(int ispublished) {
        this.ispublished = ispublished;
    }

    public int getIndexflag() {
        return indexflag;
    }

    public void setIndexflag(int indexflag) {
        this.indexflag = indexflag;
    }

    public int getIsjoinrss() {
        return isjoinrss;
    }

    public void setIsjoinrss(int isjoinrss) {
        this.isjoinrss = isjoinrss;
    }

    public int getClicknum() {
        return clicknum;
    }

    public void setClicknum(int clicknum) {
        this.clicknum = clicknum;
    }

    public int getReferid() {
        return referid;
    }

    public void setReferid(int referid) {
        this.referid = referid;
    }

    public int getModelid() {
        return modelid;
    }

    public void setModelid(int modelid) {
        this.modelid = modelid;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public int getEttrid() {
        return ettrid;
    }

    public void setEttrid(int ettrid) {
        this.ettrid = ettrid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getStringvalue() {
        return stringvalue;
    }

    public void setStringvalue(String stringvalue) {
        this.stringvalue = stringvalue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}