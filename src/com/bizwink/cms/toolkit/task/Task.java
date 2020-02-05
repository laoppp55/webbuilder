//Compile by dzq.June 19,2003
// Source File Name:   Subscriber.java
package com.bizwink.cms.toolkit.task;

import java.sql.Timestamp;

public class Task
{
  private int activityid;
  private String subject;
  private String memberid;
  private Timestamp beginDate;
  private Timestamp endDate;
  private int status;
  private String comments;
  private Timestamp creationDate;
  private Timestamp modifiedDate;

  public Task()
  {
  }

  //for activityid
  public int getActivityID()
  {
      return activityid;
  }

  public void setActivityID(int activityid)
  {
      this.activityid = activityid;
  }

  //for subject
  public String getSubject(){
    return subject;
  }

  public void setSubject(String subject){
    this.subject = subject;
  }

  //for memberid
  public String getMemberID(){
    return memberid;
  }

  public void setMemberID(String memberid){
    this.memberid = memberid;
  }

  //for beginDate
  public Timestamp getBeginDate(){
    return beginDate;
  }

  public void setBeginDate(Timestamp beginDate){
    this.beginDate = beginDate;
  }

  //for endDate
  public Timestamp getEndDate(){
    return endDate;
  }

  public void setEndDate(Timestamp endDate){
    this.endDate = endDate;
  }

  //for status
  public int getStatus(){
    return status;
  }

  public void setStatus(int status){
    this.status = status;
  }

  //for creationDate
  public Timestamp getCreationDate(){
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate){
    this.creationDate = creationDate;
  }

  //for modifiedDate
  public Timestamp getModifiedDate(){
    return modifiedDate;
  }

  public void setModifiedDate(Timestamp modifiedDate){
    this.modifiedDate = modifiedDate;
  }

  //for comments
  public String getComments(){
    return comments;
  }

  public void setComments(String comments){
    this.comments = comments;
  }
}

