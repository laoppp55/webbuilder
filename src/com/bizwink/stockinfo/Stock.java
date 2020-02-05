package com.bizwink.stockinfo;

import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-1-3
 * Time: 15:53:44
 * To change this template use File | Settings | File Templates.
 */
public class Stock {
    private  int id;
    private String name;
    private String last_trade;
    private String change;
    private String openprice;
    private String precloseprice;
    private String closeprice;
    private String maxprice;
    private String minprice;
    private Date thedate;
    private String exchange;
    private String volume;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_trade() {
        return last_trade;
    }

    public void setLast_trade(String last_trade) {
        this.last_trade = last_trade;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getOpenprice() {
        return openprice;
    }

    public void setOpenprice(String openprice) {
        this.openprice = openprice;
    }

    public String getPrecloseprice() {
        return precloseprice;
    }

    public void setPrecloseprice(String closeprice) {
        this.precloseprice = closeprice;
    }

    public String getCloseprice() {
        return closeprice;
    }

    public void setCloseprice(String closeprice) {
        this.closeprice = closeprice;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    public String getMinprice() {
        return minprice;
    }

    public void setMinprice(String minprice) {
        this.minprice = minprice;
    }

    public Date getThedate() {
        return thedate;
    }

    public void setThedate(Date thedate) {
        this.thedate = thedate;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }    
}
