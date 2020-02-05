package com.bizwink.util;

import java.io.Serializable;

/**
 * Created by Administrator on 17-12-9.
 */
public class zTreeNodeObj implements Serializable {
    private int id;
    private int pId;
    private int level;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
