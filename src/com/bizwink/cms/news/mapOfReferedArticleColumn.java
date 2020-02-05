package com.bizwink.cms.news;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-2-24
 * Time: 15:27:39
 * To change this template use File | Settings | File Templates.
 */
public class mapOfReferedArticleColumn {
    private int articleid;
    private int columnid;

    void setArticleID(int artid) {
        this.articleid = artid;
    }
    
    int getArticleID() {
        return articleid;
    }

    void setColumnID(int cid) {
        this.columnid = cid;
    }

    int getColumnID() {
        return columnid;
    }
}
