<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.security.Auth" %>
<%@ page import="com.google.gson.Gson" %>
<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 16-1-30
  Time: 下午7:27
  To change this template use File | Settings | File Templates.
--%>
<%
    //清除SESSION的数据
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken != null) {
        SessionUtil.removeUserAuthorization(response, session);
        authToken = null;
    }
    //清除Cookie的数据
    Cookie cookie = null;
    Cookie[] cookies = request.getCookies();
    if( cookies != null ){
      for (int i = 0; i < cookies.length; i++){
         cookie = cookies[i];
         if((cookie.getName( )).compareTo("username") == 0 ){
            cookie.setMaxAge(0);
			cookie.setPath("/");
            response.addCookie(cookie);
         } 
      }
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (authToken == null){
        String result = "nologin";
        jsondata = gson.toJson(result);
        out.print(jsondata);
    }else{
        String result = "login";
        jsondata = gson.toJson(result);
        out.print(jsondata);
    }
    out.flush();
%>