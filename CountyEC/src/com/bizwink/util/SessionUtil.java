package com.bizwink.util;

import javax.servlet.http.*;
import com.bizwink.usercenter.security.Auth;

public class SessionUtil
{
	private static final String AUTH_TOKEN_NAME = "AuthInfo";

	public static String getLoginUser(HttpServletRequest request,HttpServletResponse response, HttpSession session)
	{
		Auth authToken = getUserAuthorization(request, response, session);
		String userName = authToken.getUserid();
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