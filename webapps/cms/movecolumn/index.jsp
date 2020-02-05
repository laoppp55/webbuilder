<%@page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null)
  {
    response.sendRedirect( "../login.jsp" );
    return;
  }
  if (!SecurityCheck.hasPermission(authToken,54))
  {
    request.setAttribute("message","无管理用户的权限");
    response.sendRedirect("editMember.jsp?user="+authToken.getUserID());
    return;
  }
%>

<html>
<head>
<title>栏目迁移</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<frameset cols="200,*" border=0 frameborder=0 framespacing=0>
  <frameset frameborder=0 framespacing=0 border=0 cols=* rows=0,*>
    <frame src="movetree.jsp" name=cmsleft scrolling=auto marginheight=0 marginwidth=0 noresize>
    <frame marginwidth=5 marginheight=5 src=menu.html name=menu noresize scrolling=auto frameborder=0>
  </frameset>
  <frame src="columnsmain.jsp" name=cmsright scrolling=auto marginheight=0 marginwidth=5 noresize>
</frameset>
</html>