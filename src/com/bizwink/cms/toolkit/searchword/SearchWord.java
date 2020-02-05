package com.bizwink.cms.toolkit.searchword;

import java.io.Serializable;


public class SearchWord implements Serializable {

    private int id;
    private String cname; //中文
    private String ename; //拼音
    private String sname; //拼音首字母
    private int num;      //搜索次数
    private int hotflag;  //热搜词标志位 0否 1是
    private int tabooflag; //是否敏感词  0否 1是

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getHotflag() {
        return hotflag;
    }

    public void setHotflag(int hotflag) {
        this.hotflag = hotflag;
    }

    public int getTabooflag() {
        return tabooflag;
    }

    public void setTabooflag(int tabooflag) {
        this.tabooflag = tabooflag;
    }
}
