<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.publish.IPublishManager" %>
<%@ page import="com.bizwink.cms.publish.PublishPeer" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.publish.PublishException" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String appPath = application.getRealPath("/");
    String sitename = authToken.getSitename();
    String username = authToken.getUserID();
    int option = authToken.getPublishFlag();
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    int currentID = ParamUtil.getIntParameter(request, "column", 0);

    IPublishManager publishMgr = PublishPeer.getInstance();
    try {
        publishMgr.CreateMenuTreePage(currentID, siteid, appPath, sitename, username, option, -1);
        response.sendRedirect("../column/index.jsp?rightid="+rightid);
    } catch (PublishException e) {
        e.printStackTrace();
    }
%>
<html>
<head>
    <title>发布导航树</title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel=stylesheet type="text/css" href="../style/global.css">
</head>
<body>
</body>
</html>