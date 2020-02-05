package com.bizwink.program;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-14
 * Time: 19:40:49
 * To change this template use File | Settings | File Templates.
 */
public class programOftoolkit {
    private int id;
    private int siteid;
    private String progid;
    private String progname;
    private String proguri;
    private Timestamp createdate;
    private Timestamp updatedate;

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

    public String getProgid() {
        return progid;
    }

    public void setProgid(String progid) {
        this.progid = progid;
    }

    public String getProgname() {
        return progname;
    }

    public void setProgname(String progname) {
        this.progname = progname;
    }

    public String getProguri() {
        return proguri;
    }

    public void setProguri(String proguri) {
        this.proguri = proguri;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public Timestamp getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Timestamp updatedate) {
        this.updatedate = updatedate;
    }
}
