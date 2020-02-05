<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteId = ParamUtil.getIntParameter(request, "siteid", 0);
%>

<html>
<head>
    <title>文章列表属性</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<frameset cols=* border=0 frameborder=0 framespacing=0>
    <frameset frameborder=0 framespacing=0 border=0 cols=* rows=0,*>
        <frame src="treeforSelectTColumn.jsp?siteid=<%=siteId%>" name=cmsleft scrolling=auto marginheight=0 marginwidth=0>
        <frame marginwidth=5 marginheight=5 src="menu.html" name=menu scrolling=auto frameborder=0>
    </frameset>
</frameset>
</html>