<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int columnID = ParamUtil.getIntParameter(request, "column", 0);
  int modeltype = ParamUtil.getIntParameter(request, "modeltype", 0);
  String action = ParamUtil.getParameter(request, "doaction");
  int markid = ParamUtil.getIntParameter(request, "mark", 0);  
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>图片特效标记</title>
</head>
<frameset cols=160,* border=0 frameborder=0 framespacing=0>
<frameset frameborder=0 framespacing=0 border=0 cols=* rows=0,*>
<frame src=treeforxuanimages.jsp?modeltype=<%=modeltype%> name=cmsleft scrolling=auto marginheight=0 marginwidth=0 noresize>
<frame marginwidth=5 marginheight=5 src=menu.html name=menu noresize scrolling=auto frameborder=0>
</frameset>
<frame src="listarticlefor_xuanimages.jsp?column=<%=columnID%>&mark=<%=markid%>&doaction=<%=action%>" name=cmsright scrolling=auto marginheight=0 marginwidth=5 noresize>
</frameset>
</html>