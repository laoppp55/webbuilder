<%@ page import="java.util.*,
		             com.bizwink.cms.util.*,
                 com.bizwink.cms.security.*"
		 contentType="text/html;charset=utf-8"
%>

<%
	Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
	if (authToken == null)
	{
		response.sendRedirect( "../login.jsp?url=member/editUserGroup.jsp" );
		return;
	}
	if (!SecurityCheck.hasPermission(authToken, 54))
	{
		response.sendRedirect("../error.jsp?message=无管理用户组的权限");
		return;
	}

	boolean success = false;
	int siteID = authToken.getSiteID();
	boolean doUpdate = ParamUtil.getBooleanParameter(request,"doUpdate");
	int groupID = ParamUtil.getIntParameter(request, "groupID", -1);
	String groupName = ParamUtil.getParameter(request, "groupName");

	IGroupManager groupMgr = GroupPeer.getInstance();
	List userList = groupMgr.getGroup_Users(siteID);

	if (doUpdate)
	{
		String[] suser = request.getParameterValues("suser");
		try
		{
			if (suser != null)
				groupMgr.update_UserGroups(suser, groupID);
			success = true;
		}
		catch (CmsException e)
		{
			e.printStackTrace();
		}
	}

	if (success)
	{
		response.sendRedirect("groupManage.jsp?msg=success");
		return;
	}

	String[] users = groupMgr.getUsers_Groups(groupID);
%>

<html>
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>
<%
	String[][] titlebars = {
			{ "所属组别", "" }
	};
	String [][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p>

	<form action="addUserGroup.jsp" method=post>
		<input type="hidden" name="doUpdate" value="true">
		<input type="hidden" name="groupID" value="<%=groupID%>">
		<font class=tine>用户组&nbsp;<b><%=groupName%></b>&nbsp;下的用户：</font>
<p>

<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="60%" align=center>
	<tr bgcolor="#eeeeee">
		<td align=center width="40%"><b>用户名</b></td>
		<td align=center width="30%"><b>用户姓名</b></td>
		<td align=center width="30%"><b>是否是该组成员</b></td>
	</tr>
	<%
		for (int i=0; i<userList.size(); i++)
		{
			User user = (User)userList.get(i);

			String userID = user.getID();
			String nickName = StringUtil.gb2iso4View(user.getNickName());
			String value = "";
			for (int j=0; j<users.length; j++)
				if (userID.trim().compareToIgnoreCase(users[j].trim()) == 0) value = "checked";
	%>
	<tr >
		<td>&nbsp;<%=userID%></td>
		<td>&nbsp;<%=(nickName==null)?"&nbsp;":nickName%></td>
		<td align=center><input type="checkbox" name="suser" value="<%=userID%>" <%=value%>></td>
	</tr>
	<%   }  %>
</table>
<p>
<center>
	<input type="submit" value="  确定  " <%if(userList.size()==0){%>disabled<%}%>>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="submit" value="  取消  " onclick="location.href='groupManage.jsp';return false;">
</center>
</form>

</body>
</html>
