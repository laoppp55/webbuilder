package com.bizwink.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Comment implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.ID
     *
     * @mbggenerated
     */
    private BigDecimal id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.SITEID
     *
     * @mbggenerated
     */
    private BigDecimal siteid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.USERID
     *
     * @mbggenerated
     */
    private BigDecimal userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.USERNAME
     *
     * @mbggenerated
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.PID
     *
     * @mbggenerated
     */
    private BigDecimal pid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.COMMENTTITLE
     *
     * @mbggenerated
     */
    private String commenttitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.SCORES
     *
     * @mbggenerated
     */
    private BigDecimal scores;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.EXPERIENCE
     *
     * @mbggenerated
     */
    private String experience;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.ADVANTAGES
     *
     * @mbggenerated
     */
    private String advantages;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.SHORTCOMINGS
     *
     * @mbggenerated
     */
    private String shortcomings;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.USEFULNUM
     *
     * @mbggenerated
     */
    private BigDecimal usefulnum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.NOUSENUM
     *
     * @mbggenerated
     */
    private BigDecimal nousenum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.AUDITFLAG
     *
     * @mbggenerated
     */
    private BigDecimal auditflag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_COMMENT.CREATEDATE
     *
     * @mbggenerated
     */
    private Date createdate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.ID
     *
     * @return the value of TBL_COMMENT.ID
     *
     * @mbggenerated
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.ID
     *
     * @param id the value for TBL_COMMENT.ID
     *
     * @mbggenerated
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.SITEID
     *
     * @return the value of TBL_COMMENT.SITEID
     *
     * @mbggenerated
     */
    public BigDecimal getSiteid() {
        return siteid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.SITEID
     *
     * @param siteid the value for TBL_COMMENT.SITEID
     *
     * @mbggenerated
     */
    public void setSiteid(BigDecimal siteid) {
        this.siteid = siteid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.USERID
     *
     * @return the value of TBL_COMMENT.USERID
     *
     * @mbggenerated
     */
    public BigDecimal getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.USERID
     *
     * @param userid the value for TBL_COMMENT.USERID
     *
     * @mbggenerated
     */
    public void setUserid(BigDecimal userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.USERNAME
     *
     * @return the value of TBL_COMMENT.USERNAME
     *
     * @mbggenerated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.USERNAME
     *
     * @param username the value for TBL_COMMENT.USERNAME
     *
     * @mbggenerated
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.PID
     *
     * @return the value of TBL_COMMENT.PID
     *
     * @mbggenerated
     */
    public BigDecimal getPid() {
        return pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.PID
     *
     * @param pid the value for TBL_COMMENT.PID
     *
     * @mbggenerated
     */
    public void setPid(BigDecimal pid) {
        this.pid = pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.COMMENTTITLE
     *
     * @return the value of TBL_COMMENT.COMMENTTITLE
     *
     * @mbggenerated
     */
    public String getCommenttitle() {
        return commenttitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.COMMENTTITLE
     *
     * @param commenttitle the value for TBL_COMMENT.COMMENTTITLE
     *
     * @mbggenerated
     */
    public void setCommenttitle(String commenttitle) {
        this.commenttitle = commenttitle == null ? null : commenttitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.SCORES
     *
     * @return the value of TBL_COMMENT.SCORES
     *
     * @mbggenerated
     */
    public BigDecimal getScores() {
        return scores;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.SCORES
     *
     * @param scores the value for TBL_COMMENT.SCORES
     *
     * @mbggenerated
     */
    public void setScores(BigDecimal scores) {
        this.scores = scores;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.EXPERIENCE
     *
     * @return the value of TBL_COMMENT.EXPERIENCE
     *
     * @mbggenerated
     */
    public String getExperience() {
        return experience;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.EXPERIENCE
     *
     * @param experience the value for TBL_COMMENT.EXPERIENCE
     *
     * @mbggenerated
     */
    public void setExperience(String experience) {
        this.experience = experience == null ? null : experience.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.ADVANTAGES
     *
     * @return the value of TBL_COMMENT.ADVANTAGES
     *
     * @mbggenerated
     */
    public String getAdvantages() {
        return advantages;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.ADVANTAGES
     *
     * @param advantages the value for TBL_COMMENT.ADVANTAGES
     *
     * @mbggenerated
     */
    public void setAdvantages(String advantages) {
        this.advantages = advantages == null ? null : advantages.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.SHORTCOMINGS
     *
     * @return the value of TBL_COMMENT.SHORTCOMINGS
     *
     * @mbggenerated
     */
    public String getShortcomings() {
        return shortcomings;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.SHORTCOMINGS
     *
     * @param shortcomings the value for TBL_COMMENT.SHORTCOMINGS
     *
     * @mbggenerated
     */
    public void setShortcomings(String shortcomings) {
        this.shortcomings = shortcomings == null ? null : shortcomings.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.USEFULNUM
     *
     * @return the value of TBL_COMMENT.USEFULNUM
     *
     * @mbggenerated
     */
    public BigDecimal getUsefulnum() {
        return usefulnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.USEFULNUM
     *
     * @param usefulnum the value for TBL_COMMENT.USEFULNUM
     *
     * @mbggenerated
     */
    public void setUsefulnum(BigDecimal usefulnum) {
        this.usefulnum = usefulnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.NOUSENUM
     *
     * @return the value of TBL_COMMENT.NOUSENUM
     *
     * @mbggenerated
     */
    public BigDecimal getNousenum() {
        return nousenum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.NOUSENUM
     *
     * @param nousenum the value for TBL_COMMENT.NOUSENUM
     *
     * @mbggenerated
     */
    public void setNousenum(BigDecimal nousenum) {
        this.nousenum = nousenum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.AUDITFLAG
     *
     * @return the value of TBL_COMMENT.AUDITFLAG
     *
     * @mbggenerated
     */
    public BigDecimal getAuditflag() {
        return auditflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.AUDITFLAG
     *
     * @param auditflag the value for TBL_COMMENT.AUDITFLAG
     *
     * @mbggenerated
     */
    public void setAuditflag(BigDecimal auditflag) {
        this.auditflag = auditflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_COMMENT.CREATEDATE
     *
     * @return the value of TBL_COMMENT.CREATEDATE
     *
     * @mbggenerated
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_COMMENT.CREATEDATE
     *
     * @param createdate the value for TBL_COMMENT.CREATEDATE
     *
     * @mbggenerated
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}