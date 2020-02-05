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
  int threadid          = ParamUtil.getIntParameter(request, "threadid", 0);
  String username       = (String)session.getAttribute("userid");
  String password       = ParamUtil.getParameter(request, "password");
  String title          = ParamUtil.getParameter(request, "title");
  String content        = ParamUtil.getParameter(request, "content");
  String url            = ParamUtil.getParameter(request, "url");
  int startflag         = ParamUtil.getIntParameter(request, "startflag", -1);
  int forumid           = ParamUtil.getIntParameter(request, "forumid", 0);
  int loginflag         = ParamUtil.getIntParameter(request, "loginflag", 0);

  IRegisterManager registerMgr = RegisterPeer.getInstance();
  IBBSManager bbsMgr = BBSPeer.getInstance();
  BBS bbs = new BBS();
  BBS manager = new BBS();
  manager = bbsMgr.getAFORUM(forumid);

  String forummanager = manager.getManager();
  //获得论坛斑竹
  String[] bz = null;
  boolean isbanzhu = false;

  if((username != "")&&(username != null)&&(!username.equals("null"))){
   if(forummanager != null){
    if(forummanager.indexOf(",") != -1){
      bz = forummanager.split(",");

      for(int j=0;j<bz.length;j++){
        if((username.equals(bz[j]))||(username.equals("admin"))){
          isbanzhu = true;
          break;
        }
      }
    }else{
      if((username.equals(forummanager))||(username.equals("admin")))
        isbanzhu = true;
    }
   }
  }

  String getusername = "";
  if((username == "")||(username == null)||(!username.equals("null"))){
    getusername = ParamUtil.getParameter(request, "username");
  }

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

  String threadname = "";
  String threadcontent = "";
  String author = "";
  String posttime = "";
  int answerid = 0;
  int lockflag = 0;
  List list = new ArrayList();

  bbs = bbsMgr.getAThread(threadid);
  lockflag = bbs.getLockFlag();
  author = bbs.getAuthor();
  posttime = bbs.getPostTime().toString().substring(0, 19);
  threadname = bbs.getThreadName();
  threadcontent = bbs.getThreadContent();
  answerid = bbs.getAnswerID();
  if(answerid != 0){
    bbs = bbsMgr.getAThread(answerid);
    threadid = bbs.getThreadID();
  }

  list = bbsMgr.getAnswerThread(threadid);

  if(loginflag == 1) {
    String locstr = "window.location=\"thread.jsp?threadid="+threadid+"&forumid="+forumid+"\";";
    out.println("<script language=javascript>");
    out.println("alert(\"您输入的用户名或密码错误！\");");
    out.println(locstr);
    out.println("</script>");
  }

  int id = 0;
	Register register = registerMgr.getAUser(username);
	id = register.getID();
	String grade = register.getGrade();

  if((startflag == 1)&&(loginflag == 0)) {
    if((username == "")||(username == null)||(username.equals("null"))){
      boolean existflag = false;

      existflag = registerMgr.userLogin(getusername,password,0);
      if(existflag){
				register = registerMgr.getAUser(getusername);
				id = register.getID();
				grade = register.getGrade();
        session.setAttribute("userid",getusername);
        session.setAttribute("password",password);
        session.setAttribute("UserClass",grade);

        //用户登录成功，判断bbs_user表中是否有该用户的信息，若没有则添加
        username = (String)session.getAttribute("userid");
        boolean bbs_existflag = bbsMgr.CheckBBSUser(username);

        //用户的登录成功计数
        int loginnum = registerMgr.getUserLoginNum(username) + 1;
        registerMgr.updateUserLoginNum(id, loginnum);

        if(!bbs_existflag){
          bbsMgr.insertBBSUser(username);
        }else{
          int answernum = bbsMgr.getBBSUserNUM("answernum",id)+1;

          //每发表一片新文章加2分
          int score = bbsMgr.getBBSUserNUM("score",id)+2;
          bbsMgr.updateBBSUser("logintime",id,0);
          bbsMgr.updateBBSUser("answernum",id,answernum);
          bbsMgr.updateBBSUser("score",id,score);
        }

      }else{
        response.sendRedirect("thread.jsp?loginflag=1&forumid="+forumid+"&threadid="+threadid);
        return;
      }
    }

    Timestamp nowtime = new Timestamp(System.currentTimeMillis());
    String ipaddress = request.getRemoteAddr();

    //用户发新帖，更新用户在线登录时间
    bbs = new BBS();
    if((username == "")||(username == null)||(username.equals("null"))){
      bbs.setUserName(getusername);
    }else{
      bbs.setUserName(username);
    }
    bbs.setLoginTime(nowtime);
    bbs.setIPAddress(ipaddress);

    //删除该用户在在线表中的记录
    bbsMgr.deleteOnline(bbs);

    //用户登录成功，将新的用户登录时间插入用户在线表
    bbsMgr.insertOnline(bbs);

    //用户登录成功，将用户的新帖插入tbl_thread表
    bbs = new BBS();
    bbs.setForumID(forumid);
    bbs.setAnswerID(threadid);
    bbs.setThreadName("回复："+threadname);
    bbs.setThreadContent(content);
    bbs.setPostTime(nowtime);
    if((username == "")||(username == null)||(username.equals("null"))){
      bbs.setAuthor(getusername);
    }else{
      bbs.setAuthor(username);
    }
    bbs.setIPAddress(ipaddress);
    if((username == "")||(username == null)||(username.equals("null"))){
      bbs.setAnswerUser(getusername);
    }else{
      bbs.setAnswerUser(username);
    }
    bbs.setAnswerTime(nowtime);
    bbsMgr.PostThread(bbs);

    //更新表tbl_column中的最后发表的人的信息
    int threadnum = bbsMgr.getNUM("threadnum",forumid) + 1;
    int topicnum = bbsMgr.getNUM("topicnum",forumid);
    bbsMgr.updateLastNum(threadnum,topicnum,forumid);
    if((username == "")||(username == null)||(username.equals("null"))){
      bbsMgr.updateLastPost(getusername,"回复："+threadname,nowtime,forumid);
    }else{
      bbsMgr.updateLastPost(username,"回复："+threadname,nowtime,forumid);
    }

    //更新表tbl_thread表中的answernum
    int hitnum = bbsMgr.getNUM("hitnum",threadid);
    int answernum = bbsMgr.getNUM("answernum",threadid) + 1;
    bbsMgr.updateAnswerHitNum(hitnum,answernum,threadid);

    //更新表tbl_thread中的最后发表人的信息
    if((username == "")||(username == null)||(username.equals("null"))){
      bbsMgr.updateLastAnswer(getusername,nowtime,threadid);
    }else{
      bbsMgr.updateLastAnswer(username,nowtime,threadid);
    }

    //更新表bbs_user表中的信息
    answernum = bbsMgr.getBBSUserNUM("answernum",id)+1;

    //每发表一片新文章加2分
    int score = bbsMgr.getBBSUserNUM("score",id)+2;
    bbsMgr.updateBBSUser("logintime",id,0);
    bbsMgr.updateBBSUser("answernum",id,answernum);
    bbsMgr.updateBBSUser("score",id,score);

    response.sendRedirect("thread.jsp?forumid="+forumid+"&threadid="+threadid);
  }
%>
<html>
<head>
<title>欢迎访问北京城建投资发展股份有限公司</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet href="images/chengjian.css" style="text/css">
<script language="javascript">
function check(){
  if((postForm.username.value == "")||(postForm.username.value == null)){
    alert("请输入您的用户名！");
    return false;
  }

  if((postForm.password.value == "")||(postForm.password.value == null)){
    alert("请输入您的密码！");
    return false;
  }

  if((postForm.content.value == "")||(postForm.content.value == null)){
    alert("请输入回复内容！");
    return false;
  }
  return true;
}

function checkcontent(){
  if((postForm.content.value == "")||(postForm.content.value == null)){
    alert("请输入回复内容！");
    return false;
  }
  return true;
}

function conf(answerid,forumid,threadid){
  var val = confirm("你确定要删除本帖子吗？");
  if(val == 1){
    window.location="delete.jsp?threadid="+threadid+"&forumid="+forumid+"&answerid="+answerid;
  }
}
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#FFFFFF">
<center>
<table width="778" border="0" cellspacing="0" cellpadding="0">
<tr>
<td>
<table width="100%" border="0" cellspacing="1" cellpadding="4" class="txt1">
<tr bgcolor="#0877AF" align="center">
<td width="40%"><font color="#FFFFFF"><b><%=threadname%></b></font></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="txt1">
<tr bgcolor="#FFFFFF">
<td align="center"> <font color="#3399CC">作者：</font><a href="user.jsp?userid=<%=author%>" target=_blank><%=author%></a>
<font color="#3399CC">提交日期：</font><%=posttime%> </td>
</tr>
<tr bgcolor="#f4f4f4">
<td><%=threadcontent%></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="txt1">
<%
  int answerthreadid = 0;
  for(int i=0;i<list.size();i++)
  {
    bbs = (BBS)list.get(i);
    answerthreadid = bbs.getThreadID();
    threadname = bbs.getThreadName();
    threadcontent = bbs.getThreadContent();
    author = bbs.getAuthor();
    posttime = bbs.getPostTime().toString().substring(0, 19);
%>
<tr bgcolor="#FFFFFF">
<td align="center"> <font color="#3399CC">作者：</font><a href="user.jsp?userid=<%=author%>" target=_blank><%=author%></a>
<font color="#3399CC">提交日期：</font><%=posttime%> </td>
<%
  if((username != null)&&(username != "")&&(!username.equals("null"))){
    if(isbanzhu){
%>
          <td align="right" width="20"><a href="edit.jsp?threadid=<%=answerthreadid%>" target=_blank><img src="images/note.gif" border=0></a></td>
          <td align="right" width="20"><a href="#" onclick="javascript:conf(<%=answerthreadid%>,<%=forumid%>,<%=threadid%>);"><img src="images/del2.gif" border=0></a></td>
<%}}%>
</tr>
<tr bgcolor="#f4f4f4">
<%
  if((username != null)&&(username != "")&&(!username.equals("null"))){
    if(isbanzhu){
%>
          <td colspan=3><%=threadcontent%></td>
<%}else{%>
          <td><%=threadcontent%></td>
<%}}else{%>
  <td><%=threadcontent%></td>
<%}%>
</tr>
<%
  }
%>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/dot-line2.gif">
<tr>
<td><img src="images/space.gif" width="1" height="1"></td>
</tr>
</table>
<%
  if((username != null)&&(username != "")&&(!username.equals("null"))){
    if(isbanzhu){
%>
      <table width="50%" border="0" cellspacing="0" cellpadding="4" class="txt" a>
        <tr>
          <td align="right"><a href="hidden.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>"><img src="images/hotfolder.gif" title="隐藏" border=0></a></td>
          <td align="right"><a href="hidden2.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>"><img src="images/fav.gif" title="解除隐藏" border=0></a></td>
          <td align="right"><a href="turnred.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>"><img src="images/warning.gif" title="漂红" border=0></a></td>
          <td align="right"><a href="turnred2.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>">取消漂红</a></td>
          <td align="right"><a href="redface.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>"><img src="images/red-face.GIF" title="红脸" border=0></a></td>
          <td align="right"><a href="redface2.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>"><img src="images/top3.gif" title="取消红脸" border=0></a></td>
          <td align="right"><a href="blankface.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>"><img src="images/black-face.GIF" title="黑脸" border=0></a></td>
          <td align="right"><a href="blankface2.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>"><img src="images/hao_7.gif" title="取消黑脸" border=0></a></td>
          <td align="right"><a href="gotop.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>"><img src="images/gotop.gif" title="置顶" border=0></a></td>
          <td align="right"><a href="gotop2.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>"><img src="images/firstnew.gif" title="解除置顶" border=0></a></td>
          <td align="right"><a href="lock.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>"><img src="images/hot_folder.gif" title="封帖" border=0></a></td>
          <td align="right"><a href="lock2.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>"><img src="images/hot_red_folder.gif" title="解除封帖" border=0></a></td>
          <td align="right"><a href="edit.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>" target=_blank><img src="images/note.gif" title="修改" border=0></a></td>
          <td align="right"><a href="#" onclick="javascript:conf(0,<%=forumid%>,<%=threadid%>);"><img src="images/del2.gif" title="删除" border=0></a></td>
        </tr>
      </table>
<%}}%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/dotline.gif">
<tr>
<td><img src="images/space.gif" width="1" height="1"></td>
</tr>
</table>
<br>
<%
  int userstatus = bbsMgr.getUserStatus(username);

  if((lockflag == 0)&&(userstatus != 3)){
%>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="txt1">
<form name="postForm" method="post" action="thread.jsp">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="forumid" value="<%=forumid%>">
<input type="hidden" name="threadid" value="<%=threadid%>">
<%
  if((username == "")&&(username == null)&&(username.equals("null"))){
%>
<tr>
<td align="left">注意：非注册用户没有发表信息的权利。<a href="/login.jsp" class="red" target=_blank>登陆社区</a>>>　<a href="/reg1.jsp" class="red" target=_blank>注册</a>>></td>
</tr>
<%}%>
<tr>
<td>用户名：
<%
  if((username == "")||(username == null)||(username.equals("null"))){
%>
<input type="text" name="username" size="15">
密码：
<input type="password" name="password" size="15">
<%
  }else{
%>
<%=username%>
<%}%>
</td>
</tr>
<tr>
<td>
<textarea name="content" cols="80" rows="10"></textarea>
</td>
</tr>
<tr>
<td>
<%
  if((username == "")||(username == null)||(username.equals("null"))){
%>
<input type="submit" name="Submit2" value="发表" onClick="javascript:return check();">
<%}else{%>
<input type="submit" value="发表" onClick="javascript:return checkcontent();">
<%}%>
</td>
</tr>
</form>
</table>
<%}%>
</td>
</tr>
</table>
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td><img src="images/space.gif" width="10" height="10"></td>
</tr>
</table>
<table width="778" border="0" cellspacing="0" cellpadding="0">
<tr bgcolor="#0877AF">
<td><img src="images/space.gif" width="2" height="2"></td>
</tr>
</table>
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td><img src="images/space.gif" width="10" height="10"></td>
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

