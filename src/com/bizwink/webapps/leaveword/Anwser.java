package com.bizwink.webapps.leaveword;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-4-25
 * Time: 7:40:16
 * To change this template use File | Settings | File Templates.
 */
public class Anwser {
    private int id;
    private int siteid;
    private int departmentid;
    private int formid;
    private int lwid;
    private String processor;
    private int flag;
    private String content;
    private Timestamp retdate;
    private Timestamp createdate;

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

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public int getFormid() {
        return formid;
    }

    public void setFormid(int formid) {
        this.formid = formid;
    }

    public int getLwid() {
        return lwid;
    }

    public void setLwid(int lwid) {
        this.lwid = lwid;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getRetdate() {
        return retdate;
    }

    public void setRetdate(Timestamp retdate) {
        this.retdate = retdate;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }
}
