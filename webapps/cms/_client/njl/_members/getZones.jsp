<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.dubboservice.service.AreaService" %>
<%@ page import="com.bizwink.po.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%
    String cityid = ParamUtil.getParameter(request,"cityid");
    ApplicationContext appContext = SpringInit.getApplicationContext();

    List<Zone> zoneList= null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("AreaService");
        zoneList= areaService.getZones(cityid);
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (zoneList != null){
        jsondata = gson.toJson(zoneList);
        out.print(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }

    return;

%>