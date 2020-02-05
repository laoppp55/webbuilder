<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                java.text.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.bucid.bbs.*,
                com.bucid.register.*" contentType="text/html;charset=gbk"
%>
<%
if ((session.getValue("UserClass")==null)||(!session.getValue("UserClass").equals("系统管理员")))
{
   response.sendRedirect("/bbs/index.jsp");
}
%>
<html><script language="JavaScript"></script></html>

<html>
<head>
<link rel=stylesheet href="images/chengjian.css" style="text/css">

<script language="JavaScript">
function Popup(url, window_name, window_width, window_height)
{
  settings=
      "toolbar=no,location=no,directories=no,"+
      "status=no,menubar=no,scrollbars=yes,"+
      "resizable=yes,width="+window_width+",height="+window_height;

    NewWindow=window.open(url,window_name,settings);
}

function icon(theicon) {
  document.input.message.value += " "+theicon;
  document.input.message.focus();
}
</script>

<title>欢迎访问北京城建投资发展股份有限公司</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body>
<center>
<%@include file="head.jsp"%>
<table width="778" border="0" cellspacing="0" cellpadding="0">
<tr>
<td>
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/dotline.gif">
<tr>
<td><img src="images/space.gif" width="3" height="3"></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="4">
<form method=post name="loginform" action="login.jsp">
<input type="hidden" name="startflag" value="1">
<tr>
<td><b><span class="tittle">您的位置: </span></b><span class="tittle"><a href="/bbs/index.jsp">城建论坛</a></span></td>
<%
  String userid = (String)session.getAttribute("userid");
	String userclass = (String)session.getAttribute("UserClass");
	if(userid != null){
%>
    <td>欢迎您：<%=userid%></td><%if(userclass.equals("系统管理员")){%><td><a href="board_manager.jsp">管理</a></td><%}%>
<%}else{%>
<td>
用户名：<input type="text" name="username" size=8>
密码：<input type="password" name="password" size=8>
<input type="submit" value="登录">
</td>
<%}%>
<td align="right" width=40><a href="reg1.jsp">注册</a>&nbsp;&nbsp;</td>
<%
if(userid != null){
%>
<td align="left" width=40><a href="logout.jsp">退出</a>&nbsp;&nbsp;</td>
<%}%>
</tr>
</form>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/dotline.gif">
<tr>
<td><img src="images/space.gif" width="3" height="3"></td>
</tr>
</table>
</td></tr></table>
<br><br>
<table  bordercolorlight="#0000FF" cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" width="760" align="center" border=1>
  <tr>
    <td class="tablerow" colspan="7" bgcolor="#FFFFFF" height="16">
      <table border="0" cellspacing="0" width="100%" cellpadding="0">
        <tr>
          <td width="20%" align="center"><font color="#333399"><b><a href='board_manager.jsp'>论坛管理</a></b></font></td>
          <td width="40%" align="left"><font color="#333399"><b><a href='user_manager.jsp'><font color="red">用户管理</font></a></b></font></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr bgcolor="#9CCFFF">
    <td width="80" align="center" height="14">用户名</td>
    <td width="80" align="center" height="14" >用户性别</td>
	<td width="100" align="center" height="14">用户等级</td>
    <td width="100" align="center" height="14">发帖/访问次数</td>
	<td width="80" align="center" height="14">禁启状态</td>
	<!--td width="86" align="center" height="14">修改</td-->
    <td width="80" align="center" height="14">删除 </td>
  </tr>
<%
	String User_Name = "";
	String User_Id = "";
	String Submit_Button = "";
	int id = 0;
	int sex = 0;
	String grade = "";
	int postnum = 0;
	int loginnum = 0;

  Submit_Button=request.getParameter("Submit");

  IBBSManager bbsMgr = BBSPeer.getInstance();
	BBS bbs = new BBS();
	List list = bbsMgr.getAllBBSUser();

	for(int i=0;i<list.size();i++){
		bbs = (BBS)list.get(i);
		id = bbs.getID();
    User_Id = bbs.getUserID();
		User_Name = bbs.getUserName();
		sex = bbs.getSex();
		grade = bbs.getGrade();
		loginnum = bbs.getLoginNum();
		postnum = bbs.getPostNum();
%>
  <tr bgcolor="#F7FBFF">
    <td width="80" align="center" height="14"><!--a href='modifyuser_manager.jsp?modifyid=<%//=User_Id%>'--><%=User_Id%><!--/a--></td>
    <td width="80" align="center" height="14" ><%if(sex == 0){%>男<%}else{%>女<%}%></td>
	<td width="100" align="center" height="14"><%=grade%></td>
    <td width="100" align="center" height="14"><%=postnum%>/<%=loginnum%></td>
	<td width="80" align="center" height="14">
<%
  if (bbs.getLockFlag()==0)
    out.println("<a href='mod_userflag.jsp?id="+id+"&isuser=1&startflag=1'>启用</a></td>");
  else
    out.println("<a href='mod_userflag.jsp?id="+id+"&isuser=0&startflag=1'>禁用</a></td>");
%>
	<!--td width="86" align="center" height="14"><a href='modifyuser_manager.jsp?modifyid=<%//=User_Id%>'>修改</a></td-->
    <td width="80" align="center" height="14"><a href='delete_user.jsp?id=<%=id%>&startflag=1' onclick="{if(confirm('确定要删除该用户吗?')){return true;}return false;}">删除</a></td>

  </tr>
<%
  }
%>

</form>
  <tr bgcolor="#F7FBFF">
   <td align="center" height="10" width="17">&nbsp;</td>
    <td colspan="6" height="10" align="right" valign="middle">
      <div align="left"></div>
      <form name="form1" method="post" action="user_manager.jsp" >
        <div align="center">用户名
          <input type="text" name="username" >
          <input type="submit" name="Submit" value="搜索">
          <input type="submit" name="Submit" onclick="{if(confirm('确定删除该用户所发表的所有贴子?')){return true;}return false;}" value="删除该用户的所有贴子">
        </div>
      </form>
      </td>
  </tr>

</table>
</td></tr></table>
<br><br><br><br>
<%@include file="tail.jsp"%>
