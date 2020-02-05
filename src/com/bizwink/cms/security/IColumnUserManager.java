package com.bizwink.cms.security;

import java.util.*;

import com.bizwink.cms.util.*;

public interface IColumnUserManager
{
	List getUserColumns(String userid, int siteid) throws UnauthedException;

	List getGroupColumns(int gid, int siteid) throws UnauthedException;

	List getUserColsFromTBL_Members_Rights(String userID, int rightID) throws CmsException;
}