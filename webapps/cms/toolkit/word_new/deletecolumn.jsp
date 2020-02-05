<%@ page import="com.bizwink.webapps.leaveword.*" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    int id = ParamUtil.getIntParameter(request,"id",0);
    boolean docreate = ParamUtil.getBooleanParameter(request,"doCreate");
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    if(docreate) {
         wordMgr.deleteColumn(siteid,id);
        out.write("<script language=\"javascript\">alert(\"信息删除成功！\");window.opener.location='columns.jsp';window.close();</script>");
    }
%>
<html>
<head>
    <title>
        删除留言分类信息
    </title>
</head>
<body>
<table width="100%" border="1" cellpadding="3" cellspacing="1" class="css_002">
    <form name="columnForm" method="post" action="deletecolumn.jsp">
        <input type="hidden" name="doCreate" value="true">
        <input type="hidden" name="id" value="<%=id%>">
        <tr>
            <td align="center"><input type="submit" name="ok" value="确认"></td>
            <td><input type="button" name="cancel" value="返回" onclick="javascript:window.close()"></td>
        </tr>
    </form>
</table>
</body>
</html>