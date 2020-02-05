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
  String sql = "select * from bbs_thread where 1=1 ";
  sql = sql + "and answerid = 0 and typeflag = 1 order by posttime desc";

  IBBSManager bbsMgr = BBSPeer.getInstance();
  BBS bbs = new BBS();

  List list = new ArrayList();
  list = bbsMgr.getAllThread(sql);
%>
<html>
<head>
<title>欢迎访问北京城建投资发展股份有限公司</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet href="images/chengjian.css" style="text/css">
</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<center>
<%@include file="head.jsp"%>

<table width="778" border="0" cellspacing="1" cellpadding="4" class="txt" align="center">
<tr bgcolor="#0877af" align="center">
<td width="5%"></td>
<td width="40%"><font color="#FFFFFF"><b>主题</b></font></td>
<td width="15%"><font color="#FFFFFF"><b>作者 </b></font></td>
<td width="15%"><font color="#FFFFFF"><b>回复/人气</b></font></td>
<td width="15%"><font color="#FFFFFF"><b>最后更新 </b></font></td>
<td><font color="#FFFFFF"><b>回复人</b></font></td>
</tr>
</table>
<table width="778" border="0" cellspacing="0" cellpadding="4" class="txt">
      <table width="778" border="0" cellspacing="0" cellpadding="4" class="txt" align="center">
<%
  String bgcolor = "";
  int threadid = 0;
  String threadname = "";
  int answernum = 0;
  int hitnum = 0;
  String answertime = "";
  String answeruser = "";
  String author = "";
  int typeflag = 0;
  for(int i=0;i<list.size();i++)
  {
    bbs = (BBS)list.get(i);
    threadid = bbs.getThreadID();
    threadname = bbs.getThreadName();
    author = bbs.getAuthor();
    answernum = bbs.getAnswerNum();
    hitnum = bbs.getHitNum();
    answertime = bbs.getAnswerTime().toString().substring(0, 19);
    answeruser = bbs.getAnswerUser();
    typeflag = bbs.getTypeFlag();

    if(i % 2 == 0)
    {
      bgcolor = "#FFFFFF";
    }else{
      bgcolor = "#F4F4F4";
    }
%>
<tr>
<%
  if(typeflag == 0){
%>
<td bgcolor="<%=bgcolor%>" width="5%"><img src="images/blue-face.GIF"></td>
<%}else if(typeflag == 1){%>
<td bgcolor="<%=bgcolor%>" width="5%"><img src="images/red-face.GIF"></td>
<%}else{%>
<td bgcolor="<%=bgcolor%>" width="5%"><img src="images/black-face.GIF"></td>
<%}%>
<td bgcolor="<%=bgcolor%>" width="40%"><a href="updateHitnum.jsp?threadid=<%=threadid%>&forumid=<%=bbs.getFroumID()%>" target=_blank><%=threadname%></a></td>
<td bgcolor="<%=bgcolor%>" width="15%"><a href="user.jsp?userid=<%=author%>" target=_blank><%=author%></a></td>
<td bgcolor="<%=bgcolor%>" width="15%"><%=answernum%>/<%=hitnum%></td>
<td bgcolor=<%=bgcolor%>><%=answertime%></td>
<td bgcolor=<%=bgcolor%>><a href="user.jsp?userid=<%=answeruser%>" target=_blank><%=answeruser%></a></td>
</tr>
<%
  }
%>
</table>
</table>
<table align="center" width="778" border="0" cellspacing="0" cellpadding="0" background="images/dot-line2.gif">
<tr>
<td><img src="images/space.gif" width="1" height="1"></td>
</tr>
</table>
      <table align="center" width="95%" border="0" cellspacing="0" cellpadding="4" class="txt">
        <tr>
          <td>&nbsp;</td>
          <td align="right">&nbsp;</td>
        </tr>
      </table>
<%@include file="tail.jsp"%>
</td>
</tr>
</table>
</center>
</td>
</tr>
</table>
</body>
</html>

