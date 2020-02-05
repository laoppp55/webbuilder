<%@ page import="java.util.*,
  		 com.bizwink.cms.register.*,
  		 com.bizwink.cms.util.*"
  		 contentType="text/html;charset=gbk"
%>

<%
  boolean error = false;
  String errormsg = "";
  boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");

  String UserID = null;
  String SiteName = null;

  if (doCreate)
  {
    UserID = StringUtil.iso2gb(ParamUtil.getParameter(request, "UserID"));
    SiteName = StringUtil.iso2gb(ParamUtil.getParameter(request, "SiteName"));

    if (UserID == null || SiteName == null)
    {
      error = true;
      errormsg = "用户名和域名不能为空，请重新填写！";
    }
  }

  if (doCreate && !error)
  {
    try
    {
      IRegisterManager regMgr = RegisterPeer.getInstance();

      regMgr.getPassword(UserID, SiteName);

      errormsg = "请等待我们的确认，如信息填写正确，密码将会发送到您的信箱，请您耐心等候！";
    }
    catch (Exception e)
    {
      e.printStackTrace();
      errormsg = "用户名和域名不能为空，请重新填写！";
    }
  }

  if (doCreate)
  {
%>
<script language=javascript>
alert("<%=errormsg%>");
window.location = "../index.jsp";
</script>
<%
    return;
  }
%>

<html>

<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<title>取回密码</title>
<script language=javascript>
function Form_Check()
{
    if (document.RegForm.UserID.value == "")
    {
    	alert("用户名不能为空！");
    	return false;
    }
    else if (document.RegForm.SiteName.value == "")
    {
    	alert("域名不能为空！");
    	return false;
    }
    else
    {
    	return true;
    }
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" id="all" leftmargin="10" topmargin="10" marginwidth="10" marginheight="10" link="#000020" vlink="#000020" alink="#000020">
<br><p align="center"><b><font face="黑体" size="4">取&nbsp;回&nbsp;密&nbsp;码</font></b></p>
<div align="center">
  <center>

<form method="POST" action="getPassword.jsp" name="RegForm" onsubmit="javascript:return Form_Check();">
<input type=hidden name="doCreate" value="true">
<table border="1" width="45%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00 height="160">
  <tr>
    <td width="100%" bgcolor="#006699" align="right" height="20" colspan="2">
      <p align="center"><font color="#FFFFFF"><b>所有信息必须填写正确，系统才会将密码发送到您注册的邮箱！</b></font></td>
  </tr>
  <tr>
    <td width="21%" bgcolor="#EFEFEF" align="right" height="30"><b>用户名：</b></td>
    <td width="79%" bgcolor="#EFEFEF" height="30">&nbsp;<input maxlength=20 type="text" name="UserID" size="15" style="border-style: solid; border-width: 1">
       <font color=red>(用于登录的帐号)</font></td>
  </tr>
  <tr>
    <td width="21%" align="right" height="30"><b>域&nbsp; 名：</b></td>
    <td width="79%" height="30">&nbsp;<input maxlength="80" type="text" name="SiteName" size="30" style="border-style: solid; border-width: 1"></td>
  </tr>
  <tr>
    <td width="21%" align="center" height="30" colspan="2">
    如有其它问题，请发送邮件到系统管理员信箱&nbsp;<b><a href="mailto:customer@bizwink.com.cn">customer@bizwink.com.cn</a></b>
    </td>
  </tr>
</table>
<p><input type="submit" value="提  交" name="Submit">&nbsp;&nbsp;&nbsp;
   <input type="button" value="返  回" name="GoBack" onclick="history.go(-1)"></p>
</form>

  </center>
</div>

</body>
</html>
