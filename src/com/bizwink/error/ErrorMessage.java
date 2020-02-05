package com.bizwink.error;

import java.io.Serializable;

/**
 * Created by petersong on 16-6-19.
 */
public class ErrorMessage implements Serializable {
    private int errcode;
    private String errmsg;
    private String modelname;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }
}
