<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.bizwink.cms.extendAttr.ExtendAttrPeer" %>
<%@ page import="com.bizwink.cms.extendAttr.IExtendAttrManager" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String company = authToken.getDepartment();
    String username = authToken.getUserID();
    int columnid = ParamUtil.getIntParameter(request,"column",0);

    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    String indexstr = extendMgr.generateIndexNumOfBjsjs(company,username,columnid);

    Map data = new HashMap();
    data.put("DATA",indexstr);
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>