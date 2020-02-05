package com.bizwink.cms.publish;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-8-28
 * Time: 21:44:59
 * To change this template use File | Settings | File Templates.
 */
public class form {
    private String formcontent;
    private int formtype;

    public void setFormcontent(String content) {
        this.formcontent = content;
    }

    public String getFormcontent() {
        return formcontent;
    }

    public void setFormtype(int type) {
        this.formtype = type;
    }

    public int getFormtype() {
        return formtype;
    }
}
