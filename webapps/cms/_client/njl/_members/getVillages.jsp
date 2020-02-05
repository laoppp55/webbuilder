<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.dubboservice.service.AreaService" %>
<%@ page import="com.bizwink.po.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%
    String townid = ParamUtil.getParameter(request,"townid");
    ApplicationContext appContext = SpringInit.getApplicationContext();

    List<Village> villageList= null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("AreaService");
        villageList= areaService.getVillages(townid);
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (villageList != null){
        jsondata = gson.toJson(villageList);
        out.print(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }

    return;

%>