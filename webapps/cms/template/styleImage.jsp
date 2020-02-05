<%@ page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int type = ParamUtil.getIntParameter(request, "type", 0);
%>

<html>
<head>
    <title>碎片管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<frameset border=0 frameborder=0 framespacing=0>
    <frame src="styleImageRight.jsp?type=<%=type%>" name=picright scrolling=auto marginheight=0 marginwidth=5 noresize>
</frameset>
</html>