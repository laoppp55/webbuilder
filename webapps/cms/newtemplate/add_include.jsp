<%@page import="com.bizwink.cms.security.*,
                com.bizwink.cms.markManager.*,
                com.bizwink.cms.util.*"
                contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

  int columnID = ParamUtil.getIntParameter(request, "column", 0);
  int markID = ParamUtil.getIntParameter(request, "mark", 0);


%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache">
<title>包含文件</title>
</head>
<frameset rows=40,* border=0 frameborder=0 framespacing=0 name=parentFrame>
<frame src="add_include_1.jsp?column=<%=columnID%>" name=header scrolling=no marginheight=5 marginwidth=5 noresize>
<frame src="add_include_2.jsp?column=<%=columnID%>&mark=<%=markID%>" name=main scrolling=auto noresize>
</frameset>
</html>
