package com.bizwink.cms.news;

import java.sql.Timestamp;

public class Authorized
{
  private int id;
  private int siteid;
  private int columnid;
  private int articleid;
  private String targetid;
  private int type;
  private Timestamp createdate;

  public int getId()
  {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSiteid() {
    return this.siteid;
  }

  public void setSiteid(int siteid) {
    this.siteid = siteid;
  }

  public int getColumnid() {
    return this.columnid;
  }

  public void setColumnid(int columnid) {
    this.columnid = columnid;
  }

  public int getArticleid() {
    return this.articleid;
  }

  public void setArticleid(int articleid) {
    this.articleid = articleid;
  }

  public String getTargetid() {
    return this.targetid;
  }

  public void setTargetid(String targetid) {
    this.targetid = targetid;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public Timestamp getCreatedate() {
    return this.createdate;
  }

  public void setCreatedate(Timestamp createdate) {
    this.createdate = createdate;
  }
}