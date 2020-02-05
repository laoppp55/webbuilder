<%@ page import="com.unittest.sso" %>
<%@ page import="com.bjtoon.uia.sdk.domain.AccessTokenVo" %>
<%@ page import="com.bjtoon.uia.sdk.domain.UserInfoVo" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    sso sso = new sso();
    String grantCode = (String)session.getAttribute("grantCode");
    System.out.println("grantcode===" + grantCode);
    AccessTokenVo accessTokenVo = sso.getAccessToken(grantCode);
    UserInfoVo userInfoVo = sso.getUserInfo(accessTokenVo);
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(userInfoVo);
    System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>
