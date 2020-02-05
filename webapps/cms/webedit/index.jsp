<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int rightID = ParamUtil.getIntParameter(request, "right", 0);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
%>

<html>
<head>
    <title>文件夹管理</title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
</head>
<frameset cols=160,*>
    <frameset cols=* rows=0,*>
        <frame src="webtree.jsp?right=<%=rightID%>" name=cmsleft>
        <frame src=menu.html name=menu>
    </frameset>
    <frame src="webmain.jsp?column=<%=columnID%>" name=cmsright>
</frameset>
</html>