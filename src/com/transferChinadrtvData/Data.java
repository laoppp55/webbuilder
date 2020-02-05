package com.transferChinadrtvData;

import java.sql.*;

public class Data{

    private int id;
    private int cityid;
    private String proname;
    private int orderid;
    private int emsfee;


  public Data(){
  }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getEmsfee() {
        return emsfee;
    }

    public void setEmsfee(int emsfee) {
        this.emsfee = emsfee;
    }

}