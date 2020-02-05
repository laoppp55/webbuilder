package com.bizwink.cms.toolkit;

import java.sql.Timestamp;

public class Comment
{
  private int id;
  private int siteID;
  private int articleID;
  private String username;
  private String email;
  private String title;
  private String content;
  private int level;
  private Timestamp createDate;
  private String mainTitle;
  private String cName;

  public void setID(int id)
  {
    this.id = id;
  }

  public int getID()
  {
    return id;
  }

  public void setSiteID(int siteID)
  {
    this.siteID = siteID;
  }

  public int getSiteID()
  {
    return siteID;
  }

  public void setArticleID(int articleID)
  {
    this.articleID = articleID;
  }

  public int getArticleID()
  {
    return articleID;
  }

  public void setLevel(int level)
  {
    this.level = level;
  }

  public int getLevel()
  {
    return level;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getUsername()
  {
    return username;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getEmail()
  {
    return email;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getTitle()
  {
    return title;
  }

  public void setContent(String content)
  {
    this.content = content;
  }

  public String getContent()
  {
    return content;
  }

  public void setCreateDate(Timestamp createDate)
  {
    this.createDate = createDate;
  }

  public Timestamp getCreateDate()
  {
    return createDate;
  }

  public void setMainTitle(String mainTitle)
  {
    this.mainTitle = mainTitle;
  }

  public String getMainTitle()
  {
    return mainTitle;
  }

  public void setCName(String cName)
  {
    this.cName = cName;
  }

  public String getCName()
  {
    return cName;
  }
}