<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.dubboservice.service.AreaService" %>
<%@ page import="com.google.gson.Gson" %>
<%
    String cityCode = ParamUtil.getParameter(request,"code");
    ApplicationContext appContext = SpringInit.getApplicationContext();

    String cityName= null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("AreaService");
        cityName= areaService.getCityName(cityCode);
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (cityName != null){
        jsondata = gson.toJson(cityName);
        out.print(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }

%>

