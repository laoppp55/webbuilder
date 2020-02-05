<%@page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int columnID = ParamUtil.getIntParameter(request,"column",0);
  int markID = ParamUtil.getIntParameter(request,"mark",0);
%>

<html>
<head>
<title>栏目列表属性</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<frameset cols=160,* border=0 frameborder=0 framespacing=0>
<frameset frameborder=0 framespacing=0 border=0 cols=* rows=0,*>
<frame src="treeforColumnList.jsp" name=cmsleft scrolling=auto marginheight=0 marginwidth=0 noresize>
<frame marginwidth=5 marginheight=5 src=menu.html name=menu noresize scrolling=auto frameborder=0>
</frameset>
<frame src="addColumnRight.jsp?column=<%=columnID%>&mark=<%=markID%>" name=cmsright scrolling=auto marginheight=0 marginwidth=5 noresize>
</frameset>
</html>