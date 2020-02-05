package com.bizwink.cms.business.Order;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-2-10
 * Time: 下午11:11
 * To change this template use File | Settings | File Templates.
 */
public class Zones {
    private int id;
    private int cityid;
    private String zonename;
    private long orderid;

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

    public String getZonename() {
        return zonename;
    }

    public void setZonename(String zonename) {
        this.zonename = zonename;
    }

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }
}
