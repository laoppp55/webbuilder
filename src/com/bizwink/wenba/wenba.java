/**
 * Task.java
 */

package com.bizwink.wenba;

import java.sql.*;

public interface wenba {
    /**
     * Returns the closed of the task.
     */
    int getID();

    /**
     * Sets the closed of the task.
     */
    void setID(int id);

    /**
     * Returns the comments of the task.
     */
    int getSiteID();

    /**
     * Sets the comments of the task.                                                 ;
     */
    void setSiteID(int siteid);

    int getParentID();

    void setParentID(int parentid);

    int getOrderID();

    void setOrderID(int orderid);

    String getDirName();

    void setDirName(String dirName);

    String getCName();

    void setCName(String CName);

    String getEName();

    void setEName(String EName);

    int getStatus();

    void setStatus(int status);
    /**
     * Returns the creator of the task.
     */
    String getCreator();

    /**
     * Sets the creator of the task.
     */
    void setCreator(String creator);

    /**
     * Returns the Date that the task was created.
     */
    Timestamp getCreationDate();

    /**
     * Sets the creation date of the task.
     */
    void setCreationDate(Timestamp creationDate);
}