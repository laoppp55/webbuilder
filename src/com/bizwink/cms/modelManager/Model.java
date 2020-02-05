package com.bizwink.cms.modelManager;

import java.sql.*;

public class Model
{
    private int ID;                               //ID
    private int ColumnID;                         //ColumnID
    private int siteid;
    private String relatedColumnIDs;              //相关栏目
    private int IsArticle;                        //模板类型
    private String Content;                       //Content
    private Timestamp Createdate;                 //Createdate
    private Timestamp Lastupdated;                //Lastupdated
    private String Editor;                        //Editor
    private String Creator;                       //model's creator
    private int Lockstatus;                       //model's status
    private String Lockeditor;                    //模板管理者
    private String chname;                        //模板的中文名字
    private int defaultTemplate;                  //是否是默认的模板
    private String templateName;                  //模板文件名称
    private int pubflag;                          //发布标志位
    private int referModelID;                     //被引用的模板ID
    private String realpath;                      //webapp 的路径
    private String sitename;                      //站定名称
    private boolean publishArticle;               //是否需要重新发布文章
    private int isIncluded;                       //是否为包含文件
    private int tempnum;
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getColumnID() {
        return ColumnID;
    }

    public void setColumnID(int ColumnID) {
        this.ColumnID = ColumnID;
    }

    public int getSiteID() {
        return siteid;
    }

    public void setSiteID(int sid) {
        this.siteid = sid;
    }

    public int getPubFlag() {
        return pubflag;
    }

    public void setPubFlag(int pubflag) {
        this.pubflag = pubflag;
    }

    public String getRelatedColumnIDs() {
        return relatedColumnIDs;
    }

    public void setRelatedColumnIDs(String relatedColumnIDs) {
        this.relatedColumnIDs = relatedColumnIDs;
    }

    public int getIsArticle() {
        return IsArticle;
    }

    public void setIsArticle(int IsArticle) {
        this.IsArticle = IsArticle;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public Timestamp getCreatedate() {
        return Createdate;
    }

    public void setCreatedate(Timestamp Createdate) {
        this.Createdate = Createdate;
    }

    public Timestamp getLastupdated() {
        return Lastupdated;
    }

    public void setLastupdated(Timestamp Lastupdated) {
        this.Lastupdated = Lastupdated;
    }

    public String getEditor() {
        return Editor;
    }

    public void setEditor(String Editor) {
        this.Editor = Editor;
    }

    public void setCreator(String creator) {
        this.Creator = creator;
    }

    public String getCreator() {
        return Creator;
    }

    public void setLockstatus(int lockstatus) {
        this.Lockstatus = lockstatus;
    }

    public int getLockStatus() {
        return Lockstatus;
    }

    public void setChineseName(String name) {
        this.chname = name;
    }

    public String getChineseName() {
        return chname;
    }

    public void setDefaultTemplate(int defaultID) {
        this.defaultTemplate = defaultID;
    }

    public int getDefaultTemplate() {
        return defaultTemplate;
    }

    public void setLockEditor(String lockeditor) {
        this.Lockeditor = lockeditor;
    }

    public String getLockEditor() {
        return Lockeditor;
    }

    public String getTemplateName()
    {
        return templateName;
    }

    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }

    public int getReferModelID()
    {
        return referModelID;
    }

    public void setReferModelID(int referModelID)
    {
        this.referModelID = referModelID;
    }

    public void setRealPath(String realPath){
        this.realpath = realPath;
    }

    public String getRealPath(){
        return realpath;
    }

    public void setSiteName(String sitename){
        this.sitename = sitename;
    }

    public String getSiteName(){
        return sitename;
    }

    public void setPublishArticle(boolean publishArticle) {
        this.publishArticle = publishArticle;
    }

    public boolean getPublishArticle() {
        return publishArticle;
    }

    public int getIncluded() {
        return isIncluded;
    }

    public void setIncluded(int included) {
        isIncluded = included;
    }

    public int getTempnum() {
        return tempnum;
    }

    public void setTempnum(int tempnum) {
        this.tempnum = tempnum;
    }
}
