<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.util.filter" %>
<%@ page import="com.bizwink.service.UsersService" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="java.math.BigDecimal" %>
<%
    boolean result = false;
    BigDecimal siteid = BigDecimal.valueOf(5971);
    String username= filter.excludeHTMLCode(ParamUtil.getParameter(request, "username"));
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        UsersService usersService = (UsersService)appContext.getBean("usersService");
        result = usersService.checkName(siteid,username);
    }

    String jsonData = null;
    if (result == true)
        jsonData =  "{\"result\":\"true\"}";
    else
        jsonData = "{\"result\":\"false\"}";

    JSON.setPrintWriter(response, jsonData,"utf-8");
%>