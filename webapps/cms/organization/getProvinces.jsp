<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.service.AreaService" %>
<%@ page import="com.bizwink.po.Province" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    ApplicationContext appContext = SpringInit.getApplicationContext();
    List<Province> provinceList = null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("areaService");
        provinceList= areaService.getProvince();
    } else {
        System.out.println("init failed");
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (provinceList != null){
        jsondata = gson.toJson(provinceList);
        System.out.println(jsondata);
        out.print(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }

    return;
%>