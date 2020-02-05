package com.bizwink.cms.news;

import java.util.List;

public class Producttype {
    public Producttype() {

    }

    private int id;
    private String cname;
    private String ename;
    private List valueList;
    private int valueid;
    private String values;
    private int maxid;
    private int columnID;
    private int parentid;
    private int referid;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public List getValueList() {
        return valueList;
    }

    public void setValueList(List valueList) {
        this.valueList = valueList;
    }

    public int getValueid() {
        return valueid;
    }

    public void setValueid(int valueid) {
        this.valueid = valueid;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public int getMaxid() {
        return maxid;
    }

    public void setMaxid(int maxid) {
        this.maxid = maxid;
    }

    public int getColumnID() {
        return columnID;
    }

    public void setColumnID(int columnID) {
        this.columnID = columnID;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getReferid() {
        return referid;
    }

    public void setReferid(int referid) {
        this.referid = referid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}