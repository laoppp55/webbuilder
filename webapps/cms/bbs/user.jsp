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
  String userid = ParamUtil.getParameter(request, "userid");
  String username = (String)session.getAttribute("userid");
  String message = ParamUtil.getParameter(request, "message");
  int startflag = ParamUtil.getIntParameter(request, "startflag", 0);

  IRegisterManager registerMgr = RegisterPeer.getInstance();
  Register register = new Register();
  IBBSManager bbsMgr = BBSPeer.getInstance();
  BBS bbs = new BBS();

  register = registerMgr.getAUser(userid);
  bbs = bbsMgr.getUserInfo(userid);
%>
<html>
<head>
<title>布衣书局</title>
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
          <td width="40%"><font color="#FFFFFF"><b><%=userid%>个人信息</b></font></td>
</tr>
</table>
      <table width="778" border="0" cellspacing="1" cellpadding="4" class="txt">
        <tr align="center">
          <td bgcolor="#f4f4f4" width="25%"><b>注册日期: </b></td>
          <td bgcolor="#f4f4f4" class="red" width="25%"><%=register.getCreateDate()==null?"":register.getCreateDate().toString().substring(0,16)%> </td>
          <td bgcolor="#f4f4f4" width="25%"><b>上站次数: </b></td>
          <td bgcolor="#f4f4f4" class="red"><%=register.getLoginNum()%></td>
        </tr>
        <tr align="center">
          <td><b>最新上站: </b></td>
          <td class="red"><%=bbs.getLoginTime()==null?"":bbs.getLoginTime().toString().substring(0,16)%></td>
          <td><b>个人得分:</b></td>
          <td><%=bbs.getScore()%></td>
        </tr>
        <tr align="center">
          <td bgcolor="#f4f4f4"><b>回复次数: </b></td>
          <td bgcolor="#f4f4f4" class="red"><%=bbs.getAnswerNum()%> </td>
          <td bgcolor="#f4f4f4"><b>发表论题: </b></td>
          <td bgcolor="#f4f4f4"><%=bbs.getPostNum()%></td>
        </tr>
      </table>
<br><br><br><br>
<%@include file="tail.jsp"%>
</center>
</td>
</tr>
</table>
</body>
</html>
    