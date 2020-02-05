package com.bizwink.stockinfo;

/**
 * Created by IntelliJ IDEA.
 * User: Tim
 * Date: 2007-4-29
 * Time: 14:18:37
 * To change this template use File | Settings | File Templates.
 */
public class History {

    private int id;
    private String stocknum;
    private String hisurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStocknum() {
        return stocknum;
    }

    public void setStocknum(String stocknum) {
        this.stocknum = stocknum;
    }

    public String getHisurl() {
        return hisurl;
    }

    public void setHisurl(String hisurl) {
        this.hisurl = hisurl;
    }
}
