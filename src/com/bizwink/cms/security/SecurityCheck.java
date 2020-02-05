package com.bizwink.cms.security;

public class SecurityCheck
{
	public static boolean hasPermission(Auth authToken, int rightid)
	{
		PermissionSet permissions = authToken.getPermissionSet();
		boolean value = false;

		if (authToken.getUserID().toLowerCase().equals("admin"))
			return true;
		if (permissions.contains(rightid))
			value = true;

		return value;
	}
}
