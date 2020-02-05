package com.bizwink.cms.markManager;

import java.sql.*;

public class mark
{
  private int ID;
  private int columnID;
  private int siteID;
  private String content;
  private int markType;
  private String notes;
  private String lockEditor;
  private int lockFlag;
  private int pubFlag;
  private int innerHTMLFlag;
  private int formatFileNum;
  private Timestamp createDate;
  private Timestamp updateDate;
  private Timestamp publishTime;
	private String chineseName;
  private String relatedColumnID;

  public int getID(){
    return ID;
  }

  public void setID(int ID){
    this.ID = ID;
  }

  public int getColumnID() {
    return columnID;
  }

  public void setColumnID(int columnID) {
    this.columnID = columnID;
  }

  public int getSiteID() {
    return siteID;
  }

  public void setSiteID(int siteID) {
    this.siteID = siteID;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getMarkType() {
    return markType;
  }

  public void setMarkType(int markType) {
    this.markType = markType;
  }

  public String getChineseName() {
    return chineseName;
  }

  public void setChinesename(String chineseName) {
    this.chineseName = chineseName;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getLockEditor() {
    return lockEditor;
  }

  public void setLockEditor(String lockEditor) {
    this.lockEditor = lockEditor;
  }

  public int getLockFlag() {
    return lockFlag;
  }

  public void setLockFlag(int lockFlag) {
    this.lockFlag = lockFlag;
  }

  public int getPubFlag() {
    return pubFlag;
  }

  public void setPubFlag(int pubFlag) {
    this.pubFlag = pubFlag;
  }

  public int getInnerHTMLFlag() {
    return innerHTMLFlag;
  }

  public void setInnerHTMLFlag(int innerHTMLFlag) {
    this.innerHTMLFlag = innerHTMLFlag;
  }

  public int getFormatFileNum() {
    return formatFileNum;
  }

  public void setFormatFileNum(int formatFileNum) {
    this.formatFileNum = formatFileNum;
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
  }

  public Timestamp getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Timestamp updateDate) {
    this.updateDate = updateDate;
  }

  public Timestamp getPublishTime() {
    return publishTime;
  }

  public void setPublishTime(Timestamp publishTime) {
    this.publishTime = publishTime;
  }

  public String getRelatedColumnID() {
    return relatedColumnID;
  }

  public void setRelatedColumnID(String relatedColumnID) {
    this.relatedColumnID = relatedColumnID;
  }
}