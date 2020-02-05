<%@ page language="java" import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=gbk"%>
<%@ page import="java.net.*" %>
<%@ page import="java.text.ParsePosition" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.bizwink.user.User" %>
<%@ page import="com.bizwink.user.IUserManager" %>
<%@ page import="com.bizwink.user.UserPeer" %>
<%@ page import="java.text.DecimalFormat" %>
<html>
	<head>
	<%
		IUserManager userMgr = UserPeer.getInstance();
		IWenbaManager iwenba = wenbaManagerImpl.getInstance();
		User cuser = null;
		cuser =userMgr.getLogin(request.getParameter("name"),request.getParameter("pass"));
		User user_n = null;
		int id=44;
		String name = "fanglicheng";
		user_n = iwenba.getUser_jifen(cuser.getUserid(),cuser.getUsername());
		
	%>
	<style type="text/css">
<!--
.STYLE1 {color: #999999}
.STYLE2 {color: #660033}
.STYLE3 {color: #6633FF}
.STYLE4 {color: #6699FF}
.STYLE5 {color: #CC0066}
-->
    </style>
	</head>
	<body>
		<table>
			<tr>
				<td><span class="STYLE1">用户积分:</span>&nbsp;&nbsp;<%= user_n.getUsergrade()%></td>
		</table>
	</body>
</html>