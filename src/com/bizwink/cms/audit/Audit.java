package com.bizwink.cms.audit;

import java.sql.Timestamp;

public class Audit
{
  private int ID;
  private int columnID;
  private int articleID;
  private String userID;
  private String auditRules;
  private String creator;
  private String editor;
  private String auditor;
  private String sign;
  private String comments;
  private String backTo;
  private String mainTitle;
  private int status;
  private Timestamp createDate;
  private Timestamp lastUpdated;
    private int audittype;

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

  public void setID(int ID)
  {
    this.ID = ID;
  }

  public int getID()
  {
    return ID;
  }

  public void setColumnID(int columnID)
  {
    this.columnID = columnID;
  }

  public int getColumnID()
  {
    return columnID;
  }

  public void setArticleID(int articleID)
  {
    this.articleID = articleID;
  }

  public int getArticleID()
  {
    return articleID;
  }

  public void setAuditRules(String auditRules)
  {
    this.auditRules = auditRules;
  }

  public String getAuditRules()
  {
    return auditRules;
  }

  public void setCreator(String creator)
  {
    this.creator = creator;
  }

  public String getCreator()
  {
    return creator;
  }

  public void setEditor(String editor)
  {
    this.editor = editor;
  }

  public String getEditor()
  {
    return editor;
  }

  public void setCreateDate(Timestamp createDate)
  {
    this.createDate = createDate;
  }

  public Timestamp getCreateDate()
  {
    return createDate;
  }

  public void setLastUpdated(Timestamp lastUpdated)
  {
    this.lastUpdated = lastUpdated;
  }

  public Timestamp getLastUpdated()
  {
    return lastUpdated;
  }

  public void setAuditor(String auditor)
  {
    this.auditor = auditor;
  }

  public String getAuditor()
  {
    return auditor;
  }

  public void setSign(String sign)
  {
    this.sign = sign;
  }

  public String getSign()
  {
    return sign;
  }

  public void setComments(String comments)
  {
    this.comments = comments;
  }

  public String getComments()
  {
    return comments;
  }

  public void setBackTo(String backTo)
  {
    this.backTo = backTo;
  }

  public String getBackTo()
  {
    return backTo;
  }

  public void setStatus(int status)
  {
    this.status = status;
  }

  public int getStatus()
  {
    return status;
  }

  public void setMainTitle(String mainTitle)
  {
    this.mainTitle = mainTitle;
  }

  public String getMainTitle()
  {
    return mainTitle;
  }

    public int getAudittype() {
        return audittype;
    }

    public void setAudittype(int audittype) {
        this.audittype = audittype;
    }
}