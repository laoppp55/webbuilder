package com.bizwink.cms.register;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class markColumnRelation {
    private int ID;               //唯一性ID
    private int siteID;           //标记所属的站点ID
    private int mid;              //标记ID
    private int cid;              //与标记相关的栏目ID

    public markColumnRelation() {

    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public int getSiteID(){
        return siteID;
    }

    public void setSiteID(int siteID){
        this.siteID = siteID;
    }

    public int getMid(){
        return mid;
    }

    public void setMid(int mid){
        this.mid = mid;
    }

    public int getCid(){
        return cid;
    }

    public void setCid(int cid){
        this.cid = cid;
    }
}