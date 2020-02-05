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
  String username       = ParamUtil.getParameter(request, "username");
  String password       = ParamUtil.getParameter(request, "password");
  String title          = ParamUtil.getParameter(request, "title");
  String content        = ParamUtil.getParameter(request, "content");
  String url            = ParamUtil.getParameter(request, "url");
  int startflag         = ParamUtil.getIntParameter(request, "startflag", 0);
  int loginflag         = ParamUtil.getIntParameter(request, "loginflag", 0);
  int forumid           = ParamUtil.getIntParameter(request, "forumid", 0);

  IRegisterManager registerMgr = RegisterPeer.getInstance();
	Register register = new Register();
  IBBSManager bbsMgr = BBSPeer.getInstance();
  BBS bbs = new BBS();

  if((content != "")&&(content != null)){
    content = content.replaceAll("\n","<br>");
    content = content.replaceAll(" ","&nbsp;");
  }

  if((title != "")&&(title != null)){
    title = title.replaceAll("<","&lt;");
    title = title.replaceAll(">","&gt;");
  }

  if((url != "")&&(url != null)){
    content = content + "<br><br>";
    content = content + "<img src=\"" + url + "\">";
  }

  if(loginflag == 1) {
    String locstr = "window.location=\"posttopic1.jsp?forumid="+forumid+"\";";
    out.println("<script language=javascript>");
    out.println("alert(\"您输入的用户名或密码错误！\");");
    out.println(locstr);
    out.println("</script>");
  }

  if(startflag == 1) {
    boolean existflag = false;

    existflag = registerMgr.userLogin(username,password,0);
    if(existflag){
			register = registerMgr.getAUser(username);
			String grade = register.getGrade();
			int id = register.getID();
      session.setAttribute("userid",username);
      session.setAttribute("password",password);
			session.setAttribute("UserClass",grade);

      //用户的登录成功计数
      int loginnum = registerMgr.getUserLoginNum(username) + 1;
      registerMgr.updateUserLoginNum(id, loginnum);

      int postnum = bbsMgr.getBBSUserNUM("postnum",id)+1;
      //每发表一片新文章加2分
      bbsMgr.updateBBSUser("logintime",id,0);
      bbsMgr.updateBBSUser("postnum",id,postnum);

      Timestamp nowtime = new Timestamp(System.currentTimeMillis());
      String ipaddress = request.getRemoteAddr();

      //用户发新帖，更新用户在线登录时间
      bbs = new BBS();
      bbs.setUserName(username);
      bbs.setLoginTime(nowtime);
      bbs.setIPAddress(ipaddress);

      //删除该用户在在线表中的记录
      bbsMgr.deleteOnline(bbs);

      //用户登录成功，将新的用户登录时间插入用户在线表
      bbsMgr.insertOnline(bbs);

			//获得最大的threadid
			int threadid = bbsMgr.getMaxThreadID() + 1;

      //用户登录成功，将用户的新帖插入tbl_thread表
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

      //更新表tbl_column中的最后发表的人的信息
      int threadnum = bbsMgr.getNUM("threadnum",forumid);
      int topicnum = bbsMgr.getNUM("topicnum",forumid) + 1;
      bbsMgr.updateLastNum(threadnum,topicnum,forumid);
      bbsMgr.updateLastPost(username,title,nowtime,forumid);

      response.sendRedirect("list.jsp?forumid="+forumid+"&startflag=1");
    }else{
      response.sendRedirect("posttopic1.jsp?loginflag=1&forumid="+forumid);
    }
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
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="tittle">
<%
  bbs = bbsMgr.getAFORUM(forumid);
%>
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
<%
  int threadcount = bbsMgr.getAllThreadNum(0, forumid);
  int answercount = bbsMgr.getAllThreadNum(1, forumid);
  int onlinecount = bbsMgr.getAllThreadNum(2, 0);
%>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="txt">
<tr bgcolor="F1F2EC">
<form  method="post" action="searchuser.jsp" name="searchform">
<td>目前论坛总在线 <b><%=onlinecount%></b> 人&nbsp;&nbsp;&nbsp;&nbsp;本论坛发贴总数 <b><%=threadcount%></b>&nbsp;&nbsp;&nbsp;&nbsp;回复总数
<b><%=answercount%></b> [<a href="online.jsp" target=_blank>在线用户</a>] &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户查询&nbsp;
<input type="text" name="search_user" size=8>
&nbsp;
<input type=button value="查询" onClick="javascript:searchuser(this.form);" name="button">
</td>
</form>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="4">
<tr>
<td bgcolor="#FFFFFF" valign="top" align="center">
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="txt">
<form name="postForm" method="post" action="posttopic1.jsp">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="forumid" value="<%=forumid%>">
<tr>
<td align="left">注意：非注册用户没有发表信息的权利。<a href="/login.jsp" class="red" target=_blank>登陆社区</a>>>　<a href="/reg1.jsp" class="red" target=_blank>注册</a>>></td>
</tr>
<tr>
<td>用户名：
<input type="text" name="username" size="15">
密码：
<input type="password" name="password" size="15">
</td>
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
<input type="submit" name="Submit2" value="发表">
</td>
</tr>
</form>
</table>
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

