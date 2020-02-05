package com.bizwink.cms.news;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 18-5-24.
 */
public class ColumnVO implements Serializable {
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
    private int isAudited;                         //是否需要审核
    private boolean isLeaf;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getDefineAttr() {
        return defineAttr;
    }

    public void setDefineAttr(int defineAttr) {
        this.defineAttr = defineAttr;
    }

    public int getHasArticleModel() {
        return hasArticleModel;
    }

    public void setHasArticleModel(int hasArticleModel) {
        this.hasArticleModel = hasArticleModel;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    public String getExtname() {
        return extname;
    }

    public void setExtname(String extname) {
        this.extname = extname;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getIsAudited() {
        return isAudited;
    }

    public void setIsAudited(int isAudited) {
        this.isAudited = isAudited;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
}
