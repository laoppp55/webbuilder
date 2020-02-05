package com.bizwink.ColumnTree;

public class node {
    private String ChName;
    private String EnName;
    private int id;
    private int orderNum;
    private int linkPointer;
    private boolean leaffalg;
    private int hasArticleModel;
    private int isAudited;
    private String columnURL;
    private int globalColumn;
    private int defineAttr;
    private int languageType;;

    public node() {
        ChName = "";
        EnName = "";
        linkPointer = 0;
        orderNum = 0;
        hasArticleModel = 0;
    }

    public void setChName(String cname) {
        this.ChName = cname;
    }

    public String getChName() {
        return ChName;
    }

    public void setEnName(String ename) {
        this.EnName = ename;
    }

    public String getEnName() {
        return EnName;
    }

    public void setLinkPointer(int pointer) {
        this.linkPointer = pointer;
    }

    public int getLinkPointer() {
        return linkPointer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setHasArticleModel(int hasArticleModel) {
        this.hasArticleModel = hasArticleModel;
    }

    public int getHasArticleModel() {
        return hasArticleModel;
    }

    public String getColumnURL() {
        return columnURL;
    }

    public void setColumnURL(String columnURL) {
        this.columnURL = columnURL;
    }

    public int getAudited() {
        return isAudited;
    }

    public void setAudited(int audited) {
        isAudited = audited;
    }

    public int getGlobalColumn() {
        return globalColumn;
    }

    public void setGlobalColumn(int globalColumn) {
        this.globalColumn = globalColumn;
    }

    public void setDefineAttr(int defineAttr) {
        this.defineAttr = defineAttr;
    }

    public int getDefineAttr() {
        return defineAttr;
    }

    public void setLanguageType(int language) {
        this.languageType = language;
    }

    public int getLanguageType() {
        return languageType;
    }

    public boolean isLeaffalg() {
        return leaffalg;
    }

    public void setLeaffalg(boolean leaffalg) {
        this.leaffalg = leaffalg;
    }
}