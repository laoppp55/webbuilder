<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"
%>
<%
	Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
	if (authToken == null)
	{
		response.sendRedirect( "../login.jsp?url=member/createMember.jsp" );
		return;
	}
	if (!SecurityCheck.hasPermission(authToken, 54))
	{
		response.sendRedirect("../error.jsp?message=无管理用户的权限");
		return;
	}

	int siteid        = authToken.getSiteID();
	String groupName  = ParamUtil.getParameter(request,"groupname");
	String groupDesc  = ParamUtil.getParameter(request,"groupdetail");
	boolean doCreate  = ParamUtil.getBooleanParameter(request,"doCreate");
	List rightList	  = ParamUtil.getParameterValues(request,"rightList");

	if (doCreate)
	{
		IGroupManager groupMgr = GroupPeer.getInstance();
		Group group = new Group();

		group.setGroupName(groupName);
		group.setGroupDesc(groupDesc);
		group.setSiteID(siteid);
		group.setRightList(rightList);
		groupMgr.create(group);
		out.println("<SCRIPT LANGUAGE=javascript>opener.history.go(0);window.close();</SCRIPT>");
    return;
	}

  IRightsManager rightsManager = RightsPeer.getInstance();
  rightList = rightsManager.getRights();       //获取所有权限
%>

<html>
<head>
<title>创建用户组</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<script LANGUAGE="JavaScript" SRC="../js/check.js"></script>
<SCRIPT LANGUAGE=javascript>
function checkinput(form)
{
  if (!InputValid(form.groupname, 1, "string", 1, 2, 12,"用户组名称"))
    return (false);
  var el = form.rightList;
  for (var i = el.options.length-1; i >=0; i--) {
    el.options[i].selected = true;
  }
  return true;
}

var rights_sel = "";
function selectList(srcList,desList)
{
	seleflg = false;
	for(i = srcList.length - 1; i >= 0; i--)
	{
		if(srcList.options[i].selected == true)
		{
			var oOption = document.createElement("OPTION");
			oOption.value = srcList.options[i].value;
			oOption.text = srcList.options[i].text;
			desList.add(oOption);
			srcList.options[i] = null;
			seleflag = true;
		}
		else
		{
			continue;
		}
	}
	if (!seleflag)
	{
		alert ("您没有选择权限，请选择！");
		return false;
	}
}
</SCRIPT>
</head>

<body bgcolor="#cccccc">
<form name="createForm" method="post" onsubmit="return checkinput(this);">
<input type="hidden" name="doCreate" value="true">
<table width="100%" border="0">
  <tr height="40">
    <td width="25%" align=right>用户组名称：</td>
    <td>&nbsp;<input type="text" name="groupname" maxlength="30"></td>
  </tr>
  <tr height="40">
    <td align=right>用户组描述：</td>
    <td>&nbsp;<input type="text" name="groupdetail" maxlength="100" size=32></td>
  </tr>
  <tr>
    <td align=right>权限列表：</td>
    <td>
      <table width="80%" border="0" cellspacing=2 cellpadding=1>
        <tr>
          <td width="44%">备选权限<br>
            <select name="leftList" size="13" multiple style="width:100;font-size:9pt">
            <%for (int i=0; i<rightList.size(); i++) {
                int rightid = ((Rights)(rightList.get(i))).getRightID();
                String rightname =  StringUtil.gb2iso4View(((Rights)(rightList.get(i))).getRightCName());
            %>
            <option value="<%=rightid%>">&nbsp;<%=rightname%></option>
            <%}%>
            </select>
          </td>
          <td valign="middle">
            <input type="button" name="add" value=" > " onclick="return selectList(this.form.leftList,this.form.rightList)"><br><br>
            <input type="button" name="delete" value=" < " onclick="return selectList(this.form.rightList,this.form.leftList)">
          </td>
          <td width="44%" valign="top" align="center">授与权限<br>
            <select name="rightList" size="13" multiple style="width:100;font-size:9pt"></select>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr height="50">
    <td colspan="2" align="center">
      <input type="submit" class=tine value="  增加  ">&nbsp;&nbsp;
      <input type="button" class=tine value="  取消  " onclick="parent.window.close();">
    </td>
  </tr>
</table>
</form>
</body>
</html>
