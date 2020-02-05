package com.bizwink.cms.news;

/**
 * Created by IntelliJ IDEA.
 * User: EricDu
 * Date: 2006-7-16
 * Time: 11:59:39
 * To change this template use File | Settings | File Templates.
 */
public class ArticleKeyword {
    private int id;
    private int siteid;
    private int columnid;
    private String keyword;
    private String url;
    private String title;
    private String realpath;

    public void ArticleKeyword() {
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

    public int getColumnid() {
        return columnid;
    }

    public void setColumnid(int columnid) {
        this.columnid = columnid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRealpath() {
        return realpath;
    }

    public void setRealpath(String realpath) {
        this.realpath = realpath;
    }
}
