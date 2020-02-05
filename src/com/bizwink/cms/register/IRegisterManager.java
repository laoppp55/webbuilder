package com.bizwink.cms.register;

import com.bizwink.cms.util.*;
import com.bizwink.po.Companyinfo;
import com.bizwink.webapps.register.UregisterException;

import java.util.List;

public interface IRegisterManager
{
    void create(Register register,Companyinfo companyinfo) throws CmsException;

    boolean copySamSite(int sampleSiteID, int siteid,String userid,String sitename,String apppath) throws CmsException;

    void copy_CSS_AND_SCRIPT_ToCMS_AND_WEB(String userid,String sampleSiteName,int siteid, String sitename, String appPath) throws CmsException;

    int update(Register register) throws CmsException;

    Register getSite(int siteID) throws CmsException;

    int getSite_ID(String siteName) throws CmsException;

    boolean QuerySiteName(String siteName) throws CmsException;

    void getPassword(String userID,String siteName) throws CmsException;

    void copyIconToCMS(int siteID,String dname,String rootPath) throws CmsException;

    void copyIconToWEB(String username,int siteID,String rootPath) throws CmsException;

    void update_pubflag(int siteID,int pubflag) throws CmsException;

    int query_pubflag(int siteID) throws CmsException;

    boolean queryUsername(String username) throws CmsException;

    resinConfig getResinConfig();

    String checkEmail(String email);

    String checkUser(String name);

    String checkHost(String hostname);

    int getCode() throws CmsException;

    String getIPByCode() throws CmsException;

    int insertcreate(Register register, String appPath) throws CmsException;

    void update_sitepic(int siteID, String sitepic) throws CmsException;

    boolean userExist(String userid) throws CmsException;

    int insertRegister(Register reg) throws CmsException;

    void updateRsbt(Register reg, int id) throws CmsException;

    void deleteRsbtList(int id) throws CmsException;

    List getAllRsbt() throws CmsException;

    List getAllRsbt(int siteid) throws CmsException;

    List getCurrentQueryRsbtList(int id,String sqlstr, int startrow, int range) throws CmsException;

    int getAllRsbtNum(int siteid) throws CmsException;

    List getCurrentRsbtList(int siteid, int startrow, int range) throws CmsException;

    Register getByIdrsbt(int id) throws CmsException;

    List getAllOrg1() throws CmsException;

    List getAllOrg2() throws CmsException;

    List getAllOrg3() throws CmsException;

    int checkSystemUserExist(String str) throws CmsException;

    int checkSystemUserEmailExist(String str) throws  CmsException;
}