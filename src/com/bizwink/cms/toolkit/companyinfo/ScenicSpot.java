package com.bizwink.cms.toolkit.companyinfo;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-17
 * Time: 22:14:37
 * To change this template use File | Settings | File Templates.
 */
public class ScenicSpot {
    private int id;                            //id
    private int spotid;                       //景点ID     对应tbl_companyinfo中的主键ID
    private int siteid;                       //站点id
    private String spotname;                  //名称
    private String spotaddress;               //地址
    private String summary;                    //简介
    private float latitude;                  //纬度
    private float longitude;                 //经度
    private Timestamp createdate;
    private Timestamp lastupdated;

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

    public int getSpotid() {
        return spotid;
    }

    public void setSpotid(int spotid) {
        this.spotid = spotid;
    }

    public String getSpotname() {
        return spotname;
    }

    public void setSpotname(String spotname) {
        this.spotname = spotname;
    }

    public String getSpotaddress() {
        return spotaddress;
    }

    public void setSpotaddress(String spotaddress) {
        this.spotaddress = spotaddress;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public Timestamp getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Timestamp lastupdated) {
        this.lastupdated = lastupdated;
    }
}
