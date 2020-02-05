package com.bizwink.cms.sjswsbs;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2013-9-24
 * Time: 16:49:19ÊÇ
 * To change this template use File | Settings | File Templates.
 */
public class CatgEntity {

    int id, category, sortnum, valid; 
    String name, sid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSortnum() {
        return sortnum;
    }

    public void setSortnum(int sortnum) {
        this.sortnum = sortnum;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
