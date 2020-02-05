<%@page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=gbk"%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int columnID = ParamUtil.getIntParameter(request, "column", 0);
	int markID = ParamUtil.getIntParameter(request, "mark", 0);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title></title>
</head>
<frameset cols=160,* border=0 frameborder=0 framespacing=0>
  <frameset frameborder=0 framespacing=0 border=0 cols=* rows=0,*>
    <frame src=add_include_21.jsp name=cmsleft scrolling=auto marginheight=0 marginwidth=0 noresize>
    <frame marginwidth=5 marginheight=5 src=menu.html name=menu noresize scrolling=auto frameborder=0>
  </frameset>
  <frameset rows="50%,*">
    <frame src="add_include_22.jsp?column=<%=columnID%>" name="cmsright">
    <frame src="add_include_23.jsp?column=<%=columnID%>&mark=<%=markID%>" name=main2 scrolling=auto marginheight=0 marginwidth=5 noresize>
  </frameset>
</frameset>
</html>
