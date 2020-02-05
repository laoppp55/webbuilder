package com.bizwink.cms.business.Order;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-22
 * Time: 9:23:04
 * To change this template use File | Settings | File Templates.
 */
public class Invoice {
    public Invoice(){

    }
    private int id;
    private long orderid;
    private int siteid;
    private int userid;
    private int invoicetype;
    private int title;
    private String companyname;
    private int content;
    private String identification;
    private String registeraddress;
    private String phone;
    private String bankname;
    private String bankaccount;
    private Timestamp createdate;
    private String contentinfo;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getInvoicetype() {
        return invoicetype;
    }

    public void setInvoicetype(int invoicetype) {
        this.invoicetype = invoicetype;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getRegisteraddress() {
        return registeraddress;
    }

    public void setRegisteraddress(String registeraddress) {
        this.registeraddress = registeraddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public String getContentinfo() {
        return contentinfo;
    }

    public void setContentinfo(String contentinfo) {
        this.contentinfo = contentinfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
