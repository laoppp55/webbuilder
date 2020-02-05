package com.bizwink.indexer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-3-12
 * Time: 13:13:18
 * To change this template use File | Settings | File Templates.
 */
public class article {
    private int ID = 0;                //编号
    private int siteid;
    private int columnid;
    private int acttype;
    private String sitename;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getColumnid() {
        return columnid;
    }

    public void setColumnid(int columnid) {
        this.columnid = columnid;
    }

    public int getActtype() {
        return acttype;
    }

    public void setActtype(int acttype) {
        this.acttype = acttype;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }
}
