package com.bizwink.cms.viewFileManager;

import java.sql.*;

public class ViewFile
{
    private int ID;                    //编号
    private int siteID;                //格式文件所属的站点
    private int type;                  //格式文件类型
    private String content;            //格式文件内容
    private String chinesename;        //格式文件中文描述
    private String editor;             //当前修改格式文件的人，如无人修改该格式文件，设置为空
    private int lockflag;              //格式文件被人编辑设置为1，否则设置为0
    private String notes;              //格式文件注释
    private Timestamp createDate;      //入库日期
    private Timestamp lastUpdated;     //修改日期

    public ViewFile()
    {
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public int getSiteID()
    {
        return siteID;
    }

    public void setSiteID(int siteID)
    {
        this.siteID  = siteID;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type  = type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getChineseName()
    {
        return chinesename;
    }

    public void setChineseName(String chinesename)
    {
        this.chinesename  = chinesename;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes  = notes;
    }

    public String getEditor()
    {
        return editor;
    }

    public void setEditor(String editor)
    {
        this.editor = editor;
    }

    public int getLockFlag()
    {
        return lockflag;
    }

    public void setLockFlag(int lockflag)
    {
        this.lockflag  = lockflag;
    }

    public Timestamp getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate)
    {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }
}
