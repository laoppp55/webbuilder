package com.bizwink.cms.business.Other;

import java.sql.*;

public class ReceiveMoney
{
  private int id;
  private String describe;
  private String userid;
  private String payer;
  private String jing_ban_ren;
  private int payway;
  private float number;
  private String unit;
  private Timestamp createdate;
  private String username;
  private String realname;
  private int siteid;
  private int orderid;

  public ReceiveMoney(){
  }

  //for id
  public int getID(){
    return id;
  }
  public void setID(int id){
    this.id=id;
  }

  //for describe
  public String getDescribe(){
    return describe;
  }
  public void setDescribe(String describe){
    this.describe=describe;
  }

  //for userid
  public String getUserID(){
    return userid;
  }
  public void setUserID(String userid){
    this.userid=userid;
  }

  //for payer
  public String getPayer(){
    return payer;
  }
  public void setPayer(String payer){
   this.payer=payer;
  }

  //for jingbanren
  public String getJingBanRen(){
    return jing_ban_ren;
  }
  public void setJingBanRen(String jing_ban_ren){
    this.jing_ban_ren=jing_ban_ren;
  }

  //for payway
  public int getPayway(){
    return payway;
  }
  public void setPayway(int payway){
    this.payway=payway;
  }

  //for number
  public float getNumber(){
    return number;
  }
  public void setNumber(float number){
    this.number=number;
  }

  //for unit
  public String getUnit(){
    return unit;
  }
  public void setUnit(String unit){
    this.unit=unit;
  }

  //for createdate
  public Timestamp getCreatedate(){
    return createdate;
  }
  public void setCreatedate(Timestamp createdate){
    this.createdate=createdate;
  }

  //for username
  public String getUserName(){
    return username;
  }
  public void setUserName(String username){
    this.username=username;
  }

  //for realname
  public String getRealName(){
    return realname;
  }
  public void setRealName(String realname){
    this.realname=realname;
  }

  //for siteid
  public int getSiteID(){
    return siteid;
  }
  public void setSiteID(int siteid){
    this.siteid=siteid;
  }

  public int getOrderid()
  {
    return orderid;
  }

  public void setOrderid(int orderid)
  {
    this.orderid = orderid;
  }
}
