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
<title>文章内容图片特效标记</title>
</head>
<frameset border=0 frameborder=0 framespacing=0>
<frame src="listarticlefor_articlexuanimages.jsp?column=<%=columnID%>&mark=<%=markid%>&doaction=<%=action%>" name=cmsright scrolling=auto marginheight=0 marginwidth=5 noresize>
</frameset>
</html>