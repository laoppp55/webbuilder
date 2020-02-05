<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
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
  int columnID = ParamUtil.getIntParameter(request, "column", 0);
%>

<html>
<head>
<title>选择文章模板</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<frameset cols=160,*>
<frameset cols=* rows=0,*>
<frame src="treeforSelectModel.jsp" name=cmsleft>
<frame src="menu.html" name=menu>
</frameset>
<frame src="selectModelRight.jsp?column=<%=columnID%>" name=cmsright>
</frameset>
</html>
