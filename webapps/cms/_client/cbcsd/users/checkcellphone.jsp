<%@ page import="com.bizwink.service.UsersService" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.util.filter" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="java.math.BigDecimal" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-10-22
  Time: 下午7:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    boolean result = false;
    BigDecimal siteid = BigDecimal.valueOf(5971);
    String mphone= filter.excludeHTMLCode(ParamUtil.getParameter(request, "mphone"));
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        UsersService usersService = (UsersService)appContext.getBean("usersService");
        result = usersService.checkCellphone(siteid,mphone);
    }

    String jsonData = null;
    if (result == true)
        jsonData =  "{\"result\":\"true\"}";
    else
        jsonData = "{\"result\":\"false\"}";

    System.out.println("mphone=="+jsonData);
    JSON.setPrintWriter(response, jsonData,"utf-8");
%>