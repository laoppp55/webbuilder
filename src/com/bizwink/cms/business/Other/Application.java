package com.bizwink.cms.business.Other;

import java.sql.*;

public class Application{
  private int id;
  private int userid;
  private int orderid;
  private int productid;
  private int repairflag;
  private int exchangeflag;
  private int backflag;
  private String notes;
  private String username;
  private String productname;
  private Timestamp createdate;
  private Timestamp dealdate;
  private int siteid;

  public Application(){
  }
  //for id
  public int getID(){
    return id;
  }
  public void setID(int id){
    this.id=id;
  }
  //for userid
  public int getUserID(){
    return userid;
  }
  public void setUserID(int userid){
    this.userid=userid;
  }
  //for orderid;
  public int getOrderID(){
    return orderid;
  }
  public void setOrderID(int orderid){
    this.orderid=orderid;
  }
  //for productid
  public int getProductID(){
    return productid;
  }
  public void setProductID(int productid){
    this.productid=productid;
  }
  //for repairflag
  public int getRepairFlag(){
    return repairflag;
  }
  public void setRegpairFlag(int repairflag){
    this.repairflag=repairflag;
  }
  //for exchangeflag
  public int getExchangeFlag(){
    return exchangeflag;
  }
  public void setExchangeFlag(int exchangeflag){
    this.exchangeflag=exchangeflag;
  }
  //for backflag
  public int getBackFlag(){
    return backflag;
  }
  public void setBackFlag(int backflag){
    this.backflag=backflag;
  }
  //for notes
  public String getNotes(){
    return notes;
  }
  public void setNotes(String notes){
    this.notes=notes;
  }
  //for username
  public String getUserName(){
    return username;
  }
  public void setUserName(String username){
    this.username=username;
  }
  //for productname
  public String getProductName(){
    return productname;
  }
  public void setProductName(String productname){
    this.productname=productname;
  }
  //for createdate
  public Timestamp getCreatedate(){
    return createdate;
  }
  public void setCreatedate(Timestamp createdate){
    this.createdate=createdate;
  }
  //for dealdate
  public Timestamp getDealdate(){
    return dealdate;
  }
  public void setDealdate(Timestamp dealdate){
    this.dealdate=dealdate;
  }
  //for siteid
  public int getSiteID(){
    return siteid;
  }
  public void getSiteID(int siteid){
    this.siteid=siteid;
  }
}

