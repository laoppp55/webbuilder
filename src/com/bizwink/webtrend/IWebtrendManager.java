package com.bizwink.webtrend;

import java.util.*;
import java.sql.*;
import com.bizwink.cms.util.*;
import java.io.*;
/**
 * Title:        IMarkerManager.java
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      bizwink
 * @author Peter Song
 * @version 1.0
 */

public interface IWebtrendManager {

    abstract List reportAll(String id) throws com.bizwink.webtrend.WebtrendException;

    abstract List reportTop50pv(String id) throws com.bizwink.webtrend.WebtrendException;

    abstract List reportPerDay(String date, String id) throws com.bizwink.webtrend.WebtrendException;

    abstract List search(String fdate, String tdate, String id) throws com.bizwink.webtrend.WebtrendException;

    abstract List getWebtrendList(int startIndex, int numResult, String id) throws WebtrendException;

    abstract List getCurrentWebtrendList(String date, int startIndex, int numResult, String id) throws WebtrendException;

    abstract List getsearchCurrentWebtrendList(String fromdate, String todate, int startIndex, int numResult, String id) throws WebtrendException;

    abstract List searchchannelitem(String id) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchchannelurl(String id,String date) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchconclusion(String id) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchconclusion(String id,int i) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchconclusion(String id,int i,int j) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchconclusion(String id,int i,int j,int k) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchconclusion(String id,int i,int j,int k,int m) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchip(String ip) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchcountryid(String id,String date) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchprovinceid(String id,String date) throws com.bizwink.webtrend.WebtrendException;

    abstract Webtrend searchcountry(int i) throws com.bizwink.webtrend.WebtrendException;

    abstract Webtrend searchcity(int i) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchurl(String url,String id) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchurl(String url,String id,int flag) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchurl(String url,String id,int flag,int tfalg) throws com.bizwink.webtrend.WebtrendException;

    abstract List searchconclusion(String id, String begindate, String enddate) throws com.bizwink.webtrend.WebtrendException;

    //add 2003-08-16 by EricDu
    abstract List reportrefer(String date, String id) throws com.bizwink.webtrend.WebtrendException;

    abstract List getsearchCurrentReferList(int startIndex, int numResult, String date, String id) throws WebtrendException;

    abstract List reportreferdetail(String date, String id, String domain, String url) throws com.bizwink.webtrend.WebtrendException;

    abstract List getsearchCurrentReferListDetail(int startIndex, int numResult, String date, String id, String domain, String url) throws WebtrendException;
}