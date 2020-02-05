package com.bizwink.cms.selfDefine;

import java.sql.*;

public class SelfDefine{
    private String name;
    private String value;
    private String type;
    private int size;

    public void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return name;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getValue() {
        return value;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getType() {
        return type;
    }

    public  void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}