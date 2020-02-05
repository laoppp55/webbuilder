package com.bizwink.cms.security;

import java.util.*;

import com.bizwink.cms.util.*;

public interface IGroupManager {
    void create(Group group) throws CmsException;

    void update(Group group) throws CmsException;

    void remove(Group group) throws CmsException;

    int getGroupCount() throws CmsException;

    Group getGroup(int groupid, int siteid) throws CmsException;

    List getGroups(int startIndex, int numResults) throws CmsException;

    List getGroups(int siteID) throws CmsException;

    Group getGroups_New(String groupID) throws CmsException;

    List getGroupsColumn_New(String groupID) throws CmsException;

    void DelGroup(Group group) throws CmsException;

    String getRightID(String RightID) throws CmsException;

    boolean QueryGroupRights(String groupID, int columnID, String rightID) throws CmsException;

    List getGroupsRight_Remain(int groupID) throws CmsException;

    List getGroup_Users(int siteID) throws CmsException;

    String[] getUsers_Groups(int groupID) throws CmsException;

    void update_UserGroups(String[] userID, int groupID) throws CmsException;

    List getGroupsLog(int siteID, String where) throws CmsException;
}
