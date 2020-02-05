<%@page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  int columnID = ParamUtil.getIntParameter(request, "column", 0);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet type="text/css" href="style.css">
</head>
<body bgcolor=#ffffff text=#000000 link=#0000ff vlink=#0000ff alink=#ff0000>
<center>
<span class="tabstyle"><b>>选择具体包含文件</b></span>
<hr width=100% size=1>
</center>
</body>
</html>