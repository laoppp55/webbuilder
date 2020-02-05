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
    
    int ID = ParamUtil.getIntParameter(request, "ID", 0);
%>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>引用栏目文章</title>
</head>
<frameset cols=160,*>
<frameset cols=* rows=0,*>
<frame src=treeforSelectColumn.jsp?ID=<%=ID%> name=cmsleft>
<frame src=menu.html name=menu>
</frameset>
<frame src="selectColumnArticle.jsp?ID=<%=ID%>" name=cmsright>
</frameset>
</html>