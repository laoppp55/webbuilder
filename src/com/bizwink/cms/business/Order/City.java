package com.bizwink.cms.business.Order;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-2-10
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public class City {
    private int id;
    private int provid;
    private String cityname;
    private long orderid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvid() {
        return provid;
    }

    public void setProvid(int provid) {
        this.provid = provid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }
}
