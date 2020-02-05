package com.bizwink.cms.markManager;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-12-28
 * Time: 15:32:54
 * To change this template use File | Settings | File Templates.
 */
public class ArticleMark {
    private int id;
    private int articleid;
    private int  markid;
    private String fontcolor;
    private String fontziti;
    private String istop;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public int getMarkid() {
        return markid;
    }

    public void setMarkid(int markid) {
        this.markid = markid;
    }

    public String getFontcolor() {
        return fontcolor;
    }

    public void setFontcolor(String fontcolor) {
        this.fontcolor = fontcolor;
    }

    public String getFontziti() {
        return fontziti;
    }

    public void setFontziti(String fontziti) {
        this.fontziti = fontziti;
    }

    public String getIstop() {
        return istop;
    }

    public void setIstop(String istop) {
        this.istop = istop;
    }
}
