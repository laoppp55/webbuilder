<%@ page import="java.util.*,
		 com.bizwink.cms.security.*,
		 com.bizwink.cms.news.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%!	// page variables
	private final String YES = "<font color='#006600' size='-1'><b>是</b></font>";
	private final String NO  = "<font color='#ff0000' size='-1'><b>否</b></font>";
%>
<%
	Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
	if( authToken == null ) {
		response.sendRedirect( "../login.jsp" );
		return;
	}
	if (!SecurityCheck.hasPermission(authToken, "column")){
		response.sendRedirect("../error.jsp?message=No manage column's Right");
		return;
	}

	// get list of groups
	IColumnUserManager columnUserMgr = ColumnUserPeer.getInstance();
	List columnList;
	int columnCount =0;
	columnList = columnUserMgr.getColumns();
	columnCount = columnList.size();
	String msg = ParamUtil.getParameter(request,"msg");

%>
<html>
<head>
	<title><%=msg%></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>
<%	////////////////////
	String[][] titlebars = {
			{ "首页", "../main.jsp" },
			{ "栏目和用户间关系管理", "" }
	};
	String [][] operations = {
			{ "管理栏目", "../column/index.jsp" }
	};
%>
<%@ include file="../inc/titlebar.jsp" %>
<font class=line>栏目列表</font>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=80%>
	<tr bgcolor="#eeeeee" class=tine>
		<td width="30%" align=center class="listHeader"><b>栏目中文名称</b></td>
		<td width="30%" align=center>栏目英文名称</td>
		<td width="20%" align=center>栏目审核级别</td>
		<td align=center width="20%">管理栏目用户</td>

	</tr>

	<%	////////////////////////////////////
		// iterate through users, show info

		for ( int i=0; i<columnCount; i++) {

			Column column = (Column)columnList.get(i);
			String cName = column.getCName();
			String eName = column.getEName();
			int    level = column.getAuditLevel();
			int columnID = column.getID();

	%>

	<tr bgcolor="#ffffff" class=line>
		<td align=center><b><%= cName %></b></td>
		<td><%= eName %></td>
		<td><%= level %></td>
		<td align="center"><input type="radio" name="props" value=""
								  onclick="location.href='editColumnUser.jsp?colID=<%= columnID %>&ename=<%=eName%>';">
		</td>
	</tr>

	<% }  %>

</table>
</html>


