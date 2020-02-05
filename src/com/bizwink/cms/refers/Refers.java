package com.bizwink.cms.refers;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Du Zhenqiang
 * Date: 2008-2-18
 * Time: 17:37:23
 */
public class Refers {

    private int id;
    private int articleid;
    private int siteid;
    private int ssiteid;
    private int columnid;
    private int scolumnid;
    private String columnname;
    private String title;
    private int status;
    private int pubflag;
    private int auditflag;
    private int usearticletype;
    private int orders;
    private int REFERS_COLUMN_STATUS;
    private int tsiteid;
    private int artfrom;

    private Timestamp createdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getSsiteid() {
        return ssiteid;
    }

    public void setSsiteid(int ssiteid) {
        this.ssiteid = ssiteid;
    }

    public int getColumnid() {
        return columnid;
    }

    public void setColumnid(int columnid) {
        this.columnid = columnid;
    }

    public int getScolumnid() {
        return scolumnid;
    }

    public void setScolumnid(int scolumnid) {
        this.scolumnid = scolumnid;
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPubflag() {
        return pubflag;
    }

    public void setPubflag(int pubflag) {
        this.pubflag = pubflag;
    }

    public int getAuditflag() {
        return auditflag;
    }

    public void setAuditflag(int auditflag) {
        this.auditflag = auditflag;
    }

    public int getUsearticletype() {
        return usearticletype;
    }

    public void setUsearticletype(int usearticletype) {
        this.usearticletype = usearticletype;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getREFERS_COLUMN_STATUS() {
        return REFERS_COLUMN_STATUS;
    }

    public void setREFERS_COLUMN_STATUS(int REFERS_COLUMN_STATUS) {
        this.REFERS_COLUMN_STATUS = REFERS_COLUMN_STATUS;
    }

    public int getTsiteid() {
        return tsiteid;
    }

    public void setTsiteid(int tsiteid) {
        this.tsiteid = tsiteid;
    }

    public int getArtfrom() {
        return artfrom;
    }

    public void setArtfrom(int artfrom) {
        this.artfrom = artfrom;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }
}
