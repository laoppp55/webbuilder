<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.dubboservice.service.AreaService" %>
<%@ page import="com.bizwink.po.Province" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%
    ApplicationContext appContext = SpringInit.getApplicationContext();
    List<Province> provinceList = null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("AreaService");
        provinceList= areaService.getProvince();
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (provinceList != null){
        jsondata = gson.toJson(provinceList);
        out.print(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }

    return;
%>