<%@ page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int ID = ParamUtil.getIntParameter(request, "ID", 0);
    int type = ParamUtil.getIntParameter(request, "type", 0);
%>

<html>
<head>
    <title>碎片管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<frameset border=0 frameborder=0 framespacing=0>
    <frame src="editColumnStyleRight.jsp?ID=<%=ID%>&type=<%=type%>" name=cmsright scrolling=auto marginheight=0
           marginwidth=5 noresize>
</frameset>
</html>