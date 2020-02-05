package com.bizwink.cms.business.Users;

import java.util.*;

import com.bizwink.cms.tree.*;
import com.bizwink.cms.audit.*;
import java.sql.*;

public interface IBUserManager
{
    //获取网站注册用户列表
    public List getBUserList(int siteid,int startIndex,int range,String sqlstr) throws BUserException;

    //获取企业内部用户列表，包括审核后的用户和未审核的用户
    public List getBusinessUserList(int siteid,int startIndex, int range, String sqlstr) throws BUserException;

    //获取企业业务员用户的数量
    public int getTotalBusinessUsers(int siteid,int flag) throws BUserException;

    //查询企业业务员信息
    public List searchBusinessUserList(int siteid,int startIndex,int pagesize,BUser user) throws BUserException;

    //符合某个条件的企业业务员总数量
    public int searchBusinessUserListCount(int siteid,BUser user) throws BUserException;

    public List searchUserList(int siteid,int startIndex,int pagesize,BUser user) throws BUserException;

    public int searchUserListCount(int siteid,BUser user) throws BUserException;

    public int getTotalUsers(int siteid,int flag) throws BUserException;

    public BUser getAUsers(String userid,int siteid) throws BUserException;

    public void updateUserInfo(BUser buser) throws BUserException;

    public void updateLockFlag(int flag,String userid,int siteid) throws BUserException;

    public void updatePassword(String pass,String userid) throws BUserException;

    public int updatePassword(String pass, String userid,int siteid) throws BUserException;

    public void delUser(int userid,int siteid) throws BUserException;
}
