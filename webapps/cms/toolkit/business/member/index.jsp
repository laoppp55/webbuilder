<%@ page import="java.util.*,
		 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk"
%>

<%!
  private final String YES = "<font color='#006600' size='-1'><b>是</b></font>";
  private final String NO  = "<font color='#ff0000' size='-1'><b>否</b></font>";
%>
<%@ include file="../../../include/auth.jsp"%>
<%
  if (!SecurityCheck.hasPermission(authToken,54))
  {
    request.setAttribute("message","无管理用户的权限");
    response.sendRedirect("editMember.jsp?user="+authToken.getUserID());
    return;
  }
  int siteid = authToken.getSiteID();
  String authUsername = authToken.getUserID();

  IUserManager userMgr = UserPeer.getInstance();
  List userList;
  int userCount =0;
  userList = userMgr.getUsers(siteid);
  userCount = userList.size();
  String msg = ParamUtil.getParameter(request,"msg");
%>

<html>
<head>
<title>系统用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<script language="javascript">
function popup_window() {
    window.open( "addUser.jsp","","top=100,left=100,width=500,height=300,resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no");
}

function edit_window(userid) {
  window.open( "addUser.jsp?userid="+userid,"","top=100,left=100,width=500,height=300,resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no");
}

function grant_window(userid) {
  window.open( "grantRightToUser.jsp?userid="+userid,"","top=100,left=100,width=550,height=450,resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no");
}
</script>
</head>
<%
	String str1 = "";
	String str2 = "";
	String str3 = "";
	String str4 = "";
	String str5 = "";

	if (authUsername.equals("admin"))
	{
		str1 = "用户管理";
		str2 = "创建用户";
	}

	String[][] titlebars = {
	        { "首页", "../main.jsp" },
		{ str1, "" }
	};

        String[][] operations = {
                {str5, "fragManage.jsp"},
	        {"<a href=javascript:popup_window()>" + str2 + "</a>", ""},
		{str3, "groupManage.jsp"},
                {str4, "siteIPManage.jsp?siteid="+authToken.getSiteID()+"&dname="+authToken.getSitename()}
        };
%>
<%@ include file="../inc/titlebar.jsp" %>
<font class=line>用户列表</font>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=100%>
<tr bgcolor="#eeeeee" class=tine>

<td width="9%" align=center class="listHeader"><b>用户标识</b></td>
<td width="9%" align=center>用户名称</td>
<td width="16%" align=center>电子邮件</td>
<td width="10%" align=center>电话</td>
<% if (authUsername.equals("admin")) { %>
<td align=center width="5%">属性</td>
<td align=center width="5%">删除</td>
<% } %>
</tr>

<%
  for ( int i=0; i<userCount; i++)
  {
    User user = (User)userList.get(i);
    String userID = user.getID();
    String nickName = StringUtil.gb2iso4View(user.getNickName());
    String email = user.getEmail();
    String phone = user.getPhone();
    if (phone == null)
      phone = "";
%>
<tr bgcolor="#ffffff" class=line>
<td align=center><b><%= userID %></b></td>
<td><%= nickName==null?"&nbsp;":nickName %></td>
<td><%= email==null?"&nbsp;":email %></td>
<td><%= phone==null?"&nbsp;":phone %>&nbsp;</td>
<% if (authUsername.equals("admin")) { %>
<td align="center">
<input type="radio" name="props" value="" onclick='javascript:edit_window("<%= userID %>");'></td>
  <% if (!"admin".equals(userID)) { %>
  <td align="center">
  <input type="radio" name="props" value="" onclick="location.href='removeUser.jsp?userid=<%= userID %>';"></td>
  <%}else{%>
  <td align="center">&nbsp;</td>
  <%}%>
<% } %>
</tr>

<% }  %>

</table>
</html>