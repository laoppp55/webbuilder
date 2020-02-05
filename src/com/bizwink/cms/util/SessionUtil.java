package com.bizwink.cms.util;

import javax.servlet.http.*;
import com.bizwink.cms.security.*;

public class SessionUtil
{
	private static final String AUTH_TOKEN_NAME = "CmsAdmin";

	public static String getLoginUser(HttpServletRequest request,HttpServletResponse response, HttpSession session)
	{
		Auth authToken = getUserAuthorization(request, response, session);
		String userName = authToken.getUserID();
		return userName;
	}

	public static Auth getUserAuthorization(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	{
		Object obj = session.getAttribute(AUTH_TOKEN_NAME);
		Auth authToken = null;
		if (obj instanceof Auth)
		{
			authToken = (Auth)obj;
		}
		return authToken;
	}

	public static void removeUserAuthorization (HttpServletResponse response, HttpSession session)
	{
		session.removeAttribute(AUTH_TOKEN_NAME);
	}
}