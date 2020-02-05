package com.bizwink.cms.sitesetting;

import java.util.*;

public interface ISiteInfoManager {
    SiteInfo getSiteInfo(int siteid) throws SiteInfoException;

    int getSiteID(String sitename) throws SiteInfoException;

    int getSiteEncoding(int siteid) throws SiteInfoException;

    List getAllSiteInfo() throws SiteInfoException;

    List getSpecialSiteInfo() throws SiteInfoException;

    String getSiteName(int articleID) throws SiteInfoException;

    int getAllSiteInfoNum() throws SiteInfoException;

    int getAllSiteInfoNum(int sitetype,int current_siteid) throws SiteInfoException;

    List getAllSiteInfo(int resultnum,int startnum) throws SiteInfoException;

    List getAllSamSiteInfo(int resultnum,int startnum) throws SiteInfoException;

    List getAllSiteInfo(int resultnum,int startnum,int sitetype,int current_siteid) throws SiteInfoException;

    int getAllSearchSiteInfoNum(String search) throws SiteInfoException;

    int getAllSearchSiteInfoNum(String search,int sitetype,int current_siteid) throws SiteInfoException;

    List getAllSearchSiteInfo(int resultnum,int startnum,String search) throws SiteInfoException;

    List getPicSize(int siteid) throws SiteInfoException;

    List getAllSearchSiteInfo(int resultnum,int startnum,String search,int sitetype,int current_siteid) throws SiteInfoException;

    List getTop8SiteInfo(int sitetype,int current_siteid) throws SiteInfoException;

    int remove(int siteid) throws SiteInfoException;

    String getSiteStringAttribute(int siteid,String attribute_name) throws SiteInfoException;

    int getSiteIntAttribute(int siteid,String attribute_name) throws SiteInfoException;

    int updatesitevalid(int siteid,int valid) throws SiteInfoException;

    int getShareSiteflag(int siteid) throws SiteInfoException;
}