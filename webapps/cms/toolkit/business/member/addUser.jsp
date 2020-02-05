<%@ page contentType="text/html;charset=gbk"%>
<%@ include file="../../../include/auth.jsp"%>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>用户管理</title>
</head>
<%
  String userid = request.getParameter("userid");
  String file = "newuser.jsp";
  if (userid != null) {
    file ="editUser.jsp?userid="+userid;
  }
%>
<center>
<frameset cols=* border=0 frameborder=0 framespacing=0>
<frame src=<%=file%> name=cmsright scrolling=auto marginheight=0 marginwidth=5 noresize>
</frameset>
</center>
</html>