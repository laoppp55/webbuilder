<%@page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=gbk"%>
<%
/*    OlympicMembers ure = (OlympicMembers)session.getAttribute("AuthWeb");
    if (ure == null) {
        response.sendRedirect(response.encodeRedirectURL("/olympicfamily/index.jsp"));
        return;
    }*/
%>

<html>
<head>
    <title>上载图片及flash</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<frameset border=0 frameborder=0 framespacing=0>
    <frame src="uploadpic.jsp" name=cmsright scrolling=auto marginheight=0 marginwidth=5 noresize>
</frameset>
</html>