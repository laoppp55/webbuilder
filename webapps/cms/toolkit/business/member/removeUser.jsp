<%@ page import="java.util.*,
		 com.bizwink.cms.util.*,
                 com.bizwink.cms.security.*"
         contentType="text/html;charset=gbk"
        %>
<%@ include file="../../../include/auth.jsp"%>
<%
    if (!SecurityCheck.hasPermission(authToken, 54))
    {
        request.setAttribute("message","无管理用户的权限");
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    String userID = ParamUtil.getParameter(request, "userid");
    int type = ParamUtil.getIntParameter(request, "type", 0);
    boolean doDelete = ParamUtil.getBooleanParameter(request, "doDelete");

    boolean error = (userID == null);
    if (userID.equals("admin"))
    {
        request.setAttribute("message","不能删除超级用户");
        response.sendRedirect("../error.jsp");
        return;
    }

    if (doDelete && !error)
    {
        IUserManager userMgr = UserPeer.getInstance();
        try{
            User user = userMgr.getUser(userID);
            userMgr.remove(user,userID);

            if (type == 1)
            {
                response.sendRedirect("admin_index.jsp");
                return;
            }
            else
            {
                response.sendRedirect("index.jsp");
                return;			}
        }
        catch (CmsException ue)
        {
            ue.printStackTrace();
            error = true;
        }
    }
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
</head>

<%
    //用户列表
    String[][] titlebars = {
            { "", "index.jsp" },
            { "删除用户", "" }
    };
    String [][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p>

    &nbsp;&nbsp;删除用户 <b><%= userID%></b>?

<p>

<form action="removeUser.jsp" name="deleteForm">
    <input type="hidden" name="doDelete" value="true">
    <input type="hidden" name="userid" value="<%=userID%>">
    <input type="hidden" name="type" value="<%=type%>">
    &nbsp;&nbsp;
    <input type="submit" value="确  定">&nbsp;&nbsp;
    <%if (type == 1){%>
    <input type="submit" name="cancel" value="取  消" style="font-weight:bold;" onclick="location.href='admin_index.jsp';return false;">
    <%}else{%>
    <input type="submit" name="cancel" value="取  消" style="font-weight:bold;" onclick="location.href='index.jsp';return false;">
    <%}%>
</form>

<script language="JavaScript" type="text/javascript">
    <!--
    document.deleteForm.cancel.focus();
    //-->
</script>

</body>
</html>
