<%@ page import="java.sql.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.security.*"
                 contentType="text/html;charset=gbk"%>
<%
Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
if (authToken == null) {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=style/global.css>
<title>商品信息管理</title>
</head>
<frameset rows=60,*,30 border=0 frameborder=0 framespacing=0 name=parentFrame>
<frame src=header.jsp name=header scrolling=no marginheight=5 marginwidth=5 noresize>
<frame src=main.jsp name=main scrolling=auto noresize>
<frame src=footer.jsp name=bottom scrolling=no noresize>
</frameset>
</html>
