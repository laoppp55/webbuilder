package com.bizwink.cms.business.Other;

import java.sql.*;

public class Other
{
  private int gh_ID;
  private String gh_kind;
  private String gh_name;
  private String gh_lianxiren;
  private String gh_address;
  private String gh_postcode;
  private String gh_phone;
  private String gh_email;
  private String gh_notes;
  private int gh_siteid;
  private String userid;
  private String username;
  private String realname;
  private float totalfee;
  private long number;

  public Other(){
  }

  //for id
  public int getGH_ID(){
    return gh_ID;
  }
  public void setGH_ID(int id){
    this.gh_ID=id;
  }
//for kind
  public String getGH_Kind(){
    return gh_kind;
  }
  public void setGH_Kind(String kind){
    this.gh_kind=kind;
  }
//for name
  public String getGH_Name(){
    return gh_name;
  }
  public void setGH_Name(String name){
    this.gh_name=name;
  }
//for lianxiren
  public String getGH_Lianxiren(){
    return gh_lianxiren;
  }
  public void setGH_Lianxiren(String lianxiren){
    this.gh_lianxiren=lianxiren;
  }
//for address
  public String getGH_Address(){
    return gh_address;
  }
  public void setGH_Adderss(String address){
    this.gh_address=address;
  }
//for postcode
  public String getGH_Postcode(){
    return gh_postcode;
  }
  public void setGH_Postcode(String postcode){
    this.gh_postcode=postcode;
  }
//for phone
  public String getGH_Phone(){
    return gh_phone;
  }
  public void setGH_Phone(String phone){
    this.gh_phone=phone;
  }
//for email
  public String getGH_Email(){
    return gh_email;
  }
  public void setGH_Email(String email){
    this.gh_email=email;
  }
//for notes
  public String getGH_Notes(){
    return gh_notes;
  }
  public void setGH_Notes(String notes){
    this.gh_notes=notes;
  }
//for siteid
  public int getGH_Siteid(){
    return gh_siteid;
  }
  public void setGH_Siteid(int siteid){
    this.gh_siteid=siteid;
  }

//for userid
  public String getUserid(){
    return userid;
  }
  public void setUserid(String userid){
    this.userid=userid;
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

//for float
  public float getTotalFee(){
    return totalfee;
  }
  public void setTotalFee(float totalfee){
    this.totalfee=totalfee;
  }

//for number
  public long getNumber(){
    return number;
  }
  public void setNumber(long number){
    this.number=number;
  }

}
