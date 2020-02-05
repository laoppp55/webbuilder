/**
 * EventImpl.java
 */

package com.bizwink.event;

import java.sql.*;

public class EventImpl extends ActivityImpl implements Event{
    private String location;        //地点
    private String date;            //日期, for edit
    private String time;            //时间, for edit
    private int durationHour=1;     //持续小时
    private int durationMinute=0;   //持续分钟
    private int isAllDay;           //是否整天
    private String comments;        //注释
    private Timestamp creationDate; //入库日期
    private int creatorID;          //创建者ID
    private int modifierID;         //修改者ID
    private String creator;         //创建者
    private String modifier;        //修改者


    /**
     * Returns the date of the event.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the event.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the time of the event.
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time of the event.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Returns the location of the event.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the event.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the durationHour of the event.
     */
    public int getDurationHour() {
        return durationHour;
    }

    /**
     * Sets the creator of the event.
     */
    public void setDurationHour(int durationHour) {
        this.durationHour = durationHour;
    }

    /**
     * Returns the durationMinute of the event.
     */
    public int getDurationMinute() {
        return durationMinute;
    }

    /**
     * Sets the creator of the event.
     */
    public void setDurationMinute(int durationMinute) {
        this.durationMinute = durationMinute;
    }

    /**
     * Returns the isAllDay of the event.
     */
    public int getIsAllDay() {
        return isAllDay;
    }

    /**
     * Sets the isAllDay of the event.
     */
    public void setIsAllDay(int isAllDay) {
        this.isAllDay = isAllDay;
    }

    /**
     * Returns the comments of the event.
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the comments of the event.
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Returns the creatorID of the event.
     */
    public int getCreatorID() {
        return creatorID;
    }

    /**
     * Sets the creator of the event.
     */
    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    /**
     * Returns the creator of the event.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets the creator of the event.
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }


    /**
     * Returns the modifierID of the event.
     */
    public int getModifierID() {
        return modifierID;
    }

    /**
     * Sets the modifierID of the event.
     */
    public void setModifierID(int modifierID) {
        this.modifierID = modifierID;
    }

    /**
     * Returns the modifier of the event.
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * Sets the modifier of the event.
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }


    /**
     * Returns the creation date of the event.
     */
    public Timestamp getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the event.
     */
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}