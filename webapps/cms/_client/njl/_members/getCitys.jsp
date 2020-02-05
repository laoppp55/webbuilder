<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.dubboservice.service.AreaService" %>
<%@ page import="com.bizwink.po.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%
    String provinceid = ParamUtil.getParameter(request,"provinceid");
    ApplicationContext appContext = SpringInit.getApplicationContext();

    List<City> cityList= null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("AreaService");
        cityList= areaService.getCity(provinceid);
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (cityList != null){
        jsondata = gson.toJson(cityList);
        out.print(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }

    return;

%>