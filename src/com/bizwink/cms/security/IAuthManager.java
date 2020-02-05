package com.bizwink.cms.security;

import com.bizwink.webapps.register.UregisterException;

public interface IAuthManager
{
    Auth getAuth(String username,String password,String userip) throws UnauthedException;

    Auth getSjsAuth(String userid, String password) throws UnauthedException;

    int removeAllNoActionUsers(int dtime) throws UnauthedException;

    int removeUserLoginInfo(int siteid,String userid, String userip) throws UnauthedException;

    int getUserLoginStatus(String userid, String userip) throws UnauthedException;

    int updateUserLoginInfo(String userid, String userip) throws UnauthedException;

    AuthForPerson getAuthForPerson(String userid, String password) throws UnauthedException;

    AuthForWeb getAuthForWeb(String usern, String passw,int siteid) throws UnauthedException;

    int getTemplateNum(int siteid);
}