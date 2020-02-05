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
  int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
%>

<html>
<head>
<title>模板管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<frameset cols=160,*>
<frameset cols=* rows=0,*>
<frame src="test.jsp?rightid=<%=rightid%>" name=cmsleft>
<frame src="menu.html" name=menu>
</frameset>
<frame src="pagesmain.jsp" name=cmsright>
</frameset>
</html>
