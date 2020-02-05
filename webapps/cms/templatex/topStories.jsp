<%@page import="com.bizwink.cms.security.*,
                com.bizwink.cms.markManager.*,
                com.bizwink.cms.util.*"
        contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int markID = ParamUtil.getIntParameter(request, "mark", 0);

    IMarkManager markMgr = markPeer.getInstance();
    String str = "";
    if (markID > 0)
        try {
            str = markMgr.getAMarkContent(markID);
        } catch (markException e) {
            e.printStackTrace();
        }
    System.out.println("columnID==" + columnID);
    System.out.println("markID==" + markID);
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Pragma" content="no-cache">
    <title>热点文章</title>
</head>
<frameset rows=40,* border=0 frameborder=0 framespacing=0 name=parentFrame>
    <frame src="topStories_1.jsp?column=<%=columnID%>" name=header scrolling=no marginheight=5 marginwidth=5 noresize>
    <%if (str.indexOf("[COLUMN]") == -1) {%>
    <frame src="topStories_2.jsp?column=<%=columnID%>&mark=<%=markID%>" name=main scrolling=auto noresize>
    <%}else{%>
    <frame src="topStories_3.jsp?column=<%=columnID%>&mark=<%=markID%>" name=main scrolling=auto noresize>
    <%}%>
</frameset>
</html>
