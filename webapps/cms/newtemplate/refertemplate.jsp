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
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
%>

<html>
<head>
    <title>引用模板</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<frameset cols=160,* border=0 frameborder=0 framespacing=0>
    <frameset frameborder=0 framespacing=0 border=0 cols=* rows=0,* scrolling=auto marginheight=0 marginwidth=0>
        <frame src="treeforReferTemplate.jsp?column=<%=columnID%>" name=cmsleft>
        <frame marginwidth=5 marginheight=5 src="menu.html" name=menu scrolling=auto frameborder=0>
    </frameset>
    <frame src="referTemplateRight.jsp?column1=<%=columnID%>&column2=<%=columnID%>&rightid=<%=rightid%>" name=cmsright>
</frameset>
</html>
