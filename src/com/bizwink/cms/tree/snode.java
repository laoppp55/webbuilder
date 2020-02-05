package com.bizwink.cms.tree;

import java.util.List;

public class snode {
    private String ChName;
    private String EnName;
    private String id;
    private int orderNum;
    private String linkPointer;
    private int hasArticleModel;
    private int isAudited;
    private String columnURL;
    private int globalColumn;
    private int defineAttr;
    private int languageType;
    private String unit;
    private String desc;
    private String keyword;
    private String keywordnote;
    private List<String> subnodes;

    public snode() {
        ChName = "";
        EnName = "";
        linkPointer = "0";
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

    public void setLinkPointer(String pointer) {
        this.linkPointer = pointer;
    }

    public String getLinkPointer() {
        return linkPointer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeywordnote() {
        return keywordnote;
    }

    public void setKeywordnote(String keywordnote) {
        this.keywordnote = keywordnote;
    }

    public List<String> getSubnodes() {
        return subnodes;
    }

    public void setSubnodes(List<String> subnodes) {
        this.subnodes = subnodes;
    }
}