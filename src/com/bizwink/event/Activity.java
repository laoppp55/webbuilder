/**
 * Activity.java
 */

package com.bizwink.event;

import java.sql.*;

public interface Activity{
    /**
     * Returns the id of the activity.
     */
    int getID();

    /**
     * set the id of the activity.
     */
    void setID(int id);

    /**
     * Returns the type of the activity, subject or event
     */
    int getType();

    /**
     * set the type of the activity.
     */
    void setType(int type);

    /**
     * Returns the subject of the activity.
     */
    String getSubject();

    /**
     * set the subject of the activity.
     */
    void setSubject(String subject);

    /**
     * Returns the duedate of the activity.
     */
    Timestamp getDuedate();

    /**
     * set the duedate of the activity.
     */
    void setDuedate(Timestamp duedate);

    /**
     * Returns the assigned person of the activity.
     */
    String getAssign();

    /**
     * set the the assigned person of the activity.
     */
    void setAssign(String assign);

    /**
     * Returns the priority of the activity.
     */
    String getPriority();

    /**
     * set the priority of the activity.
     */
    void setPriority(String priority);

    /**
     * Returns the status of the activity.
     */
    String getStatus();

    /**
     * set the status of the activity.
     */
    void setStatus(String status);

    /**
     * Returns the leadID of the activity.
     */
    int getLeadID();

    /**
     * Sets the leadID of the activity.
     */
    void setLeadID(int leadID);

    /**
     * Returns the leadName of the activity.
     */
    public String getLeadName();

    /**
     * Sets the lead of the activity.
     */
    public void setLeadName(String leadName);


    /**
     * Returns the accountID of the activity.
     */
    int getAccountID();

    /**
     * Sets the accountID of the activity.
     */
    void setAccountID(int accountID);

    /**
     * Returns the accountName of the activity.
     */
    public String getAccountName();

    /**
     * Sets the account of the activity.
     */
    public void setAccountName(String accountName);

    /**
     * Returns the opportunityID of the activity.
     */
    int getOpportunityID();

    /**
     * Sets the opportunityID of the activity.
     */
    void setOpportunityID(int opportunityID);

    /**
     * Returns the opportunity of the activity.
     */
    public String getOpportunityName();

    /**
     * Sets the opportunity of the activity.
     */
    public void setOpportunityName(String opportunityName);

    /**
     * Returns the contactID of the activity.
     */
    int getContactID();

    /**
     * Sets the contactID of the activity.
     */
    void setContactID(int contactID);

    /**
     * Returns the contact of the activity.
     */
    public String getContactName();

    /**
     * Sets the contact of the activity.
     */
    public void setContactName(String contactName);

    /**
     * Returns the ownerID of the activity.
     */
    int getOwnerID();

    /**
     * Sets the ownerID of the activity.
     */
    void setOwnerID(int ownerID);

    /**
     * Returns the owner of the activity.
     */
    String getOwner();

    /**
     * Sets the owner of the activity.
     */
    void setOwner(String owner);

    /**
     * Returns the Date that the activity was modified.
     */
    Timestamp getModifiedDate();

    /**
     * Sets the modified date of the activity.
     */
    void setModifiedDate(Timestamp modifiedDate);
}