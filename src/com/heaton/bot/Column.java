package com.heaton.bot;

import java.sql.*;

public class Column
{
    private int ID;
    private int siteid;                            //站点编号
    private String dirName;                        //发布后的目录名
    private int orderID;                           //显示次序
    private int parentID;                          //default 0,父编号
    private int defineAttr;                        //栏目是否有自定义属性 0--没有  1--有
    private int hasArticleModel;                   //栏目是否有文章模板  0--没有   1--有
    private String CName;                          //chinese name
    private String EName;                          //english name
    private String extname;                        //生成文件的扩展名
    private Timestamp createDate;                  //创建时间(sysdate)
    private Timestamp lastUpdated;                 //最后修改时间(sysdate)
    private String editor;                         //最后修改者，FK
    private String xmlTemplate;                    //扩展属性模板
    private int isAudited;                         //是否需要审核
    private String desc;                           //栏目描述
    private int isProduct;                         //栏目属性
    private int isPublishMore;                     //是否发布多个文章模板
    private int languageType;                      //栏目语言类型 0-简体中文 1-繁体中文 2-日文
    private int isPutAD;
    private int isRss;
    private int getRssArticleTime;

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
        return siteid;
    }

    public void setSiteID(int siteID)
    {
        this.siteid  = siteID;
    }

    public void setDefineAttr(int defineAttr)
    {
        this.defineAttr = defineAttr;
    }

    public int getDefineAttr()
    {
        return defineAttr;
    }

    public void setHasArticleModel(int hasArticleModel)
    {
        this.hasArticleModel = hasArticleModel;
    }

    public int getHasArticleModel()
    {
        return hasArticleModel;
    }

    public String getDirName()
    {
        return dirName;
    }

    public void setDirName(String dirName)
    {
        this.dirName = dirName;
    }

    public int getOrderID()
    {
        return orderID;
    }

    public void setOrderID(int orderID)
    {
        this.orderID = orderID;
    }

    public int getParentID()
    {
        return parentID;
    }

    public void setParentID(int parentID)
    {
        this.parentID = parentID;
    }

    public String getCName()
    {
        return CName;
    }

    public void setCName(String CName)
    {
        this.CName = CName;
    }

    public String getEName()
    {
        return EName;
    }

    public void setEName(String EName)
    {
        this.EName = EName;
    }

    public String getExtname()
    {
        return extname;
    }

    public void setExtname(String extname)
    {
        this.extname  = extname;
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

    public String getEditor()
    {
        return editor;
    }

    public void setEditor(String editor)
    {
        this.editor = editor;
    }

    public void setXMLTemplate(String xmlTemplate)
    {
        this.xmlTemplate = xmlTemplate;
    }

    public String getXMLTemplate()
    {
        return xmlTemplate;
    }

    public int getIsAudited()
    {
        return isAudited;
    }

    public void setIsAudited(int isAudited)
    {
        this.isAudited = isAudited;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getDesc()
    {
        return desc;
    }

    public int getIsProduct()
    {
        return isProduct;
    }

    public void setIsProduct(int isProduct)
    {
        this.isProduct = isProduct;
    }

    public int getIsPublishMoreArticleModel()
    {
        return isPublishMore;
    }

    public void setIsPublishMoreArticleModel(int isPublishMore)
    {
        this.isPublishMore = isPublishMore;
    }

    public int getLanguageType()
    {
        return languageType;
    }

    public void setLanguageType(int languageType)
    {
        this.languageType = languageType;
    }

    public int getPutAD()
    {
        return isPutAD;
    }

    public void setPutAD(int putAD)
    {
        isPutAD = putAD;
    }

    public int getRss() {
        return isRss;
    }

    public void setRss(int rss) {
        isRss = rss;
    }

    public int getGetRssArticleTime() {
        return getRssArticleTime;
    }

    public void setGetRssArticleTime(int getRssArticleTime) {
        this.getRssArticleTime = getRssArticleTime;
    }
}