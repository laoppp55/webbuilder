<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.dubboservice.service.AreaService" %>
<%@ page import="com.google.gson.Gson" %>
<%
    String zoneCode = ParamUtil.getParameter(request,"code");
    ApplicationContext appContext = SpringInit.getApplicationContext();

    String zoneName= null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("AreaService");
        zoneName= areaService.getZoneName(zoneCode);
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (zoneName != null){
        jsondata = gson.toJson(zoneName);
        out.print(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }


%>
