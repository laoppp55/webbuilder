<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="antlr.Parser" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 16-1-30
  Time: 下午7:27
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username = null;
    int usertype = 0;

    //入股SESSION失效，试试COOKIE是否可以读出来
    Cookie[] cookies = request.getCookies();
    String cookie_value = null;
    for(Cookie c :cookies ){
        if (c.getName().equalsIgnoreCase("AuthInfo_cookie")) {
            SecurityUtil securityUtil = new SecurityUtil();
            System.out.println("cookie value==" + c.getValue());
            cookie_value = securityUtil.detrypt(c.getValue(),null);
            System.out.println("机密后cookie value==" + cookie_value);
            break;
        }
    }

    if (cookie_value != null) {
        if (authToken == null) {
            int posi = cookie_value.indexOf("-");
            username = cookie_value.substring(0,posi+1);
            usertype = Integer.parseInt(cookie_value.substring(posi));
        } else {
            username =authToken.getUsername();
            usertype = authToken.getUsertype();
        }
    } else {                        //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
        if (authToken != null) {
            SessionUtil.removeUserAuthorization(response, session);
        }
    }

    if (username != null)
        out.write("<uname>"+username+"-" + usertype + "</uname>");
    else
        out.write("");

    out.flush();
%>