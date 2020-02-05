<%@page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int gsiteid  = ParamUtil.getIntParameter(request, "gsid", 0);
  int usegc    = ParamUtil.getIntParameter(request, "usegc", 0);
%>

<html>
<head>
<title>增加相关文章</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<frameset cols=160,* border=0 frameborder=0 framespacing=0>
<frame src="treeforPubColumns.jsp?usegc=<%=usegc%>&gsid=<%=gsiteid%>" name=cmsleft scrolling=auto marginheight=0 marginwidth=0 noresize>
</frameset>
<frame src="addPubColumnsRight.jsp" name=cmsright scrolling=auto marginheight=0 marginwidth=5 noresize>
</frameset>
</html>