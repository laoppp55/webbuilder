<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                java.text.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.bizwink.bbs.bbs.*" contentType="text/html;charset=gbk"
%>
<%
  int id                  = ParamUtil.getIntParameter(request, "forumid", 0);
  int startflag           = ParamUtil.getIntParameter(request, "startflag", 0);
  int startrow            = ParamUtil.getIntParameter(request, "startrow", 0);
  int range               = ParamUtil.getIntParameter(request, "range", 50);
  int orderflag           = ParamUtil.getIntParameter(request, "orderflag", 0);
  String username       = (String)session.getAttribute("userid");

  List list = new ArrayList();
  List currentlist = new ArrayList();
  List topcurrentlist = new ArrayList();
  int rows = 0;
  int totalpages = 0;
  int currentpage = 0;

  IBBSManager bbsMgr = BBSPeer.getInstance();
  BBS bbs = new BBS();
  BBS forumbbs = new BBS();
  forumbbs = bbsMgr.getAFORUM(id);
  String forummanager = forumbbs.getManager();
  if(forummanager == null){
    forummanager = "招聘中...";
  }

  if(startflag == 1){
    //读取置顶的帖子
    topcurrentlist = bbsMgr.getCurrentThread(id, 0, range, orderflag,1);

    list = bbsMgr.getAllThread(id,orderflag,0);
    if(startrow == 0){
      currentlist = bbsMgr.getCurrentThread(id, startrow, (range-topcurrentlist.size()), orderflag,0);
    }else{
      currentlist = bbsMgr.getCurrentThread(id, (startrow-topcurrentlist.size()), range, orderflag,0);
    }

    rows = list.size();

    if(rows < range){
      totalpages = 1;
      currentpage = 1;
    }else{
      if(rows%range == 0)
        totalpages = rows/range;
      else
        totalpages = rows/range + 1;

      currentpage = startrow/range + 1;
    }
  }
%>
<html>
<head>
<title>欢迎访问北京城建投资发展股份有限公司</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet href="images/chengjian.css" style="text/css">
<script language="javascript">
function golistnew(r){
  listform.action = "list.jsp?startrow="+r+"&startflag=1&forumid=<%=id%>&orderflag=<%=orderflag%>";
  listform.submit();
}

function search(frm){
  var title = frm.vtitle.value;
  var author = frm.vwriter.value;
  window.open("search.jsp?vtitle="+title+"&vwriter="+author+"&forumid=<%=id%>","查询","");
}

function searchuser(frm){
  var user = frm.search_user.value;
  window.open("searchuser.jsp?user="+user,"查询","");
}
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#FFFFFF">
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
<%
  bbs = bbsMgr.getAFORUM(id);
%>
<table width="100%" border="0" cellspacing="0" cellpadding="4">
<form method=post name="loginform" action="login.jsp">
<input type="hidden" name="startflag" value="1">
<tr>
<td class="tittle"><b><span class="tittle">您的位置: </span></b><span class="tittle"><a href="/bbs/index.jsp">城建论坛</a></span> >> </span></b><span class="tittle"><a href="list.jsp?forumid=<%=id%>&startflag=1"><%=bbs.getBBSName()%></a></span></td>
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
<%
  int threadcount = bbsMgr.getAllThreadNum(0, id);
  int answercount = bbsMgr.getAllThreadNum(1, id);
  int onlinecount = bbsMgr.getAllThreadNum(2, 0);
%>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="txt">
<tr bgcolor="F1F2EC">
<form  method="post" action="searchuser.jsp" name="searchform">
<td>目前论坛总在线 <b><%=onlinecount%></b> 人&nbsp;&nbsp;&nbsp;&nbsp;本论坛发贴总数 <b><%=threadcount%></b>&nbsp;&nbsp;&nbsp;&nbsp;回复总数
<b><%=answercount%></b> [<a href="online.jsp" target=_blank>在线用户</a>] &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--用户查询&nbsp;
<input type="text" name="search_user" size=8>
&nbsp;
<input type=button value="查询" onClick="javascript:searchuser(this.form);" name="button"-->
</td>
</form>
</tr>
</table>
<%
  //获得论坛斑竹
  String[] bz = null;
  String formmang = "";
  if(forummanager.indexOf(",") != -1){
    bz = forummanager.split(",");

    for(int j=0;j<bz.length;j++){
      formmang = formmang + "<a href=\"user.jsp?userid=" + bz[j] + "\" target=_blank>" + bz[j] + "</a>" + "&nbsp;&nbsp;";
    }
  }else{
    formmang = forummanager;
  }

  boolean isbanzhu = false;
  if(forummanager.indexOf(",") != -1){
    bz = forummanager.split(",");

    if((username != "")&&(username != null)&&(!username.equals("null"))){
      for(int j=0;j<bz.length;j++){
        if((username.equals(bz[j]))||(username.equals("admin"))){
          isbanzhu = true;
          break;
        }
      }
    }
  }else{
    if((username != "")&&(username != null)&&(!username.equals("null"))){
      if((username.equals(forummanager))||(username.equals("admin")))
        isbanzhu = true;
    }
  }

%>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="txt">
<tr>
<td><img src="images/icon-newarticle.gif" width="11" height="11" align="absmiddle">
<a href="checklogin.jsp?forumid=<%=id%>" class="red"><b>发新贴</b></a> <img src="images/icon-reflash.gif" width="14" height="13" align="absmiddle">
<a href="list.jsp?startrow=<%=startrow%>&startflag=1&forumid=<%=id%>" class="red"><b>刷新</b></a></td>
<td align="right"><%if(isbanzhu){%><a href="hiddened.jsp?forumid=<%=id%>" target=_blank>已被隐藏的帖子</a><%}%> <a href="jinghua.jsp" target=_blank>论坛精华</a>　 论坛管理员：<%if(!formmang.equals("招聘中...")){%><%=formmang%><%}else{%>招聘中... <%}%></a>&nbsp;&nbsp;</td>
</tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="4" class="txt">
<tr bgcolor="#A5BCC2" align="center">
<td width="5%" bgcolor="#0877AF"></td>
<td width="40%" bgcolor="#0877AF"><font color="#FFFFFF"><b>主题</b></font></td>
<td width="15%" bgcolor="#0877AF"><font color="#FFFFFF"><b>作者 </b></font></td>
<td width="15%" bgcolor="#0877AF"><font color="#FFFFFF"><b>回复/人气</b></font></td>
<td width="15%" bgcolor="#0877AF"><font color="#FFFFFF"><b>最后更新 </b></font></td>
<td bgcolor="#0877AF"><font color="#FFFFFF"><b>回复人</b></font></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="4" class="txt">
<%
  String bgcolor = "";
  int threadid = 0;
  String threadname = "";
  String author = "";
  int answernum = 0;
  int hitnum = 0;
  String answertime = "";
  String answeruser = "";
  int typeflag = -1;
  int lockflag = -1;

  if(startrow == 0){
    for(int i=0;i<topcurrentlist.size();i++)
    {
      bbs = (BBS)topcurrentlist.get(i);
      threadid = bbs.getThreadID();
      threadname = bbs.getThreadName();
      author = bbs.getAuthor();
      answernum = bbs.getAnswerNum();
      hitnum = bbs.getHitNum();
      answertime = bbs.getAnswerTime().toString().substring(0, 19);
      answeruser = bbs.getAnswerUser();
      typeflag = bbs.getTypeFlag();
      lockflag = bbs.getLockFlag();

      if(i % 2 == 0)
      {
        bgcolor = "#F4F4F4";
      }else{
        bgcolor = "#FFFFFF";
      }
%>
<tr bgcolor="<%=bgcolor%>">
<%
  if(typeflag == 0){
%>
<td bgcolor="<%=bgcolor%>" width="5%"><img src="images/blue-face.GIF"></td>
<%}else if(typeflag == 1){%>
<td bgcolor="<%=bgcolor%>" width="5%"><img src="images/red-face.GIF"></td>
<%}else{%>
<td bgcolor="<%=bgcolor%>" width="5%"><img src="images/black-face.GIF"></td>
<%}%>
<td width="40%"><a href="updateHitnum.jsp?threadid=<%=threadid%>&forumid=<%=id%>" target=_blank><%=threadname%></a></td>
<td width="15%"><a href="user.jsp?userid=<%=author%>" target=_blank><%=author%></a></td>
<td width="15%"><%=answernum%>/<%=hitnum%></td>
<td width="15%"><%=answertime%></td>
<td><a href="user.jsp?userid=<%=answeruser%>" target=_blank><%=answeruser%></a></td>
</tr>
<%
		}
	}

  for(int i=0;i<currentlist.size();i++)
  {
    bbs = (BBS)currentlist.get(i);
    threadid = bbs.getThreadID();
    threadname = bbs.getThreadName();
    author = bbs.getAuthor();
    answernum = bbs.getAnswerNum();
    hitnum = bbs.getHitNum();
    answertime = bbs.getAnswerTime().toString().substring(0, 16);
    answeruser = bbs.getAnswerUser();
    typeflag = bbs.getTypeFlag();
    lockflag = bbs.getLockFlag();

    if(i % 2 == 0)
    {
      bgcolor = "#F4F4F4";
    }else{
      bgcolor = "#FFFFFF";
    }
%>
<tr bgcolor="<%=bgcolor%>">
<%
  if(typeflag == 0){
%>
<td bgcolor="<%=bgcolor%>" width="5%"><img src="images/blue-face.GIF"></td>
<%}else if(typeflag == 1){%>
<td bgcolor="<%=bgcolor%>" width="5%"><img src="images/red-face.GIF"></td>
<%}else{%>
<td bgcolor="<%=bgcolor%>" width="5%"><img src="images/black-face.GIF"></td>
<%}%>
<td width="40%"><a href="updateHitnum.jsp?threadid=<%=threadid%>&forumid=<%=id%>" target=_blank><%=threadname%></a></td>
<td width="15%"><a href="user.jsp?userid=<%=author%>" target=_blank><%=author%></a></td>
<td width="15%"><%=answernum%>/<%=hitnum%></td>
<td width="15%"><%=answertime%></td>
<td><a href="user.jsp?userid=<%=answeruser%>" target=_blank><%=answeruser%></a></td>
</tr>
<%}%>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/dotline.gif">
<tr>
<td><img src="images/space.gif" width="1" height="1"></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="txt">
<form  method="post" action="" name="listform">
<tr>
<td width=500 nowrap> 标题：
<input type="text" name="vtitle" size="8">
作者：
<input type="text" name="vwriter" size="6">
<input type="submit" value="查询" name="B1" onClick="javascript:search(this.form);">
<a href="list.jsp?orderflag=1&forumid=<%=id%>&startflag=1">按论题提交日期排列</a>
<a href="list.jsp?orderflag=0&forumid=<%=id%>&startflag=1">按论题更新日期排列</a>
</td>
<td width = 140 align="right">
<a href="list.jsp?startrow=<%=startrow%>&startflag=1&forumid=<%=id%>">刷新</a>
 <%if(currentpage>1){%>
   <a href="javascript:golistnew(<%=(startrow-range)%>);">上一页</a>
 <%}else{%>
   <font color="#CCCCCC">上一页</font>
 <%}%>
 <%if(currentpage==totalpages){%>
   <font color="#CCCCCC">下一页</font>
 <%}else{%>
   <a href="javascript:golistnew(<%=(startrow+range)%>);">下一页</a>
 <%}%>
</td>
</tr>
</form>
</table>
</td>
</tr>
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
