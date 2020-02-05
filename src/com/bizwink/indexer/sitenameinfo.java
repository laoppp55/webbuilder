package com.bizwink.indexer;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class sitenameinfo {
    private int siteid;                //文章所属的站点
    private String sitename;           //文章所属站点的名称

    public int getSiteID() {
        return siteid;
    }

    public void setSiteID(int siteid) {
        this.siteid  = siteid;
    }

    public String getSiteName() {
        return sitename;
    }

    public void setSiteName(String sitename) {
        this.sitename  = sitename;
    }
}