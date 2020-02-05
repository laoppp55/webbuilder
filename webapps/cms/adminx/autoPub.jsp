<%@page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.server.*"
                 contentType="text/html;charset=GBK"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }


  String username = authToken.getUserID();
  String sitename = authToken.getSitename();
  String appPath = application.getRealPath("/");
  String scheme = request.getScheme();
  String servername = request.getServerName();
  int serverport = request.getServerPort();
  int socketPort = 8902;
  try
  {
    socketPort = Integer.parseInt(CmsServer.getInstance().getProperty("main.os.port"));
  }
  catch (NumberFormatException e) { }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<title></title>
</head>
<body>
<%
  String[][] titlebars = {
    { "自动发布", "" }
  };
  String[][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<br>
<table border=0 width="100%" align=center>
  <tr>
    <td align=center>
<applet
  codebase = "/webbuilder/"
  code     = "com.bizwink.applets.SchedulePublish.class"
  width    = "300"
  height   = "200"
  align    = "middle">
<param name="username" value="<%=username%>">
<param name="scheme" value="<%=scheme%>">
<param name="serverport" value="<%=serverport%>">
<param name="servername" value="<%=servername%>">
<param name="realpath" value="<%=appPath%>">
<param name="socketPort" value="<%=socketPort%>">
</applet>
    </td>
  </tr>
</table>
</body>
</html>
