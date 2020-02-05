<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.pic.IPicManager" %>
<%@ page import="com.bizwink.cms.pic.PicPeer" %>
<%@ page import="com.bizwink.cms.news.Turnpic" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.google.gson.Gson" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-6-3
  Time: 下午1:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int articleid = ParamUtil.getIntParameter(request,"article",0);
    IPicManager picManager = PicPeer.getInstance();
    List<Turnpic> turnpics = picManager.getTurpics(articleid);

    if (turnpics!=null) {
        Gson gson = new Gson();
        String jsondata = gson.toJson(turnpics);
        out.println(jsondata);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }
    return;
%>