<%@ page import="com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="com.bizwink.util.JSON" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int articleID = ParamUtil.getIntParameter(request, "id", 0);
    String from = ParamUtil.getParameter(request, "fromflag");
    int errcode = 0;

    if (from == null) from = "";
    if (from.equalsIgnoreCase("a") || from.equalsIgnoreCase("c")) {
        IArticleManager articleMgr = ArticlePeer.getInstance();
        errcode = articleMgr.updatecancle(articleID);
    }

    String jsonData = null;
    if (errcode == 0)
        jsonData =  "{\"result\":\"true\"}";
    else
        jsonData = "{\"result\":\"false\"}";
    JSON.setPrintWriter(response, jsonData,"utf-8");
%>
