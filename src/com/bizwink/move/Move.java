package com.bizwink.move;

import java.util.*;

public class Move
{
  private static int aimColumnID;
  private static int orgColumnID;
  private static String username;
  private static String appPath;
  private static int moveType;
  private static int isMovePic;
  private static int siteID;
  private static String dirName;
  private static List articleList;
  private static String siteName;

  public void setAimColumnID(int aimColumnID)
  {
    this.aimColumnID = aimColumnID;
  }

  public int getAimColumnID()
  {
    return aimColumnID;
  }

  public void setOrgColumnID(int orgColumnID)
  {
    this.orgColumnID = orgColumnID;
  }

  public int getOrgColumnID()
  {
    return orgColumnID;
  }

  public void setUserName(String username)
  {
    this.username = username;
  }

  public String getUserName()
  {
    return username;
  }

  public void setAppPath(String appPath)
  {
    this.appPath = appPath;
  }

  public String getAppPath()
  {
    return appPath;
  }

  public void setMoveType(int moveType)
  {
    this.moveType = moveType;
  }

  public int getMoveType()
  {
    return moveType;
  }

  public void setIsMovePic(int isMovePic)
  {
    this.isMovePic = isMovePic;
  }

  public int getIsMovePic()
  {
    return isMovePic;
  }

  public void setSiteID(int siteID)
  {
    this.siteID = siteID;
  }

  public int getSiteID()
  {
    return siteID;
  }

  public void setDirName(String dirName)
  {
    this.dirName = dirName;
  }

  public String getDirName()
  {
    return dirName;
  }

  public void setArticleList(List articleList)
  {
    this.articleList = articleList;
  }

  public List getArticleList()
  {
    return articleList;
  }

  public void setSiteName(String siteName)
  {
    this.siteName = siteName;
  }

  public String getSiteName()
  {
    return siteName;
  }
}
