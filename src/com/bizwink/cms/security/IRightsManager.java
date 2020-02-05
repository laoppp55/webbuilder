package com.bizwink.cms.security;

import java.util.*;
import com.bizwink.cms.util.*;

public interface IRightsManager
{
	List getCrRights() throws CmsException;

	List getUrRights() throws CmsException;

	int grantToColumns(String userID,int rightID,List columnList,int siteID) throws CmsException;

	List getColumnsByRightidAndUserid(int siteid,String userid,int rightID) throws CmsException;

	int grantToUser(String userid, List rlist) throws CmsException;

	int grantGroupToColumns(int groupid, int columnid, List rlist) throws CmsException;

	void grantToGroup(int groupID,int rightID,List columnList,int siteID) throws CmsException;

	List getUserColumnRight(String userID, int columnID) throws CmsException;

	List getGrantedUserRights(String userID) throws CmsException;

	List getGroupColumnRight(int groupID,int rightID) throws CmsException;

	List getGrantedGroupRights(int groupid, int siteid) throws CmsException;

	Rights getRight(int rightID) throws CmsException;

	List getRights() throws CmsException;

	int getRightCount() throws CmsException;

	void withDrawGrant(String userid) throws CmsException;

	List getRights(String uid) throws CmsException;

	List getRemainRights(String uid) throws CmsException;

	void CreateUserRights(Rights right) throws CmsException;

	boolean QueryRights(String userID, int columnID, String rightID) throws Exception;

	void DelUserRights(String userID, String column_Right, String columnID) throws CmsException;

	void DelUserColumns(String userID, String columnID) throws CmsException;

	void DelGroupRights(String groupID, String column_Right, String columnID) throws CmsException;

	void DelGroupColumns(String groupID, String columnID) throws CmsException;

	boolean hasRightOnThisColumn(String userID, String rightID, int columnID) throws Exception;
}
