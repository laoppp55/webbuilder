<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.service.AreaService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.po.City" %>
<%@ page import="java.math.BigDecimal" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String provinceid = filter.excludeHTMLCode(ParamUtil.getParameter(request,"provinceid"));
    ApplicationContext appContext = SpringInit.getApplicationContext();

    List<City> cityList= null;
    if (appContext!=null) {
        AreaService areaService = (AreaService)appContext.getBean("areaService");
        cityList= areaService.getCity(BigDecimal.valueOf(Integer.parseInt(provinceid)));
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (cityList != null){
        jsondata = gson.toJson(cityList);
        System.out.println(jsondata);
        out.print(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }

    return;

%>