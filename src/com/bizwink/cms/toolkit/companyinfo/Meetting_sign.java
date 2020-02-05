package com.bizwink.cms.toolkit.companyinfo;

import java.sql.Timestamp;

/**
 * Created by petersong on 15-10-20.
 */
public class Meetting_sign {
    private int id;                        //number,              --主键ID
    private long orderid;                  //number,               --订单号
    private int    siteid;                //number,              --培训会站点id
    private int meetingid;                 //number,              --培训会ID
    private String comapnyname;            //varchar2(200),       --公司名称
    private String invoicetitle;           //varchar2(200),       --发票抬头
    private String address;                 //varchar2(10),        --发票邮寄地址
    private String postcode;                //varchar2(6),         --邮政编码
    private float fee;                      //number(9,2),         --培训费用
    private int payway;                     //number(1),           --支付方式     1--银行   2--网络支付
    private Timestamp paytime;              //date,                --支付日期和时间
    private Timestamp createdate;          //date,                --注册日期和时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(int meetingid) {
        this.meetingid = meetingid;
    }

    public String getComapnyname() {
        return comapnyname;
    }

    public void setComapnyname(String comapnyname) {
        this.comapnyname = comapnyname;
    }

    public String getInvoicetitle() {
        return invoicetitle;
    }

    public void setInvoicetitle(String invoicetitle) {
        this.invoicetitle = invoicetitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getPayway() {
        return payway;
    }

    public void setPayway(int payway) {
        this.payway = payway;
    }

    public Timestamp getPaytime() {
        return paytime;
    }

    public void setPaytime(Timestamp paytime) {
        this.paytime = paytime;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }
}
