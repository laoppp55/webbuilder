package com.bizwink.OrgTree;


public class typenode{

    private int id;
    private int parentid;
    private String cname;
    private int linkPointer;

    public typenode(){
        cname = "" ;
        parentid =0;
        linkPointer = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getLinkPointer() {
        return linkPointer;
    }

    public void setLinkPointer(int linkPointer) {
        this.linkPointer = linkPointer;
    }
}