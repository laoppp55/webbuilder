package com.bizwink.cms.viewFileManager;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-6-18
 * Time: 19:44:30
 * To change this template use File | Settings | File Templates.
 */
public class navigator {
    private int id;
    private String notes;
    private String cname;
    private String content;
    private String fcontent;
    private Timestamp createdate;

    public int getID()
    {
      return id;
    }

    public void setID(int ID)
    {
      this.id = ID;
    }


    public String getNotes()
    {
      return notes;
    }

    public void setNotes(String notes)
    {
      this.notes  = notes;
    }

    public Timestamp getCreateDate()
    {
      return createdate;
    }

    public void setCreateDate(Timestamp createDate)
    {
      this.createdate = createDate;
    }
    public String getName()
    {
      return cname;
    }

    public void setName(String chinesename)
    {
      this.cname  = chinesename;
    }
    public String getContent()
    {
      return content;
    }

    public void setContent(String content)
    {
      this.content = content;
    }

    public String getfContent()
    {
      return fcontent;
    }

    public void setfContent(String content)
    {
      this.fcontent = content;
    }

}
