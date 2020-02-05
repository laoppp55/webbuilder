/**
 * TaskImpl.java
 */

package com.bizwink.wenba;

import java.sql.*;

public class wenbaImpl implements wenba{
    private int ID;             //是否完成
    private int siteid;
    private int parentid;
    private int orderid;
    private String cname;
    private String ename;
    private int status;
    private String dirname;
    private Timestamp createdate; //入库日期
    private String creator;         //创建者

    /**
     * Returns the closed of the task.
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets the closed of the task.
     */
    public void setID(int id) {
        this.ID = id;
    }

    /**
     * Returns the comments of the task.
     */
    public int getSiteID() {
        return siteid;
    }

    /**
     * Sets the comments of the task.
     */
    public void setSiteID(int siteid) {
        this.siteid = siteid;
    }

    /**
     * Returns the creatorID of the task.
     */
    public int getParentID() {
        return parentid;
    }

    /**
     * Sets the creator of the task.
     */
    public void setParentID(int parentid) {
        this.parentid = parentid;
    }

    /**
     * Returns the creatorID of the task.
     */
    public int getOrderID() {
        return orderid;
    }

    /**
     * Sets the creator of the task.
     */
    public void setOrderID(int orderid) {
        this.orderid = orderid;
    }

    public String getDirName() {
        return dirname;
    }

    public void setDirName(String dirName) {
        this.dirname = dirName;
    }

    public String getCName() {
        return cname;
    }

    public void setCName(String CName) {
        this.cname = CName;
    }

    public String getEName() {
        return ename;
    }

    public void setEName(String EName) {
        this.ename = EName;
    }

    /**
     * Returns the creator of the task.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets the creator of the task.
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * Returns the creator of the task.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the creator of the task.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns the creation date of the task.
     */
    public Timestamp getCreationDate() {
        return createdate;
    }

    /**
     * Sets the creation date of the task.
     */
    public void setCreationDate(Timestamp creationDate) {
        this.createdate = creationDate;
    }
}