package com.heaton.bot;

import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 *
 * @author unascribed
 * @version 1.0
 */

public class siteinfo {
    private int id;
    private int siteid;
    private String sitename;
    private String starturl;
    private int getdepth;
    private int chadepth;
    private int urlnumber;
    private String matchurl;
    private String nomatchurl;
    private String flashcookieurl;
    private String starttag;
    private String endtag;
    private String status;
    private int loginflag;
    private String posturl;
    private String postdata;
    private int classid;
    private List matchurls;                     //匹配的URL列表
    private List tags;                           //匹配的标记列表   元素类型为StartEndTag
    private List columns;                        //抓取信息装载栏目列表

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getClassID() {
        return classid;
    }

    public void setClassID(int classid) {
        this.classid = classid;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getStarturl() {
        return starturl;
    }

    public void setStarturl(String starturl) {
        this.starturl = starturl;
    }

    public int getGetdepth() {
        return getdepth;
    }

    public void setGetdepth(int getdepth) {
        this.getdepth = getdepth;
    }

    public int getChadepth() {
        return chadepth;
    }

    public void setChadepth(int chadepth) {
        this.chadepth = chadepth;
    }

    public int getUrlnumber() {
        return urlnumber;
    }

    public void setUrlnumber(int urlnumber) {
        this.urlnumber = urlnumber;
    }

    public String getMatchurl() {
        return matchurl;
    }

    public void setMatchurl(String matchurl) {
        this.matchurl = matchurl;
    }

    public String getNomatchurl() {
        return nomatchurl;
    }

    public void setNomatchurl(String nomatchurl) {
        this.nomatchurl = nomatchurl;
    }

    public String getFlashcookieurl() {
        return flashcookieurl;
    }

    public void setFlashcookieurl(String flashcookieurl) {
        this.flashcookieurl = flashcookieurl;
    }

    public String getStarttag() {
        return starttag;
    }

    public void setStarttag(String starttag) {
        this.starttag = starttag;
    }

    public String getEndtag() {
        return endtag;
    }

    public void setEndtag(String endtag) {
        this.endtag = endtag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLoginflag() {
        return loginflag;
    }

    public void setLoginflag(int loginflag) {
        this.loginflag = loginflag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostURL() {
        return posturl;
    }

    public void setPostURL(String url) {
        this.posturl = url;
    }

    public String getPostData() {
        return postdata;
    }

    public void setPostData(String data) {
        this.postdata = data;
    }

    public List getMatchurls() {
        return matchurls;
    }

    public void setMatchurls(List matchurls) {
        this.matchurls = matchurls;
    }

    public List getTags() {
        return tags;
    }

    public void setTags(List tags) {
        this.tags = tags;
    }

    public List getColumns() {
        return columns;
    }

    public void setColumns(List columns) {
        this.columns = columns;
    }
}