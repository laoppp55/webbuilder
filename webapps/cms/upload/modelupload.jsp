<%@ page import="com.bizwink.cms.security.*,
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

  int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
  int columnID = ParamUtil.getIntParameter(request, "column", 0);
  int languageType = ParamUtil.getIntParameter(request, "language", 0);
%>

<html>
<head>
<title>模板管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<frameset cols=160,*>
<frameset cols=* rows=0,*>
<frame src="treeforUpload.jsp?rightid=<%=rightid%>&column=<%=columnID%>" name=cmsleft>
<frame src="menu.html" name=menu>
</frameset>
<frame src="do_modelupload.jsp?column=<%=columnID%>&language=<%=languageType%>" name=cmsright>
</frameset>
</html>
