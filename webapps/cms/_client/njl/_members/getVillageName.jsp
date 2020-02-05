<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.dubboservice.service.AreaService" %>
<%@ page import="com.google.gson.Gson" %>
<%
    String villageCode = ParamUtil.getParameter(request,"code");
    ApplicationContext appContext = SpringInit.getApplicationContext();

    String villageName= null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("AreaService");
        villageName= areaService.getVillageName(villageCode);
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (villageName != null){
        jsondata = gson.toJson(villageName);
        out.print(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }

%>

