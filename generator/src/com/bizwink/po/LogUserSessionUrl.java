package com.bizwink.po;

import java.math.BigDecimal;
import java.util.Date;

public class LogUserSessionUrl {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TLOG_USERSESSION_URL.ID
     *
     * @mbggenerated
     */
    private BigDecimal ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TLOG_USERSESSION_URL.SESSIONID
     *
     * @mbggenerated
     */
    private BigDecimal SESSIONID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TLOG_USERSESSION_URL.URL
     *
     * @mbggenerated
     */
    private String URL;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TLOG_USERSESSION_URL.ACCESSTIME
     *
     * @mbggenerated
     */
    private Date ACCESSTIME;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TLOG_USERSESSION_URL.ID
     *
     * @return the value of TLOG_USERSESSION_URL.ID
     *
     * @mbggenerated
     */
    public BigDecimal getID() {
        return ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TLOG_USERSESSION_URL.ID
     *
     * @param ID the value for TLOG_USERSESSION_URL.ID
     *
     * @mbggenerated
     */
    public void setID(BigDecimal ID) {
        this.ID = ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TLOG_USERSESSION_URL.SESSIONID
     *
     * @return the value of TLOG_USERSESSION_URL.SESSIONID
     *
     * @mbggenerated
     */
    public BigDecimal getSESSIONID() {
        return SESSIONID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TLOG_USERSESSION_URL.SESSIONID
     *
     * @param SESSIONID the value for TLOG_USERSESSION_URL.SESSIONID
     *
     * @mbggenerated
     */
    public void setSESSIONID(BigDecimal SESSIONID) {
        this.SESSIONID = SESSIONID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TLOG_USERSESSION_URL.URL
     *
     * @return the value of TLOG_USERSESSION_URL.URL
     *
     * @mbggenerated
     */
    public String getURL() {
        return URL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TLOG_USERSESSION_URL.URL
     *
     * @param URL the value for TLOG_USERSESSION_URL.URL
     *
     * @mbggenerated
     */
    public void setURL(String URL) {
        this.URL = URL == null ? null : URL.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TLOG_USERSESSION_URL.ACCESSTIME
     *
     * @return the value of TLOG_USERSESSION_URL.ACCESSTIME
     *
     * @mbggenerated
     */
    public Date getACCESSTIME() {
        return ACCESSTIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TLOG_USERSESSION_URL.ACCESSTIME
     *
     * @param ACCESSTIME the value for TLOG_USERSESSION_URL.ACCESSTIME
     *
     * @mbggenerated
     */
    public void setACCESSTIME(Date ACCESSTIME) {
        this.ACCESSTIME = ACCESSTIME;
    }
}