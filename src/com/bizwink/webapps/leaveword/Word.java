package com.bizwink.webapps.leaveword;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Word {
    private int id;
    private int siteid;
    private int sex;
    private String code;
    private int columnid;
    private String mphone;
    private int formid;
    private String content;
    private String retcontent;
    private String title;
    private Timestamp writedate;
    private String company;
    private String linkman;
    private String links;
    private String zip;
    private String email;
    private String phone;
    private int flag;
    private String userid;
    private int departmentid;
    private String auditor;
    private int auditflag;
    private String processor;
    private int valid;
    private String validreason;
    private Timestamp datefromdept;
    private int finalflag;
    private Timestamp endtouser;
    private String prefix;
    private List anwserfordept = new ArrayList();

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public int getAuditflag() {
        return auditflag;
    }

    public void setAuditflag(int auditflag) {
        this.auditflag = auditflag;
    }

    public Word() {
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public int getFormid() {
        return formid;
    }

    public void setFormid(int formid) {
        this.formid = formid;
    }
    

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRetcontent() {
        return retcontent;
    }

    public void setRetconent(String content) {
        this.retcontent = content;
    }    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getWritedate() {
        return writedate;
    }

    public void setWritedate(Timestamp writedate) {
        this.writedate = writedate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getValidreason() {
        return validreason;
    }

    public void setValidreason(String validreason) {
        this.validreason = validreason;
    }

    public Timestamp getDatefromdept() {
        return datefromdept;
    }

    public int getFinalflag() {
        return finalflag;
    }

    public void setFinalflag(int finalflag) {
        this.finalflag = finalflag;
    }

    public void setDatefromdept(Timestamp datefromdept) {
        this.datefromdept = datefromdept;
    }

    public Timestamp getEndtouser() {
        return endtouser;
    }

    public void setEndtouser(Timestamp endtouser) {
        this.endtouser = endtouser;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getColumnid() {
        return columnid;
    }

    public void setColumnid(int columnid) {
        this.columnid = columnid;
    }

    public List getAnwserfordept() {
        return anwserfordept;
    }

    public void setAnwserfordept(List anwserfordept) {
        this.anwserfordept = anwserfordept;
    }
}

