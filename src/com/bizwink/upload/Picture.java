package com.bizwink.upload;

import java.sql.Timestamp;

/**
 * <p>Title: Picture.java</p>
 * <p>Description: Picture</p>
 * <p>Copyright: Copyright (c) 2003 ejbchina inc</p>
 * <p>Company: ejbchina</p>
 *
 * @author Oliver Hu
 * @version 2.0
 */

public class Picture {
  private int id = 0;
  private String imgDir = "";
  private String imgfile = "";
  private int width = 0;
  private int height = 0;
  private long size = 0;
  private Timestamp createdate;
  private int cid = 0;
  private int siteid = 0;
  private String editor = "";

  public int getSiteID() {
    return this.siteid;
  }

  public void setSiteID(int si) {
    this.siteid = si;
  }

  public long getSize() {
    return this.size;
  }

  public void setSize(long s) {
    this.size = s;
  }

  public int getColumnID() {
    return this.cid;
  }

  public void setColumnID(int ci) {
    this.cid = ci;
  }

  public int getID() {
    return this.id;
  }

  public void setID(int i) {
    this.id = i;
  }

  public Timestamp getCreateDate() {
    return this.createdate;
  }

  public void setCreateDate(Timestamp cd) {
    this.createdate = cd;
  }

  public String getImgDir() {
    return imgDir;
  }

  public void setImgDir(String id) {
    this.imgDir = id;
  }

  public void setImgfile(String imgfile) {
    this.imgfile = imgfile;
  }

  public String getImgfile() {
    return imgfile;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getWidth() {
    return width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getHeight() {
    return height;
  }

  public Picture(String imgDir, String imgfile) {
    this.imgDir = imgDir;
    this.imgfile = imgfile;
  }

  public Picture() {
  }

  public String getImgPrefix() {
    ImageFilter imageFilter = new ImageFilter();
    int length = imgfile.length() - imageFilter.getPostfixname(imgfile).length();
    return imgfile.substring(0, length);
  }

  public String getEditor() {
    return editor;
  }

  public void setEditor(String editor) {
    this.editor = editor;
  }
}
