<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'ok.jsp' starting page</title>
<%
	//String UserName = (String)request.getParameter("name");
	Uregister urg = (Uregister)session.getAttribute("urg");
	//UregisterPeer  regMgr = new UregisterPeer();
	//Uregister urg = new Uregister();
	//urg = regMgr.getUserInfo(UserName);
%>
  </head>
  
  <body>
   <table>
   	<tr>
   	 <td>用户名:</td><td><%= urg.getMemberid()%></td>
   	</tr>
   	<tr>
   	 <td>邮箱:</td><td><%= urg.getEmail()%></td>
   	</tr>
   	<tr>
   	 <td>所在国家:</td><td><%= urg.getCountru()%></td>
   	</tr>
   	<tr>
   	 <td>所在城市:</td><td><%= urg.getCity()%></td>
   	</tr>
   	<tr>
   	 <td>移动电话:</td><td><%= urg.getPhoen()%></td>
   	</tr>
   </table>
  </body>
</html>
