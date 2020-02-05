<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="java.net.URLDecoder" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/2
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String markChineseName = ParamUtil.getParameter(request,"markname");
    if (markChineseName!=null) {
        markChineseName = URLDecoder.decode(markChineseName,"utf-8");
        if (markChineseName.length()>2) markChineseName = markChineseName.substring(1,markChineseName.length()-1);
    }

    IMarkManager markMgr = markPeer.getInstance();
    String markcode = null;
    markcode = markMgr.getMarkCode(markChineseName);

    String jsonData = null;
    if (markcode !=null)
        jsonData =  "{\"result\":\"" + markcode + "\"}";
    else
        jsonData = "{\"result\":\"nodata\"}";

    JSON.setPrintWriter(response, jsonData,"utf-8");
%>


