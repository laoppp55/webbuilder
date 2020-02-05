/**
 * TaskImpl.java
 */

package com.bizwink.event;

import java.sql.*;

public class TaskImpl extends ActivityImpl implements Task{
    private int closed;             //是否完成
    private String comments;        //注释
    private Timestamp creationDate; //入库日期
    private int creatorID;          //创建者ID
    private int modifierID;         //修改者ID
    private String creator;         //创建者
    private String modifier;        //修改者

    /**
     * Returns the closed of the task.
     */
    public int getClosed() {
        return closed;
    }

    /**
     * Sets the closed of the task.
     */
    public void setClosed(int closed) {
        this.closed = closed;
    }

    /**
     * Returns the comments of the task.
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the comments of the task.
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Returns the creatorID of the task.
     */
    public int getCreatorID() {
        return creatorID;
    }

    /**
     * Sets the creator of the task.
     */
    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
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
     * Returns the modifierID of the task.
     */
    public int getModifierID() {
        return modifierID;
    }

    /**
     * Sets the modifierID of the task.
     */
    public void setModifierID(int modifierID) {
        this.modifierID = modifierID;
    }

    /**
     * Returns the modifier of the task.
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * Sets the modifier of the task.
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * Returns the creation date of the task.
     */
    public Timestamp getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the task.
     */
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}