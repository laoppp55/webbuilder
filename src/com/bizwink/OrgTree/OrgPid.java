package com.bizwink.OrgTree;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-6-15
 * Time: 下午4:04
 * To change this template use File | Settings | File Templates.
 */
public class OrgPid implements Serializable {
    private int id;
    private int parentid;

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
}
