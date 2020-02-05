package com.bizwink.cms.news;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 2008-11-24
 * Time: 16:07:20
 * To change this template use File | Settings | File Templates.
 */
public class Turnpic implements Serializable{
    public Turnpic(){

    }
    private int id;
    private int articleid;
    private String picname;
    private String mediaurl;
    private String smallpic1url;
    private String smallpic2url;
    private String smallpic3url;
    private int sortid;
    private Timestamp createdate;
    private String notes;
    private double laitude;
    private double longitude;
    private String tab1;
    private String tab2;
    private String tab3;
    private String tab4;
    private String tab5;
    private String tab6;
    private String tab7;
    private String tab8;

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

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public String getMediaurl() {
        return mediaurl;
    }

    public void setMediaurl(String mediaurl) {
        this.mediaurl = mediaurl;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }

    public String getTab1() {
        return tab1;
    }

    public void setTab1(String tab1) {
        this.tab1 = tab1;
    }

    public String getTab2() {
        return tab2;
    }

    public void setTab2(String tab2) {
        this.tab2 = tab2;
    }

    public String getTab3() {
        return tab3;
    }

    public void setTab3(String tab3) {
        this.tab3 = tab3;
    }
    public String getTab4() {
        return tab4;
    }

    public void setTab4(String tab4) {
        this.tab4 = tab4;
    }

    public String getTab5() {
        return tab5;
    }

    public void setTab5(String tab5) {
        this.tab5 = tab5;
    }

    public String getTab6() {
        return tab6;
    }

    public void setTab6(String tab6) {
        this.tab6 = tab6;
    }

    public String getTab7() {
        return tab7;
    }

    public void setTab7(String tab7) {
        this.tab7 = tab7;
    }

    public String getTab8() {
        return tab8;
    }

    public void setTab8(String tab8) {
        this.tab8 = tab8;
    }

    public String getSmallpic1url() {
        return smallpic1url;
    }

    public void setSmallpic1url(String smallpic1url) {
        this.smallpic1url = smallpic1url;
    }

    public String getSmallpic2url() {
        return smallpic2url;
    }

    public void setSmallpic2url(String smallpic2url) {
        this.smallpic2url = smallpic2url;
    }

    public String getSmallpic3url() {
        return smallpic3url;
    }

    public void setSmallpic3url(String smallpic3url) {
        this.smallpic3url = smallpic3url;
    }

    public double getLaitude() {
        return laitude;
    }

    public void setLaitude(double laitude) {
        this.laitude = laitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}