package com.bizwink.util;

import javax.servlet.http.*;
import com.bizwink.security.*;

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

    public static Auth getUserAuthCookies(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        //SESSION失效，试试COOKIE是否可以读出来
        Auth auth = null;
        Cookie[] cookies = request.getCookies();
        String cookie_value = "";
        if (cookies != null) {
            try {
                SecurityUtil securityUtil = new SecurityUtil();
                for(Cookie c :cookies ){
                    if (c.getName().equalsIgnoreCase("AuthInfo_cookie")) {
                        cookie_value = securityUtil.detrypt(c.getValue(),null);
                        break;
                    }
                }

                if (cookie_value != null) {
                    //JSONObject jsonobject = JSONObject.fromObject(cookie_value);
                    //auth = (Auth)JSONObject.toBean(jsonobject,Auth.class);
                    String[] cookie_val = cookie_value.split("-");
                    auth = new Auth();
                    auth.setUid(Integer.parseInt(cookie_val[0]));
                    auth.setDeptid(Integer.parseInt(cookie_val[1]));
                    auth.setCompanyid(Integer.parseInt(cookie_val[2]));
                    auth.setOrgid(Integer.parseInt(cookie_val[3]));
                    auth.setSiteid(Integer.parseInt(cookie_val[4]));
                    auth.setUserid(cookie_val[5]);
                    auth.setUsername(cookie_val[6]);
                    auth.setUsertype(Integer.parseInt(cookie_val[7]));
                    return auth;
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }

        return null;
    }


    public static void removeUserAuthorization (HttpServletResponse response, HttpSession session)
	{
		session.removeAttribute(AUTH_TOKEN_NAME);
	}
}