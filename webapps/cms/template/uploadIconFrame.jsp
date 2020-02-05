<%@ page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
%>

<html>
<head>
    <title>上传图标</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<frameset cols=*.0 border=0 frameborder=0 framespacing=0>
    <frame src="uploadIcon.jsp" name=cmsright scrolling=auto marginheight=0 marginwidth=5
           noresize>
</frameset>
</html>