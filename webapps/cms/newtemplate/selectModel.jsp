<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    //System.out.println("columnid=" + columnID);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>模板管理</title>
</head>
<frameset cols=160,*>
    <frameset cols=* rows=0,*>
        <frame src="dirTreeMenu.jsp?column=<%=columnID%>" name="cmsleft">
        <frame src=menu.html name=menu>
    </frameset>
    <frame src="listfile.jsp?column=<%=columnID%>" name="cmsright">
</frameset>
</html>
