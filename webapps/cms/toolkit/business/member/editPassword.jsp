<%@ page import="com.bizwink.cms.util.*,
		 com.bizwink.cms.audit.*,
                 com.bizwink.cms.security.*"
         contentType="text/html;charset=gbk"%>
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
    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");

    boolean error = false;
    String pass = null;

    if (doUpdate)
    {
        pass = ParamUtil.getParameter(request, "pass1");

        if (userID == null || pass == null)
        {
            error = true;
        }
    }

    if (doUpdate && !error)
    {
        try
        {
            IAuditManager auditMgr = AuditPeer.getInstance();
            auditMgr.updatePassword(userID, pass);

            if (type == 1)
            {
                response.sendRedirect("admin_index.jsp");
                return;
            }
            else
            {
                response.sendRedirect("index.jsp");
                return;
            }
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
            { "修改密码", "" }
    };
    String [][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>

<p>
    &nbsp;&nbsp;&nbsp;修改用户 <b><%= userID%></b>&nbsp;的密码
<p>
    getUserColsFromTBL_Members_R
<form action="editPassword.jsp" name="updateForm" onsubmit="javascript:return CheckPass();">
    <input type="hidden" name="doUpdate" value="true">
    <input type="hidden" name="userid" value="<%=userID%>">
    <input type="hidden" name="type" value="<%=type%>">
    &nbsp;&nbsp;
    请输入新密码：&nbsp;&nbsp;<input type="password" name="pass1" size=12 maxlength=8>
    <br><br>
    &nbsp;&nbsp;
    请再次输入密码：<input type="password" name="pass2" size=12 maxlength=8>
    <br><br><br>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit" value="确  定">&nbsp;&nbsp;
    <%if (type == 1){%>
    <input type="submit" name="cancel" value="取  消" style="font-weight:bold;" onclick="location.href='admin_index.jsp';return false;">
    <%}else{%>
    <input type="submit" name="cancel" value="取  消" style="font-weight:bold;" onclick="location.href='index.jsp';return false;">
    <%}%>
</form>

<script language="JavaScript" type="text/javascript">
    <!--
    document.updateForm.cancel.focus();
    //-->

    function CheckPass()
    {
        if (document.updateForm.pass1.value == "" || document.updateForm.pass2.value == "")
        {
            alert("密码不能为空");
            return false;
        }
        else if (document.updateForm.pass1.value != document.updateForm.pass2.value)
        {
            alert("两次输入的密码不相同");
            return false;
        }
        else
        {
            return true;
        }
    }
</script>

</body>
</html>
