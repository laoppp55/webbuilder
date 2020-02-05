package com.bizwink.cms.register;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class templateMarkRelation {
  private int id;
  private int tid;
  private int markid;
  private int siteid;

  public templateMarkRelation() {

  }

  public int getID() {
    return id;
  }

  public void setID (int id) {
    this.id = id;
  }

  public int getTID() {
    return tid;
  }

  public void setTID (int tid) {
    this.tid = tid;
  }

  public int getMarkID() {
    return markid;
  }

  public void setMarkID (int markid) {
    this.markid = markid;
  }

  public int getSiteID() {
    return siteid;
  }

  public void setSiteID (int siteid) {
    this.siteid = siteid;
  }
}