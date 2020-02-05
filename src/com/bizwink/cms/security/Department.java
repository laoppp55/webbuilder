package com.bizwink.cms.security;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-9-7
 * Time: 20:17:04
 * To change this template use File | Settings | File Templates.
 */
public class Department {
    private int id;
    private int siteid;
    private String cname;
    private String ename;
    private String telephone;
    private String manager;
    private String vicemanager;
    private String leader;
    private String unit;  //上级单位
    private String upcode; //上级机构编码

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

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

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getVicemanager() {
        return vicemanager;
    }

    public void setVicemanager(String vicemanager) {
        this.vicemanager = vicemanager;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUpcode() {
        return upcode;
    }

    public void setUpcode(String upcode) {
        this.upcode = upcode;
    }
}
