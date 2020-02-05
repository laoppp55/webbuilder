<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.security.Auth" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.Gson" %>
<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 16-1-30
  Time: 下午7:27
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);

    Gson gson = new Gson();
    String jsondata=null;
    System.out.println("hello word");
    if (authToken != null) {
        jsondata = gson.toJson(authToken);
        out.print(jsondata);
        out.flush();
    } else{
        out.print("{\"result\":\"nologin\"}");
        out.flush();
        //jsonData = "{\"result\":\"nologin\"}";
    }
    //JSON.setPrintWriter(response, jsonData, "utf-8");

%>