package com.bizwink.webapps.survey.define;

import java.sql.Timestamp;

public class Define {

    //for su_survye
    private int id;
    private String surveyname;
    private String notes;
    private Timestamp createtime;
    private int siteid;
    private int userflag;

    //for su_dquestion
    private int qid;
    private int sid;
    private String qname;
    private int qtype;
    private int qmust;
    private int count;
    private int atype;
    private String picurl;
    private int questionarrange;                 //定义某个调查里面问题的顺序

    //for su_danswer
    private int aid;
    private int nother;
    private String qanswer;

    public Define() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurveyname() {
        return surveyname;
    }

    public void setSurveyname(String surveyname) {
        this.surveyname = surveyname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getQname() {
        return qname;
    }

    public void setQname(String qname) {
        this.qname = qname;
    }

    public int getQtype() {
        return qtype;
    }

    public void setQtype(int qtype) {
        this.qtype = qtype;
    }

    public int getQmust() {
        return qmust;
    }

    public void setQmust(int qmust) {
        this.qmust = qmust;
    }

    public int getQuestionarrange() {
        return questionarrange;
    }

    public void setQuestionarrange(int questionarrange) {
        this.questionarrange = questionarrange;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getNother() {
        return nother;
    }

    public void setNother(int nother) {
        this.nother = nother;
    }

    public String getQanswer() {
        return qanswer;
    }

    public void setQanswer(String qanswer) {
        this.qanswer = qanswer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAtype() {
        return atype;
    }

    public void setAtype(int atype) {
        this.atype = atype;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getUserflag() {
        return userflag;
    }

    public void setUserflag(int userflag) {
        this.userflag = userflag;
    }
}