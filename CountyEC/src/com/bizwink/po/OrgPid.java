package com.bizwink.po;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-6-15
 * Time: 下午4:04
 * To change this template use File | Settings | File Templates.
 */
public class OrgPid implements Serializable {
    private int parentid;

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }
}
