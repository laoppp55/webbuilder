<%@ page import="java.util.*,
		             com.bizwink.cms.util.*,
		             com.bizwink.cms.audit.*,
                 com.bizwink.cms.security.*"
                 contentType="text/html;charset=gbk"
%>
<%@ page import="com.bizwink.joincompany.IJoincompanyManager" %>
<%@ page import="com.bizwink.joincompany.JoincompanyPeer" %>

<%


	String userID = ParamUtil.getParameter(request, "name");
	int type = ParamUtil.getIntParameter(request, "type", 0);
	boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int  id=ParamUtil.getIntParameter(request,"id",-1);
	if (doUpdate)
	{
		String pass = ParamUtil.getParameter(request, "pass1");
        IJoincompanyManager jpeer= JoincompanyPeer.getInstance();
        jpeer.updateJoincompanyPass(pass,id);

        
        if (type == 1)
			out.write("<script>window.close();</script>");
		else
			out.write("<script>window.close();</script>");
		return;
	}
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>
<%
	String[][] titlebars = {
		{ "修改密码", "" }
	};
	String [][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>

<p>
&nbsp;&nbsp;&nbsp;<font class=tine>修改用户 <b><%= userID%></b>&nbsp;的密码</font>
<p>

<form action="houupdate.jsp" name="updateForm" onsubmit="javascript:return CheckPass();">
<input type="hidden" name="doUpdate" value="true">
<input type="hidden" name="name" value="<%=userID%>">
<input type="hidden" name="type" value="<%=type%>">
    <input type="hidden" name="id" value="<%=id%>">
&nbsp;&nbsp;
<font class=tine>请输入新密码：</font><input type="password" name="pass1" size=12 class=tine>
<br><br>
&nbsp;&nbsp;
<font class=tine>再次输入密码：</font><input type="password" name="pass2" size=12 class=tine>
<br><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="submit" value=" 确定 ">&nbsp;&nbsp;
<%if (type == 1){%>
<input type="button" value=" 取消 " onclick="location.href='sitlist.jsp';return false;">
<%}else{%>
<input type="button" value=" 取消 " onclick="location.href='sitlist.jsp';return false;">
<%}%>
</form>

<script language="JavaScript">
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
