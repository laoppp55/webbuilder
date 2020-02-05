<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 16-1-30
  Time: 下午7:27
  To change this template use File | Settings | File Templates.
--%>
<%
    //首先清除COOKIE的值
    boolean clear_Cookie_Flag = false;
    Cookie[] cookies = request.getCookies();
    try {
        for(int i=0;i<cookies.length;i++) {
            System.out.println(cookies[i].getName() + ":" + cookies[i].getValue());
            Cookie cookie = new Cookie(cookies[i].getName(), null);
            cookie.setMaxAge(0);
            cookie.setPath("/");//根据你创建cookie的路径进行填写
            response.addCookie(cookie);
        }
        clear_Cookie_Flag = true;
    }catch(Exception ex) {
        System.out.println("清空Cookies发生异常！");
    }

    //清除SESSION的数据
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken != null) {
        SessionUtil.removeUserAuthorization(response, session);
    }

    authToken = SessionUtil.getUserAuthorization(request, response, session);

    if (authToken == null && clear_Cookie_Flag ==true)
        out.write("<logoutflag>"+true+"</logoutflag>");
    else
        out.write("");

    out.flush();
%>