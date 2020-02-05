<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String imgstr = ParamUtil.getParameter(request, "imgstr");
  int columnID  = ParamUtil.getIntParameter(request, "column", 0);
  imgstr = StringUtil.replace(imgstr, "'", "\"");
%>

<html>
<head>
<title>图片修改</title>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>
<table boder=0 width="600">
<tr>
<td>
<iframe src='editimage.jsp?column=<%=columnID%>&imgstr=<%=imgstr%>' width="600" marginwidth="0" height="380" marginheight="0" scrolling="no" frameborder="0"></iframe>
</td>
</tr>
</table>
</body>
</html>