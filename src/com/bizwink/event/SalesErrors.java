/**
 * SalesErrors.java
 */

package com.bizwink.event;

import java.util.*;

public class SalesErrors{
    public static final int NULL_NAME=0;
    public static final int NULL_COMPANY=1;

    public static final int NO_LEAD=1;
    public static final int DUP_LEAD=2;
    public static final int NO_ACCOUNT=1;
    public static final int DUP_ACCOUNT=2;
    public static final int NO_CONTACT=3;
    public static final int DUP_CONTACT=4;
    public static final int NO_OPPORTUNITY=5;
    public static final int DUP_OPPORTUNITY=6;
    public static final int NULL_SUBJECT=7;
    public static final int NO_OWNER=8;
    public static final int DUP_OWNER=9;
    public static final int NO_BELONG=11;
    public static final int DUP_BELONG=12;
    public static final int NULL_DATE=13;
    public static final int NULL_TIME=14;
    public static final int NULL_TITLE=15;
    public static final int NULL_CLOSEDATE=7;
    public static final int NULL_STAGE=8;

    public static final int DUP_QUARTER=1;
    public static final int DUP_FILENAME=1;
    public static final int ERROR_EXTNAME=2;


    //USER  add by cao
    public static final int NULL_FULLNAME=16;
    public static final int DUP_USERNAME=17;
    public static final int NULL_EMAIL=18;
    public static final int NULL_ROLEID=19;
    //
    //操作错误返回值
    public static final int  NOT_LOGIN=1;
    public static final int  NOT_OWNER=2;
    public static final int  NO_PERMISSION=3;
    public static final int  NOT_ADMIN=4;
    public static final int  USER_READONLY=5;
    public static final int  NOT_SAMECOMPANY=6;
    public static final int  NO_ROLE=7;

    //end add by cao

    private BitSet errors;

    public SalesErrors() {
        errors = new BitSet();
    }

    public void setError(int error)
    {
        errors.set(error);
    }

    public boolean hasError(int error) {
        return errors.get(error);
    }
}