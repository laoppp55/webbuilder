<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"
%>
<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
    if( authToken == null ) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
%>

<html><head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>用户管理</title>
</head>
<%
    String userid = filter.excludeHTMLCode(request.getParameter("userid"));
    String file = "newuser.jsp";
    if (userid != null) {
        file ="editUser.jsp?userid="+userid;
    }
%>

<frameset cols=160,* border=0 frameborder=0 framespacing=0>
    <frameset frameborder=0 framespacing=0 border=0 cols=* rows=0,*>
        <frame src=columntree.jsp name=cmsleft scrolling=auto marginheight=0 marginwidth=0 noresize>
        <frame marginwidth=5 marginheight=5 src=menu.html name=menu noresize scrolling=auto frameborder=0>
    </frameset>
    <frame src=<%=file%> name=cmsright scrolling=auto marginheight=0 marginwidth=5 noresize>
</frameset>
</html>