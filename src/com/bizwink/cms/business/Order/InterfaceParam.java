package com.bizwink.cms.business.Order;

import java.sql.Timestamp;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-10-31
 * Time: 16:37:42
 * To change this template use File | Settings | File Templates.
 */
public class InterfaceParam {
      private int id;
      private int paywayId;
      private String accountNumber;
      private  String paywayKey;
      private  String alipayAccount;
      private Timestamp createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaywayId() {
        return paywayId;
    }

    public void setPaywayId(int paywayId) {
        this.paywayId = paywayId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPaywayKey() {
        return paywayKey;
    }

    public void setPaywayKey(String paywayKey) {
        this.paywayKey = paywayKey;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
