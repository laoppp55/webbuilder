package com.bizwink.upload;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-4-10
 * Time: 21:18:04
 * To change this template use File | Settings | File Templates.
 */

import java.util.*;

public class uploaderrormsg {
    private int errcode;
    private String errmsg;
    private String paras;
    private String templatename;
    private List errimages=new ArrayList();

    public int getErrorCode() {
      return this.errcode;
    }

    public void setErrorCode(int code) {
      this.errcode = code;
    }

    public String getErrorMsg() {
      return errmsg;
    }

    public void setErrorMsg(String msg) {
      this.errmsg = msg;
    }

    public String getParas() {
      return paras;
    }

    public void setParas(String paras) {
      this.paras = paras;
    }

    public String getTemplatename() {
      return templatename;
    }

    public void setTemplatename(String name) {
      this.templatename = name;
    }


    public void ClearErrorPics()  {
        this.errimages.clear();
    }

    public void setErrorPics(String fname)  {
        this.errimages.add(fname);
    }

    public List getErrorPics() {
        return errimages;
    }
}
