<%@ page language="java" import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*" %>

<%
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	int userId = Integer.parseInt((String)request.getParameter("userid"));
	User user = null;
	user = iwenba.jifen(userId);
	
	out.print(user.getUsergrade());
%>
