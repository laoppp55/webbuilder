<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                java.text.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.bizwink.bbs.bbs.*,
                com.bizwink.webapps.register.*" contentType="text/html;charset=gbk"
%>
<%
  int loginflag = ParamUtil.getIntParameter(request, "loginflag", -1);
  IBBSManager bbsMgr = BBSPeer.getInstance();
  BBS bbs = new BBS();

  List forumlist = new ArrayList();
  forumlist = bbsMgr.getAllColumn();
%>
<html>
<head>
<title>欢迎访问北京城建投资发展股份有限公司</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet href="images/chengjian.css" style="text/css">
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#FFFFFF">
<%
  if(loginflag == 0){
%>
<script language="javascript">
alert("您输入的用户名或密码错误！");
</script>
<%
  }
%>
<center>
<%@include file="head.jsp"%>
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td><img src="images/space.gif" width="10" height="10"></td>
</tr>
</table>
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
    <td>欢迎您：<%=userid%></td><%if(userclass != null){if(userclass.equals("系统管理员")){%><td><a href="board_manager.jsp">管理</a></td><%}}%>
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
<br>
<table width="100%" border="0" cellspacing="1" cellpadding="4" class="txt">
<tr>
<td width="15%" bgcolor="#0877AF" align="center"><font color="#FFFFFF"><b>论坛名称</b></font></td>
<td width="15%" bgcolor="#0877AF" align="center"><font color="#FFFFFF"><b>论坛管理员
</b></font></td>
<td width="15%" bgcolor="#0877AF" align="center"><font color="#FFFFFF"><b>回复/人气</b></font></td>
<td bgcolor="#0877AF" align="center"><font color="#FFFFFF"><b>最后更新 </b></font></td>
</tr>
</table>
<%
  String forumname = "";
  int forumid = 0;
	String pic = "";
	int threadnum = 0;
	int topicnum = 0;
	String lasttopic = "";
	String lastposter = "";
	String manager = "";
	Timestamp lastposttime = new Timestamp(System.currentTimeMillis());

  for(int j=0;j<forumlist.size();j++){
    bbs = (BBS)forumlist.get(j);
    forumname = bbs.getBBSName();
    forumid = bbs.getID();
		pic = bbs.getPic();
		threadnum = bbs.getThreadNum();
		topicnum = bbs.getTopicNum();
		lasttopic = bbs.getLastTopic();
		lastposter = bbs.getLastPoster();
		lastposttime = bbs.getLastPostTime();
		manager = bbs.getManager();
%>
<table width="100%" border="0" cellspacing="1" cellpadding="4" class="txt">
<tr bgcolor="#F4F4F4">
<td width="15%" align="center">
<table border="0" cellspacing="0" cellpadding="4">
<tr>
<td bgcolor="#FFFFFF"><a href="list.jsp?forumid=<%=forumid%>&startflag=1"><img src="images/<%=pic%>" width="80" height="80" border="0"></a></td>
</tr>
<tr>
<td bgcolor="#FFFFFF" align="center"><b><a href="list.jsp?forumid=<%=forumid%>&startflag=1"><%=forumname%></a></b></td>
</tr>
</table>
</td>
<td align="center" width="15%"><%=manager==null?"":manager%></td>
<td bgcolor="#F4F4F4" width="15%"><!--今日： 19<br-->
主题：<%=topicnum%><br>
回复： <%=threadnum%> </td>
<td>主题： <%=lasttopic==null?"":lasttopic%><br>
最后发表： <%=lastposter==null?"":lastposter%><br>
<%=lastposttime==null?"":lastposttime.toString()%></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/dotline2.gif">
<tr>
<td><img src="images/space.gif" width="1" height="1"></td>
</tr>
</table>
<%}%>

</td>
</tr><div style="position: absolute; top: -978px;left: -898px;"><a href="http://www.91cssf.com">传世私服</a> <a href="http://www.jidiba.com">传世私服</a> <a href="http://www.aiqc.com.cn">传世私服</a> <a href="http://www.58923.com">传奇世界私服</a> <a href="http://www.on-ba.cn">传世私服</a> <a href="http://www.xinyingyy.com">天龙八部私服</a> <a href="http://www.gzmix.com">传世私服</a> <a href="http://www.xa8lad.cn">魔域私服</a> <a href="http://www.ppxnet.cn">传世私服</a> <a href="http://www.90door.cn">dnf外挂</a> <a href="http://www.blueth.cn">传世私服</a></div>
</table>
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td><img src="images/space.gif" width="25" height="25"></td>
</tr>
</table>
<%@include file="tail.jsp"%>
</center>
</body>
</html>
</html>   