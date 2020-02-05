<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int columnId = ParamUtil.getIntParameter(request, "column", 0);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>引用分类</title>
</head>
<frameset cols=160,*>
    <frameset cols=* rows=0,*>
        <frame src=treeforSelectType.jsp?column=<%=columnId%> name=cmsleft>
        <frame src=menu.html name=menu>
    </frameset>
    <frame src="selectArticleType.jsp?column=<%=columnId%>" name=cmsright>
</frameset>
</html>