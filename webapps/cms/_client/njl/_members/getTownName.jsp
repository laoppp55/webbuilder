<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.dubboservice.service.AreaService" %>
<%@ page import="com.google.gson.Gson" %>
<%
    String townCode = ParamUtil.getParameter(request,"code");
    ApplicationContext appContext = SpringInit.getApplicationContext();

    String townName= null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("AreaService");
        townName= areaService.getTownName(townCode);
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (townName != null){
        jsondata = gson.toJson(townName);
        out.print(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }


%>
