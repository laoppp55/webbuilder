<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

%>
<html>
<head>
    <title>供应商</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<frameset cols=*.0 border=0 frameborder=0 framespacing=0>
    <frame src="gong.jsp" name=example05 scrolling=auto marginheight=0 marginwidth=5
           noresize>
</frameset>
</html>