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
  String username       = (String)session.getAttribute("userid");
  String title          = ParamUtil.getParameter(request, "title");
  String content        = ParamUtil.getParameter(request, "content");
  String url            = ParamUtil.getParameter(request, "url");
  int startflag         = ParamUtil.getIntParameter(request, "startflag", 0);
  int forumid           = ParamUtil.getIntParameter(request, "forumid", 0);

  if((content != "")&&(content != null)){
    //content = content.replaceAll("<","&lt;");
    //content = content.replaceAll(">","&gt;");
    content = content.replaceAll("\n","<br>");
    content = content.replaceAll(" ","&nbsp;");
  }

  if((title != "")&&(title != null)){
    title = title.replaceAll("<","&lt;");
    title = title.replaceAll(">","&gt;");
  }

  if((url != "")&&(url != null)){
    //content = content.replaceAll("<","&lt;");
    //content = content.replaceAll(">","&gt;");
    content = content + "<br><br>";
    content = content + "<img src=\"" + url + "\">";
  }

  IBBSManager bbsMgr = BBSPeer.getInstance();
	IRegisterManager registerMgr = RegisterPeer.getInstance();
	Register register = new Register();
  BBS bbs = new BBS();

  if(startflag == 1) {
    //用户登录成功，判断bbs_user表中是否有该用户的信息，若没有则添加
    boolean bbs_existflag = bbsMgr.CheckBBSUser(username);
    if(!bbs_existflag){
      bbsMgr.insertBBSUser(username);
    }else{
      register = registerMgr.getAUser(username);
      int id = register.getID();
			    
      int postnum = bbsMgr.getBBSUserNUM("postnum",id)+1;
      //每发表一片新文章加2分
      int score = bbsMgr.getBBSUserNUM("score",id)+2;
      bbsMgr.updateBBSUser("logintime",id,0);
      bbsMgr.updateBBSUser("postnum",id,postnum);
      bbsMgr.updateBBSUser("score",id,score);
    }

    Timestamp nowtime = new Timestamp(System.currentTimeMillis());
    String ipaddress = request.getRemoteAddr();

	  //获得最大的threadid
		int threadid = bbsMgr.getMaxThreadID() + 1;

    //将用户的新帖插入tbl_thread表
    bbs = new BBS();
		bbs.setID(threadid);
    bbs.setForumID(forumid);
    bbs.setAnswerID(0);
    bbs.setThreadName(title);
    bbs.setThreadContent(content);
    bbs.setPostTime(nowtime);
    bbs.setAuthor(username);
    bbs.setIPAddress(ipaddress);
    bbs.setAnswerUser(username);
    bbs.setAnswerTime(nowtime);
    bbsMgr.PostThread(bbs);

    //用户发新帖，更新用户在线登录时间
    bbs = new BBS();
    bbs.setUserName(username);
    bbs.setLoginTime(nowtime);
    bbs.setIPAddress(ipaddress);

    //删除该用户在在线表中的记录
    bbsMgr.deleteOnline(bbs);

    //将新的用户登录时间插入用户在线表
    bbsMgr.insertOnline(bbs);

    //更新表tbl_column中的最后发表的人的信息
    int threadnum = bbsMgr.getNUM("threadnum",forumid);
    int topicnum = bbsMgr.getNUM("topicnum",forumid) + 1;
    bbsMgr.updateLastNum(threadnum,topicnum,forumid);
    bbsMgr.updateLastPost(username,title,nowtime,forumid);

    response.sendRedirect("list.jsp?forumid="+forumid+"&startflag=1");
  }
%>
<html>
<head>
<title>欢迎访问北京城建投资发展股份有限公司</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet href="images/chengjian.css" style="text/css">
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#FFFFFF">
<center>
<table width="778" border="0" cellspacing="0" cellpadding="0" height="36">
<tr>
<td background="images/index-bg01.gif" align="right">
<table border="0" cellspacing="0" cellpadding="0" width="250">
<tr>
<td><img src="images/button-home.gif" width="75" height="24"></td>
<td><img src="images/button-dichan.gif" width="75" height="24"></td>
<td><img src="images/button-touzi.gif" width="75" height="24"></td>
</tr>
</table>
</td>
<td bgcolor="#717171" width="1"></td>
</tr>
</table>
<table width="778" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="257"><img src="images/index-logo.gif" width="257" height="45"></td>
<td>&nbsp; </td>
</tr>
</table>
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
<%
  bbs = bbsMgr.getAFORUM(forumid);
%>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="tittle">
<tr>
<td><b><span class="tittle">您的位置:</span></b><span class="tittle"><a href="/bbs/index.jsp">城建论坛</a>
>> <a href="list.jsp?forumid=<%=forumid%>&startflag=1"><%=bbs.getBBSName()%></a></span></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/dotline.gif">
<tr>
<td><img src="images/space.gif" width="3" height="3"></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="txt">
<tr bgcolor="F1F2EC">
<form  method="post" action="searchuser.jsp" name="searchform">
<td>目前论坛总在线 <b>10</b> 人&nbsp;&nbsp;&nbsp;&nbsp;本论坛发贴总数 <b>1274</b>&nbsp;&nbsp;&nbsp;&nbsp;回复总数
<b>18471</b> [<a href="online.jsp" target=_blank>在线用户</a>] &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户查询&nbsp;
<input type="text" name="search_user" size=8>
&nbsp;
<input type=button value="查询" onClick="javascript:searchuser(this.form);" name="button">
</td>
</form>
</tr>
</table>
<script language="javascript">
function check(){
  if((postForm.title.value == "")||(postForm.title.value == null)){
    alert("请输入标题！");
    return false;
  }
  if((postForm.content.value == "")||(postForm.content.value == null)){
    alert("请输入帖子内容！");
    return false;
  }
  postForm.submit();
}
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="4">
<tr>
<td bgcolor="#FFFFFF" valign="top" align="center">
<%
  int userstatus = bbsMgr.getUserStatus(username);
  if(userstatus != 3){
%>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="txt">
<form name="postForm" method="post" action="posttopic2.jsp">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="forumid" value="<%=forumid%>">
<tr>
  <td>作者： <%=username%></td>
</tr>
<tr>
<td>标题：
<input type="text" name="title" size="60">
</td>
</tr>
<tr>
<td> 内容：
<textarea name="content" cols="80" rows="10"></textarea>
</td>
</tr>
<tr align="center">
<td>
<input type="button" name="Submit2" value="发表" onclick="javascript:return check();">
</td>
</tr>
</form>
</table>
<%}else{%>
  <font size=2 color=red>您现在无法发言</font>
<%}%>
</td>
</tr>
</table>
</td>
</tr>
</table>
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td><img src="images/space.gif" width="25" height="25"></td>
</tr>
</table>
<table width="778" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="49"><img src="images/index17.gif" width="49" height="36"></td>
<td valign="top" bgcolor="#EEEEEE">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td height="1" bgcolor="#BBBBBB"></td>
</tr>
<tr>
<td>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="48">&nbsp;</td>
<td width="111"><a href="sitemap.html"><img src="images/index-button15.gif" width="111" height="13" border="0"></a></td>
<td width="111"><a href="law.html"><img src="images/index-button16.gif" width="111" height="13" border="0"></a></td>
<td width="111"><a href="contactus.html"><img src="images/index-button17.gif" width="111" height="13" border="0"></a></td>
<td><a href="hezuo.html"><img src="images/index-button18.gif" width="111" height="13" border="0"></a></td>
<td><img src="images/index-bg05.gif" width="2" height="34"></td>
</tr>
</table>
</td>
</tr>
<tr bgcolor="#BBBBBB">
<td height="1"></td>
</tr>
</table>
</td>
<td width="6"><img src="images/index18.gif" width="6" height="36"></td>
</tr>
</table>
<table width="100" border="0" cellspacing="0" cellpadding="0">
<tr>
<td height="10"></td>
</tr>
</table>
<table width="778" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="265"><img src="images/index19.gif" width="257" height="34"></td>
<td bgcolor="#A5A5A5" width="1"></td>
<td align="center">
<table width="98%" border="0" cellspacing="0" cellpadding="3">
<tr>
<td>北京市朝阳区北土城西路11号 邮编：100029 <br>
电话：(010) 82275566 传真：(010) 82275533E_mail：bucid@bucid.com </td>
</tr>
</table>
</td>
</tr>
</table>
</center>
</body>
</html>

