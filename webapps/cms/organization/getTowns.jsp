<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.service.AreaService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.po.Town" %>
<%@ page import="java.math.BigDecimal" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String zoneid = filter.excludeHTMLCode(ParamUtil.getParameter(request,"zoneid"));
    ApplicationContext appContext = SpringInit.getApplicationContext();

    List<Town> townList=null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("areaService");
        townList= areaService.getTowns(BigDecimal.valueOf(Integer.parseInt(zoneid)));
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