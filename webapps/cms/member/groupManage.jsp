<%@ page import="java.util.*,
		             com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk"
%>

<%
	Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
	if (authToken == null)
	{
		response.sendRedirect( "../login.jsp" );
		return;
	}
	if (!SecurityCheck.hasPermission(authToken, 54))
	{
		response.sendRedirect("../error.jsp?message=无管理用户的权限");
		return;
	}

	IGroupManager groupMgr = GroupPeer.getInstance();
	int siteID = authToken.getSiteID();

	List groupList = groupMgr.getGroups(siteID);
	int groupCount = groupList.size();
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<script language="javascript">
function createGroup()
{
  window.open("newgroup.jsp","","top=100,left=100,width=360,height=360");
}

function editGroup(groupID)
{
  window.open("editGroup.jsp?groupID="+groupID,"","top=100,left=100,width=360,height=360");
}

function grantGroup(groupID)
{
  window.open("grantRightToGroup.jsp?groupID="+groupID,"","top=100,left=100,width=400,height=360");
}

function addUser(groupID, groupName)
{
  window.location = "addUserGroup.jsp?groupID="+groupID+"&groupName="+groupName;
}
</script>
</head>
<%
	String[][] titlebars = {
		{ "组管理", "" }
	};
	String [][] operations =
	{
	  {"<a href=javascript:createGroup();>创建组</a>", ""},
		{"系统管理","index.jsp"},
	};
%>
<%@ include file="../inc/titlebar.jsp" %>
<br>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="90%" align=center>
<tr bgcolor="#eeeeee" class=tine>
<td width="20%" align=center>用户组名称</td>
<td width="30%" align=center>用户组描述</td>
<td align=center width="10%">属性</td>
<td align=center width="10%">授权</td>
<td align=center width="10%">添加用户</td>
<td align=center width="10%">删除</td>
</tr>

<%
	for (int i=0; i<groupCount; i++)
	{
		Group group = (Group)groupList.get(i);
		int groupID = group.getGroupID();
		String groupName = StringUtil.gb2iso4View(group.getGroupName());
		String groupDesc = group.getGroupDesc();
		if (groupDesc == null) groupDesc = "";
%>

<tr bgcolor="#ffffff" class=line>
<td>&nbsp;&nbsp;<%=groupName%></td>
<td>&nbsp;&nbsp;<%=StringUtil.gb2iso4View(groupDesc)%></td>
<td align="center"><input type="radio" name="props" onclick="editGroup(<%=groupID%>);"></td>
<td align="center"><input type="radio" name="props" onclick="grantGroup(<%=groupID%>);"></td>
<td align="center"><input type="radio" name="props" onclick="addUser(<%=groupID%>,'<%=groupName%>');"></td>
<td align="center"><input type="radio" name="props" onclick="location.href='removeGroup.jsp?group=<%=groupID%>&name=<%=groupName%>';"></td>
</tr>
<% }  %>

</table>
</html>
