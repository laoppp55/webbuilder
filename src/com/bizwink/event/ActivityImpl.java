/**
 * ActivityImpl.java
 */

package com.bizwink.event;

import java.sql.*;

public class ActivityImpl implements Activity{
    protected int id;                 //ID
    protected int type;               //类型：task or subject
    protected String subject;         //主题
    protected Timestamp  duedate;     //预期
    protected String  assign;        //委派人
    protected String priority;        //优先级
    protected String status;          //状态
    protected int    leadID;          //线索ID
    protected String leadName;        //线索名称
    protected int    accountID;       //客户ID
    protected String accountName;     //客户名称
    protected int    opportunityID;   //机会ID
    protected String opportunityName; //机会名称
    protected int    contactID;       //联系人ID
    protected String contactName;     //联系人名称
    protected Timestamp modifiedDate; //修改日期
    protected int ownerID;            //拥有者ID
    protected String owner;           //拥有者姓名
    /**
     * Returns the id of the activity.
     */
    public int getID() {
        return id;
    }

    /**
     * set the id of the activity.
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Returns the type of the activity.
     */
    public int getType() {
        return type;
    }

    /**
     * set the type of the activity.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Returns the subject of the activity.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * set the subject of the activity.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns the duedate of the activity.
     */
    public Timestamp getDuedate() {
        return duedate;
    }

    /**
     * set the duedate of the activity.
     */
    public void setDuedate(Timestamp duedate) {
        this.duedate = duedate;
    }

    /**
     * Returns the assigned person of the activity.
     */
    public String getAssign() {
        return assign;
    }

    /**
     * set the the assigned person of the activity.
     */
    public void setAssign(String assign) {
        this.assign = assign;
    }

    /**
     * Returns the priority of the activity.
     */
    public String getPriority() {
        return priority;
    }

    /**
     * set the priority of the activity.
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Returns the status of the activity.
     */
    public String getStatus() {
        return status;
    }

    /**
     * set the status of the activity.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the leadID of the activity.
     */
    public int getLeadID() {
        return leadID;
    }

    /**
     * Sets the leadID of the activity.
     */
    public void setLeadID(int leadID) {
        this.leadID = leadID;
    }

    /**
     * Returns the leadName of the activity.
     */
    public String getLeadName() {
        return leadName;
    }

    /**
     * Sets the leadName of the activity.
     */
    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    /**
     * Returns the accountID of the activity.
     */
    public int getAccountID() {
        return accountID;
    }

    /**
     * Sets the accountID of the activity.
     */
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    /**
     * Returns the accountName of the activity.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets the accountName of the activity.
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Returns the opportunityID of the activity.
     */
    public int getOpportunityID() {
        return opportunityID;
    }

    /**
     * Sets the opportunityID of the activity.
     */
    public void setOpportunityID(int opportunityID) {
        this.opportunityID = opportunityID;
    }

    /**
     * Returns the opportunityName of the activity.
     */
    public String getOpportunityName() {
        return opportunityName;
    }

    /**
     * Sets the opportunity of the activity.
     */
    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    /**
     * Returns the contactID of the activity.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the contactID of the activity.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Returns the contactName of the activity.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact of the activity.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Returns the ownerID of the activity.
     */
    public int getOwnerID() {
        return ownerID;
    }

    /**
     * Sets the ownerID of the activity.
     */
    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * Returns the owner of the activity.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the activity.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Returns the Date that the activity was modified.
     */
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the modified date of the activity.
     */
    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}