<%@page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=gbk"%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int columnID = ParamUtil.getIntParameter(request, "column", 0);
  int styleID = ParamUtil.getIntParameter(request, "ID", 0);
  int type = ParamUtil.getIntParameter(request, "type", 0);
%>

<html>
<head>
<title>列表样式</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<frameset cols=170,* border=0 frameborder=0 framespacing=0>
<frameset frameborder=0 framespacing=0 border=0 cols=* rows=0,*>
<frame src="treeforStyle.jsp?ID=<%=styleID%>&type=<%=type%>" name=cmsleft scrolling=auto marginheight=0 marginwidth=0 noresize>
<frame marginwidth=5 marginheight=5 src=menu.html name=menu noresize scrolling=auto frameborder=0>
</frameset>
<frame src="editStyleRight.jsp?column=<%=columnID%>&ID=<%=styleID%>&type=<%=type%>" name=cmsright scrolling=auto marginheight=0 marginwidth=5 noresize>
</frameset>
</html>