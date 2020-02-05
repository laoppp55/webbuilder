package com.bizwink.cms.bjhqfw.yuding;

import java.sql.Timestamp;

public class YuDing {

    private int id;
    private String ydperson;
    private String jbxinxiid;
    private Timestamp khdate;
    private Timestamp jsdate;
    private int flag;
    private String shperson;
    private Timestamp shdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYdperson() {
        return ydperson;
    }

    public void setYdperson(String ydperson) {
        this.ydperson = ydperson;
    }

    public String getJbxinxiid() {
        return jbxinxiid;
    }

    public void setJbxinxiid(String jbxinxiid) {
        this.jbxinxiid = jbxinxiid;
    }

    public Timestamp getKhdate() {
        return khdate;
    }

    public void setKhdate(Timestamp khdate) {
        this.khdate = khdate;
    }

    public Timestamp getJsdate() {
        return jsdate;
    }

    public void setJsdate(Timestamp jsdate) {
        this.jsdate = jsdate;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getShperson() {
        return shperson;
    }

    public void setShperson(String shperson) {
        this.shperson = shperson;
    }

    public Timestamp getShdate() {
        return shdate;
    }

    public void setShdate(Timestamp shdate) {
        this.shdate = shdate;
    }
}
