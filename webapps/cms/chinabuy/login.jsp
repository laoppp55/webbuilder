<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*"
                contentType="text/html;charset=gbk"
%>

<%
  String username = ParamUtil.getParameter(request, "username");
  String password = ParamUtil.getParameter(request, "password", true);
  boolean doLogin = ParamUtil.getBooleanParameter(request, "doLogin");

  String errorMessage = "&nbsp;";

  if (doLogin)
  {
    IAuthManager authMgr = AuthPeer.getInstance();
    try
    {
        Auth authToken = authMgr.getAuth(username, password);
        if (authToken != null) {
            int siteid = authToken.getSiteID();
            session.setAttribute("CmsAdmin", authToken);
            session.setMaxInactiveInterval(3600);
            int modelnum = authMgr.getTemplateNum(siteid);
            //System.out.println("modelnum=" + modelnum);
            //System.out.println("siteid=" + siteid);
            if (modelnum == 0 && !username.equals("admin"))  {                    //转向模板选择页面
%>
<script type="text/javascript">
var ret = confirm("选择已经存在的模板？");
if (ret)
   <%response.sendRedirect("index.jsp");%>
else
   window.location="index.jsp";
</script>
<%
                //response.sendRedirect("register/webindex.jsp");
            } else                                                                  //转向登录成功页面
                response.sendRedirect("../index.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }
    catch (UnauthedException e)
    {
      errorMessage = "<font color=red>登陆失败!请重新输入用户名和密码!</font>&nbsp;&nbsp;&nbsp;&nbsp;";
    }
    doLogin = false;
  }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>欢迎登录站点超市</title>
<link href="coositecss.css" rel="stylesheet" type="text/css" />
</head>

<body>
<%@ include file="/sites/www_chinabuy360_cn/include/top.shtml"%>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="576">&nbsp;</td>
    <td width="350" align="left" valign="top"><img src="images/coosite_03.gif" width="350" height="68" /></td>
    <td width="74">&nbsp;</td>
  </tr>
</table>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="588" align="left" valign="top"><img src="images/coosite_1.gif" width="588" height="229" /></td>
    <td width="325" align="center" valign="top">
      <table width="325" border="0" cellspacing="0" cellpadding="0"> <form action="login.jsp" name="loginForm" method="POST">
	   <input type=hidden name=doLogin value=true>
		  <tr>
			<td width="34" height="29">&nbsp;</td>
			<td width="52">&nbsp;</td>
			<td width="207"><%=errorMessage%></td>
			<td width="32">&nbsp;</td>
		  </tr>
	    <tr>
		  <td>&nbsp;</td>
			<td align="left" valign="top" class="blue_font">用户名：</td>
			<td align="left" valign="bottom">
			<label>
		    <input name="username" type="text" class="txt_inde" />
		  </label>			</td>
			<td>&nbsp;</td>
	    </tr>
		  <tr>
			<td height="29"></td>
			<td></td>
			<td></td>
			<td></td>
		  </tr>
		  <tr>
			<td height="22">&nbsp;</td>
			<td align="left" valign="top" class="blue_font">密 &nbsp;码：</td>
			<td align="left" valign="top">
			<label>
			  <input name="password" type="password" class="txt_inde" />
			</label></td>
			<td>&nbsp;</td>
		  </tr>
		  <tr>
			<td height="29"></td>
			<td></td>
			<td></td>
			<td></td>
		  </tr>
		  <tr>
			<td></td>
			<td></td>
			<td align="left" valign="top"><input type=image src="images/enter_331.jpg" width="62" height="27" />&nbsp;&nbsp;&nbsp;<a href="/webbuilder/chinabuy/register.jsp"><img src="images/login_331.jpg" width="62" height="27" hspace="30" border="0" /></a></td>
			<td></td>
		  </tr>
	 </form> </table>
      <br />
      <img src="images/coosite_06.gif" width="320" height="1" vspace="20" /></td>
    <td align="left" valign="top"><img src="images/coosite_02.gif" width="87" height="229" /></td>
  </tr>
</table>
<%@ include file="/sites/www_chinabuy360_cn/include/low.shtml"%>
</body>
</html>
