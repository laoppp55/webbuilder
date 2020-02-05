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


	int siteID = authToken.getSiteID();

	IUserManager uMgr = UserPeer.getInstance();
    List list = uMgr.getDepartments(siteID);
	int Count = list.size();
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<script language="javascript">
function createGroup()
{
  window.open("createdepartment.jsp","","top=100,left=100,width=360,height=360");
}
function edit(id)
{
   window.open("editdepartment.jsp?id="+id,"","top=100,left=100,width=360,height=360");
}
function del(id)
{
    window.open("deletedepartment.jsp?id="+id,"","top=100,left=100,width=360,height=360");
}
</script>
</head>
<%
	String[][] titlebars = {
		{ "部门管理", "" }
	};
	String [][] operations =
	{
	  {"<a href=javascript:createGroup();>创建部门</a>", ""},
    {"系统管理","index.jsp"},
	};
%>
<%@ include file="../inc/titlebar.jsp" %>
<br>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="90%" align=center>
<tr bgcolor="#eeeeee" class=tine>
<td width="15%" align=center>部门中文名称</td>
<td width="15%" align=center>部门英文名称</td>
<td align=center width="10%">电话</td>
<td align=center width="10%">经理</td>
<td align=center width="10%">副经理</td>
    <td align=center width="10%">主管领导</td>
    <td align=center width="10%">修改</td>
<td align=center width="10%">删除</td>
</tr>

<%
	for (int i=0; i<list.size(); i++)
	{
		Department dept = (Department)list.get(i);
%>

<tr bgcolor="#ffffff" class=line>
<td align="center">&nbsp;&nbsp;<%=dept.getCname()==null?"--":StringUtil.gb2iso4View(dept.getCname())%></td>
<td align="center">&nbsp;&nbsp;<%=dept.getEname()==null?"--":dept.getEname()%></td>
<td align="center"><%=dept.getTelephone() ==null?"--":StringUtil.gb2iso4View(dept.getTelephone())%></td>
<td align="center"><%=dept.getManager() == null?"--":StringUtil.gb2iso4View(dept.getManager())%></td>
<td align="center"><%=dept.getVicemanager() == null?"--":StringUtil.gb2iso4View(dept.getVicemanager())%></td>
    <td align="center"><%=dept.getLeader() == null?"--":StringUtil.gb2iso4View(dept.getLeader())%></td>
    <td align="center"><a href="#" onclick="edit(<%=dept.getId()%>)">修改</a> </td>
<td align="center"><a href="#" onclick="del(<%=dept.getId()%>)">删除</a> </td>
</tr>
<% }  %>

</table>
</html>
