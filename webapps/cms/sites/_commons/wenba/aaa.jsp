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
		
	%>
	<%
		List userlist = null;
		List list = null;
		IWenbaManager iwenba = wenbaManagerImpl.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		userlist = iwenba.getUserIDList();
		String userID;
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
		<%
			List datelist = null;
			List ulist = null;
			List list_month = null;
			List list_month_status = null;
			datelist = iwenba.DateCon();
			//String d1=datelist.get(0).toString();
			//String d2=datelist.get(1).toString();
		%>
			
		<%
		for(int i=0;i<userlist.size();i++){
			userID = userlist.get(i).toString();
			
			//ulist = iwenba.getUserAnwNum(SuserID,d1,d2);
			ulist = iwenba.getUserAnwNum2(userID);
			list_month = iwenba.getUserNum_month(userID);
			list_month_status = iwenba.getUserNum_month_Status(userID);
		%>
			<tr>
				<td><span class="STYLE1">用户ID:</span>&nbsp;&nbsp;<%= userID%></td>
				<td><span class="STYLE2">周答题数：</span>&nbsp;&nbsp;<%= ulist.size()%></td>
				<td><span class="STYLE3">月答题数：</span>&nbsp;&nbsp;<%= list_month.size() %></td>
				<td><span class="STYLE4">月最佳回答数：</span>&nbsp;&nbsp;<%= list_month_status.size() %></td>
				<% 
					if(list_month.size()!=0){
						float ii = (float)list_month_status.size()/(float)list_month.size();
						DecimalFormat df5 = new DecimalFormat("0.00");
						ii = ii*100;
						String res = df5.format(ii)+"%";
				%>
				<td><span class="STYLE5">采纳率：</span><%= res%></td>
				<%} %>
			</tr>
			<%
				
			}
			%>
		</table>
	</body>
</html>