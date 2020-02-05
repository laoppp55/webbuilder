
<%@ page import="java.sql.*,
    com.bizwink.cms.modelManager.*,
    com.bizwink.cms.security.*,
    com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.google.gson.Gson" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
    if( authToken == null ) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int templateID  = ParamUtil.getIntParameter(request,"template", 0);
    int columnID  = ParamUtil.getIntParameter(request,"column", 0);
    int modelType  = ParamUtil.getIntParameter(request,"isArticle", 0);
    boolean success = false;

    IModelManager modelMgr = ModelPeer.getInstance();
    try {
        success = modelMgr.setupDefault(templateID,columnID,modelType);
    } catch (ModelException e) {
        e.printStackTrace();
    }


    Map data = new HashMap();
    data.put("RESULT",success);
    data.put("PUBLISHRIGHT",SecurityCheck.hasPermission(authToken, 6));              //用户是否有发布权限
    data.put("MODELRIGHT",SecurityCheck.hasPermission(authToken, 2));                //用户是否有模板管理权限
    data.put("ADMINRIGHT",SecurityCheck.hasPermission(authToken, 54));               //用户是否有站点管理员权限
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response, jsondata,"utf-8");
%>