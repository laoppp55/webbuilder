<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.dubboservice.service.AreaService" %>
<%@ page import="com.bizwink.po.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%
    String zoneid = ParamUtil.getParameter(request,"zoneid");
    ApplicationContext appContext = SpringInit.getApplicationContext();

    List<Town> townList=null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("AreaService");
        townList= areaService.getTowns(zoneid);
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (townList != null){
        jsondata = gson.toJson(townList);
        out.print(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }

    return;

%>