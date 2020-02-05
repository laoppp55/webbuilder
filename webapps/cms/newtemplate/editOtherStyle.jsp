<%@ page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=gbk" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int type = ParamUtil.getIntParameter(request, "type", 0);
    int ID = ParamUtil.getIntParameter(request, "ID", 0);
    String referer = request.getHeader("Referer");
    int fromflag = 0;
    if((referer != null)&&(referer.indexOf("addNavBar.jsp") != -1))
        fromflag = 1;
%>

<html>
<head>
    <title>碎片管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<frameset border=0 frameborder=0 framespacing=0>
    <frame src="editOtherStyleRight.jsp?type=<%=type%>&ID=<%=ID%>&fromflag=<%=fromflag%>" name=cmsright scrolling=auto marginheight=0
           marginwidth=5 noresize>
</frameset>
</html>