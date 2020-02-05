package com.bizwink.cms.business.Order;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-2-10
 * Time:  11:10
 * To change this template use File | Settings | File Templates.
 */
public class Province {
    private int id;
    private String provname;
    private long orderid;
    private int emsfee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvname() {
        return provname;
    }

    public void setProvname(String provname) {
        this.provname = provname;
    }

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public int getEmsfee() {
        return emsfee;
    }

    public void setEmsfee(int emsfee) {
        this.emsfee = emsfee;
    }
}
