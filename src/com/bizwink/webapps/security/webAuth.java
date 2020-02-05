package com.bizwink.webapps.security;

import java.util.*;

public class webAuth {
  private String userID;
  private String sitename;
  private int siteid;
  private webPermissionSet permissionSet;
  private int imgSaveFlag;
  private int showArtFlag;

  public webAuth(String userID, String sitename, int siteid, int imgFlag, int artFlag, webPermissionSet permissionSet) {
    this.userID = userID;
    this.siteid = siteid;
    this.sitename = sitename;
    this.imgSaveFlag = imgFlag;
    this.showArtFlag = artFlag;
    this.permissionSet = permissionSet;
  }

  /**
   * Returns the userID associated with this Authorization.
   */
  public String getUserID() {
    return userID;
  }

  public String getSitename() {
    return sitename;
  }

  public int getSiteID() {
    return siteid;
  }

  public void setSiteID(int siteid) {
    this.siteid = siteid;
  }

  public int getImgSaveFlag() {
    return imgSaveFlag;
  }

  public void setImgSaveFlag(int flag) {
    this.imgSaveFlag = flag;
  }

  public int getShowArtFlag() {
    return showArtFlag;
  }

  public void setShowArtFlag(int flag) {
    this.showArtFlag = flag;
  }

  public webPermissionSet getPermissionSet() {
    return permissionSet;
  }
}