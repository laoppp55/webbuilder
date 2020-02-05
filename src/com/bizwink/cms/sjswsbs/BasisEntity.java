package com.bizwink.cms.sjswsbs;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2013-9-24 µÄ
 * Time: 16:48:00
 * To change this template use File | Settings | File Templates.
 */
public class BasisEntity {

    int id,wsbsid,category;
    String name,content,standby;   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWsbsid() {
        return wsbsid;
    }

    public void setWsbsid(int wsbsid) {
        this.wsbsid = wsbsid;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby;
    }
}
