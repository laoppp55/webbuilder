package com.bizwink.cms.business.Other;

import java.sql.*;

public class Tongji
{
  private String userid;
  private Timestamp createdate;
  private float receivemoney;
  private int ordernum;
  private float totalmoney;
  private Timestamp begintime;
  private Timestamp endtime;
  private int counter;

  public Tongji(){
  }
  // for userid
  public String getUserID(){
    return userid;
  }
  public void setUserID(String userid){
    this.userid=userid;
  }
  // for createdate
  public Timestamp getCreatedate(){
    return createdate;
  }
  public void setCreatedate(Timestamp createdate){
    this.createdate=createdate;
  }
  // for receivemoney
  public float getReceiveMoney(){
    return receivemoney;
  }
  public void setReceiveMoney(float receivemoney){
    this.receivemoney=receivemoney;
  }
  //for ordernum
  public int getOrderNum(){
    return ordernum;
  }
  public void setOrderNum(int ordernum){
    this.ordernum = ordernum;
  }
  // for ordermoney
  public float getTotalMoney(){
    return totalmoney;
  }
  public void setTotalMoney(float totalmoney){
    this.totalmoney=totalmoney;
  }
  // for begintime
  public Timestamp getBegintime(){
    return begintime;
  }
  public void setBegintime(Timestamp begintime){
    this.begintime=begintime;
  }
  //for endtime
  public Timestamp getEndtime(){
    return endtime;
  }
  public void setEndtime(Timestamp endtime){
    this.endtime=endtime;
  }
  //for counter
  public int getCounter(){
    return counter;
  }
  public void setCounter(int counter){
    this.counter=counter;
  }
}
