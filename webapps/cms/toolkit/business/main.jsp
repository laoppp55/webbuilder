<%@ page import = "java.util.*,
    		   com.bizwink.cms.util.*,
    		   com.bizwink.cms.security.*"
    		   contentType="text/html;charset=gbk"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if( authToken == null )
    {
        response.sendRedirect( "login.jsp" );
        return;
    }

    String userID = authToken.getUserID().toString().toLowerCase().trim();
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=style/global.css>
</head>

<BODY LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr bgcolor="#000000">
    <td height="2"></td>
  </tr>
</table>

<%
    String[][] titlebars = {
        { "首页", "" }
    };
    String[][] operations=null;
%>
<%@ include file="inc/titlebar.jsp" %>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr bgcolor="#000000">
    <td height="2"></td>
  </tr>
</table>

<TABLE BORDER=0 CELLPADDING=0 CELLSPACING=0 width=100%>
<tr>
<td height=300 align=center class=hdr>欢迎使用北京盈商动力软件开发有限公司内容发布管理系统</td>
</tr>
<tr>
<td class=cur>请送反馈意见到    <a href=mailto:Customer@bizwink.com.cn>Customer@bizwink.com.cn</a>
</td>
</tr>
</table>
</body>
</html>