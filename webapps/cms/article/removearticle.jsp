<%@ page import="java.sql.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8" %>
<%@ page import="com.bizwink.util.JSON" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    int delwebflag = ParamUtil.getIntParameter(request, "delwebflag", 1);

    IArticleManager articleManager = ArticlePeer.getInstance();
    String editor = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int errcode = 0;

    try {
        if (articleID>0) errcode = articleManager.remove(articleID, siteid, editor, delwebflag);
    } catch (ArticleException e) {
        e.printStackTrace();
    }

    String jsonData = null;
    if (errcode == 0)
        jsonData =  "{\"result\":\"true\"}";
    else
        jsonData = "{\"result\":\"false\"}";
    JSON.setPrintWriter(response, jsonData,"utf-8");
%>