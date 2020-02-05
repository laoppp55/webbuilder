<%@page contentType="text/html;charset=gbk"%>
<%@page import="java.util.*,
  		com.bizwink.cms.server.*,
  		com.bizwink.cms.security.*,
  		com.bizwink.cms.news.*,
  		com.bizwink.cms.util.*"
%>

<%
    String username = ParamUtil.getParameter(request, "username");
    String password = ParamUtil.getParameter(request, "password", true);
    boolean doLogin = ParamUtil.getBooleanParameter(request, "doLogin");

    String errorMessage = "&nbsp;";
    CmsServer.getInstance().init();

    if (doLogin)
    {
        IAuthManager authMgr = AuthPeer.getInstance();

        try
        {
            Auth authToken = authMgr.getAuth(username, password);
            session.setAttribute("CmsAdmin", authToken);
            session.setMaxInactiveInterval(3600);
            response.sendRedirect("index1.jsp");
        }
        catch (UnauthedException e)
        {
            errorMessage = "<font color=blue>登陆失败!请重新输入用户名和密码!</font>&nbsp;&nbsp;&nbsp;&nbsp;";
        }
        doLogin = false;
    }
%>

<html>
<head>
<title>内容管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet href="images/cms.css" style="text/css">
</head>

<body bgcolor="#8B121D" text="#000000" leftmargin="0" topmargin="0">
<center>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td height="8" bgcolor="#B30121"></td>
</tr>
<tr>
<td bgcolor="#000000" height="3"></td>
</tr>
<tr>
<td height="1" bgcolor="#FFFFFF"></td>
</tr>
<tr>
<td bgcolor="#000000" height="4"></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td bgcolor="#B30121">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td><img src="images/title_new.gif" width="293" height="96"></td>
<td align="right"><img src="images/logo_new.gif" width="184" height="96"></td>
</tr>
</table>
</td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td bgcolor="#C77C83" height="1"></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td background="images/bg_new.gif" align="center">
<p>&nbsp;</p>
<table width="519" border="0" cellspacing="0" cellpadding="0">
<tr>
<td><img src="images/login_new.gif" width="519" height="34"></td>
</tr>
<tr>
<td bgcolor="#D3979C" valign="top">
<table width="100%" border="0" cellspacing="1" cellpadding="0">
<tr>
<td background="images/bg2.gif" align="center"> <br>
<form action="index.jsp" name="loginForm" method="POST">
<input type=hidden name=doLogin value=true>
<%=errorMessage%>
<table width="50%" border="0" cellspacing="0" cellpadding="3" height="80">
  <tr>
    <td>
      <b><font color="#FFFFFF"><% out.println("用户：");%></font></b>
      <input type="text" name="username" maxlength=20>
    </td>
  </tr>
  <tr>
    <td>
      <b><font color="#FFFFFF"><% out.println("密码：");%></font></b>
      <input type="password" name="password" maxlength=8>
    </td>
  </tr>
<!--
  <tr>
    <td align=right><a href="register.jsp"><b>注 册</b></a></td>
  </tr>
-->
</table>
<br>
<input type="image" name="submit" src="images/btn_login.gif">&nbsp;&nbsp;&nbsp;&nbsp;
<a href="register.jsp"><img name="register" src="images/btn_reg.gif" border=0></a>&nbsp;&nbsp;&nbsp;&nbsp;
</form>
</td>
</tr>
<tr>
<td background="images/bg3.gif" align="center">
<table width="85%" border="0" cellspacing="0" cellpadding="4">
<tr>
<td><b><font color="#FFFFFF">欢迎使用CMS文章发布系统！请输入用户名和口令进行身份确认，<br>
以便进入使用发布系统。<a href="member/getPassword.jsp"><b>忘记密码</b></a></font></b></td>
</tr>
</table>
<b></b></td>
</tr>
</table>
</td>
</tr>
</table>
<p>&nbsp;</p>
</td>
</tr>
<tr>
<td bgcolor="#B30121" align="center"><font color="#FFFFFF"><br>
电子邮箱:<a href="mailto:customer@bizwink.com.cn">customer@bizwink.com.cn</a> Bizwink Software Inc &copy;公司版权所有2001 <br>
<br>
</font></td>
</tr>
</table>
</center>
</body>
</html>
<script language="javascript">document.forms[0].username.focus();</script>