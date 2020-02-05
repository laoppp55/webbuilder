package com.bizwink.webapps.address;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-9
 * Time: 8:34:29
 * To change this template use File | Settings | File Templates.
 */
public class City {
    public City(){

    }
    private int id;
    private String cityname;
    private int provid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public int getProvid() {
        return provid;
    }

    public void setProvid(int provid) {
        this.provid = provid;
    }
}
