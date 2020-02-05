<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"
%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if( authToken == null )
    {
        response.sendRedirect( "../login.jsp?url=member/createMember.jsp" );
        return;
    }
    if (!SecurityCheck.hasPermission(authToken, 54))
    {
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    int groupID = ParamUtil.getIntParameter(request,"groupID",0);    //被授权人的组id，不是登录者的组id
%>

<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>用户管理</title>
</head>
<frameset cols=160,*>
<frameset cols=* rows=0,*>
<frame src=columntree.jsp name=cmsleft>
<frame src=menu.html name=menu>
</frameset>
<frame src="doGrantForGroup.jsp?groupID=<%=groupID%>" name=cmsright>
</frameset>
</html>