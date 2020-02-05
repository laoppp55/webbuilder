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
  IBBSManager bbsMgr = BBSPeer.getInstance();
  BBS bbs = new BBS();
  List list = new ArrayList();
  list = bbsMgr.getAllOnlineUser();
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

<table width="778" border="0" cellspacing="0" cellpadding="10">
<tr>
<td bgcolor="#FFFFFF" valign="top" align="center">
<table width="778" border="0" cellspacing="1" cellpadding="4" class="txt">
<tr bgcolor="#0877af" align="center">
          <td width="40%"><font color="#FFFFFF"><b>在线用户列表（共<%=list.size()%>个）</b></font></td>
</tr>
</table>
<table width="778" border="0" cellspacing="1" cellpadding="4" class="txt">
<%
  String bgcolor = "";
  String username = "";
  int rowsnum = -1;
  for(int i=0;i<list.size();i++){
    bbs = (BBS)list.get(i);
    username = bbs.getUserName();

    if(i%6 == 0){
      rowsnum = rowsnum + 1;
      if(rowsnum%2 == 0){
        bgcolor = "#f4f4f4";
      }else{
        bgcolor = "#ffffff";
      }
%>
<tr align="center" bgcolor=<%=bgcolor%>>
<%
    }
%>
<td><a href="user.jsp?userid=<%=username%>" target=_blank><%=username%></a></td>
<%
    if((i%6 == 5)&&(i > 1)){
%>
</tr>
<%
    }
  }

  if(list.size() % 6 != 0){
%>
</tr>
<%
  }
%>
</table>
<table width="95%" border="0" cellspacing="0" cellpadding="0" background="images/dot-line2.gif">
<tr>
<td><img src="images/space.gif" width="1" height="1"></td>
</tr>
</table>
<br><br><br><br>
<%@include file="tail.jsp"%>
</center>
</body>
</html>

