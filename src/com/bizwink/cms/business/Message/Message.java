package com.bizwink.cms.business.Message;

import java.sql.*;

public class Message{

  private int id;
  private int siteid;
  private String send_user;
  private String receive_user;
  private String sendername;
  private String receivername;
  private String message;
  private Timestamp senddate;
  private int send_del;
  private int receive_del;
  private int flag;
  private int userid;

  public Message(){
  }
  //for id
  public int getID(){
    return id;
  }
  public void setID(int id){
    this.id=id;
  }
  //for siteid
  public int getSiteID(){
    return siteid;
  }
  public void setSiteID(int siteid){
    this.siteid=siteid;
  }
  //for send_user
  public String getSend_User(){
    return send_user;
  }
  public void setSend_User(String send_user){
    this.send_user=send_user;
  }
  //for receive_user
  public String getReceive_User(){
    return receive_user;
  }
  public void setReceive_User(String receive_user){
    this.receive_user=receive_user;
  }
  //for message
  public String getMessage(){
    return message;
  }
  public void setMessage(String message){
    this.message=message;
  }
  //for senddate
  public Timestamp getSendDate(){
    return senddate;
  }
  public void setSendDate(Timestamp senddate){
    this.senddate=senddate;
  }
  //for send_del
  public int getSendDel(){
    return send_del;
  }
  public void setSendDel(int send_del){
    this.send_del=send_del;
  }
  //for receive_del
  public int getReceiveDel(){
    return receive_del;
  }
  public void setReceiveDel(int receive_del){
    this.receive_del=receive_del;
  }
  /*for send_save
  public int getSend_Save(){
    return send_save;
  }
  public void setSend_Save(int send_save){
    this.send_save=send_save;
  }
  //for receive_save
  public int getReceive_Save(){
    return receive_save;
  }
  public void setRecevie_Save(int receive_save){
    this.receive_save=receive_save;
  }
  */
  //for flag
  public int getFlag(){
    return flag;
  }
  public void setFlag(int flag){
    this.flag=flag;
  }
  //for sendername
  public String getSenderName(){
    return sendername;
  }
  public void setSenderName(String sendername){
    this.sendername=sendername;
  }
  //for receivername
  public String getReceiverName(){
    return receivername;
  }
  public void setReceiverName(String receivername){
    this.receivername=receivername;
  }
  //for userid
  public int getUserID(){
    return userid;
  }
  public void setUserID(int userid){
    this.userid=userid;
  }
}