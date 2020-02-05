package com.bizwink.cms.processTag;

import java.lang.*;
import java.util.*;

/**
 * <p>Title: BW-WebBuilder</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Beijing Bizwink Software Inc</p>
 * @author Peter Song
 * @version 1.0
 */

public class columnListItem {
    private List artList;
    private List subColList;
    private int node= 0;
    private String colChineseName;
    private String colURL;

    public columnListItem() {

    }

    public columnListItem(List al,List cl) {
        this.artList = al;
        this.subColList = cl;
    }

    public void setArtList(List li) {
        this.artList = li;
    }

    public List getArtList() {
        return artList;
    }

    public void setSubColList(List li) {
        this.subColList = li;
    }

    public List getSubColList() {
        return subColList;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public int getNode() {
        return node;
    }

    public void setColChineseName(String name) {
        this.colChineseName = name;
    }

    public String getColChineseName() {
        return colChineseName;
    }

    public void setColURL(String url) {
        this.colURL = url;
    }

    public String getColURL() {
        return colURL;
    }
}