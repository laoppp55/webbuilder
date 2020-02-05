package com.bizwink.joincompany;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-5-8
 * Time: 9:58:55
 * To change this template use File | Settings | File Templates.
 */
public interface IJoincompanyManager {
     public int insertJoincompany(Joincompany join,int ID);
    public Joincompany getJoin(String name,String pass);
    public int updateJoin(Joincompany join);
    public int updatepass(Joincompany join,String password);
    public List getPageJoin(int ipage);
    public int getCountJoin();
    public List searchJoin(String name,int ipage);
    public int getSearchCountJoin(String name);
    public int  getCMSMembersCount(int ipage ,int id,int dflag);
    public List getCMSMembersPage(int ipage,int id,int dflag);
     public List getSiteInfo(int siteid);
    public int updateMembers(String userid);
    public int  getCMSMembersCount(int ipage ,int id);
    public List getCMSMembersPage(int ipage,int id);
    public int deleteJoincompany(int id);
     public int updateJoincompanyPass(String password,int id);
}
