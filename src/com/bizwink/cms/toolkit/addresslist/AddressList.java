//Compile by dzq.June 19,2003
// Source File Name:   Subscriber.java
package com.bizwink.cms.toolkit.addresslist;

import java.sql.Timestamp;

public class AddressList
{
  private int id;
  private String memberid;
  private String name;
  private String sex;
  private String email;
  private String corporation;
  private String address;
  private String phone;
  private String fax;
  private String mobilephone;
  private String postcode;
  private long writedate;

  private String sender;
  private String receiver;
  private String content;
  private long senddate;

  public AddressList()
  {
  }

  //for id
  public int getID()
  {
      return id;
  }

  public void setID(int id)
  {
      this.id = id;
  }

  //for memberid
  public String getMemberID(){
    return memberid;
  }

  public void setMemberID(String memberid){
    this.memberid = memberid;
  }

  //for name
  public String getName()
  {
      return name;
  }

  public void setName(String name)
  {
      this.name = name;
  }

  //for sex
  public String getSex()
  {
      return sex;
  }

  public void setSex(String sex)
  {
      this.sex = sex;
  }

  //for corporation
  public String getCorporation()
  {
      return corporation;
  }

  public void setCorporation(String corporation)
  {
      this.corporation = corporation;
  }

  //for phone
  public String getPhone()
  {
      return phone;
  }

  public void setPhone(String phone)
  {
      this.phone = phone;
  }

  //for email
  public String getEmail()
  {
      return email;
  }

  public void setEmail(String email)
  {
      this.email = email;
  }


  //for writerdate
  public long getWriteDate()
  {
      return writedate;
  }

  public void setWriteDate(long writedate)
  {
      this.writedate = writedate;
  }

  //for address
  public String getAddress()
  {
      return address;
  }

  public void setAddress(String address)
  {
      this.address = address;
  }

  //for fax
  public String getFax(){
    return fax;
  }

  public void setFax(String fax){
    this.fax = fax;
  }

  //for mobilephone
  public String getMobilephone(){
    return mobilephone;
  }

  public void setMobilephone(String mobilephone){
    this.mobilephone = mobilephone;
  }

  //for postcode
  public String getPostCode(){
    return postcode;
  }

  public void setPostCode(String postcode){
    this.postcode = postcode;
  }

  //for sender
  public String getSender(){
    return sender;
  }

  public void setSender(String sender){
    this.sender = sender;
  }

  //for receiver
  public String getReceiver(){
    return receiver;
  }

  public void setReceiver(String receiver){
    this.receiver = receiver;
  }

  //for content
  public String getContent(){
    return content;
  }

  public void setContent(String content){
    this.content = content;
  }

  //for senddate
  public long getSendDate(){
    return senddate;
  }

  public void setSendDate(long senddate){
    this.senddate = senddate;
  }
}

