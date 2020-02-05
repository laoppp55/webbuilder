package com.bizwink.wenba;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-11-17
 * Time: 11:04:42
 * To change this template use File | Settings | File Templates.
 */
public class EMoney {
    private int id;
    private int user_id;
    private String add_date;
    private int ADD_BEFORE_EMONEY;
    private int ADD_EMONEY;
    private int ADD_AFTER_EMONEY;
    private String RMB;
    private String beizhu;
    private String payway;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }

    public int getADD_BEFORE_EMONEY() {
        return ADD_BEFORE_EMONEY;
    }

    public void setADD_BEFORE_EMONEY(int ADD_BEFORE_EMONEY) {
        this.ADD_BEFORE_EMONEY = ADD_BEFORE_EMONEY;
    }

    public int getADD_EMONEY() {
        return ADD_EMONEY;
    }

    public void setADD_EMONEY(int ADD_EMONEY) {
        this.ADD_EMONEY = ADD_EMONEY;
    }

    public int getADD_AFTER_EMONEY() {
        return ADD_AFTER_EMONEY;
    }

    public void setADD_AFTER_EMONEY(int ADD_AFTER_EMONEY) {
        this.ADD_AFTER_EMONEY = ADD_AFTER_EMONEY;
    }

    public String getRMB() {
        return RMB;
    }

    public void setRMB(String RMB) {
        this.RMB = RMB;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }
}
