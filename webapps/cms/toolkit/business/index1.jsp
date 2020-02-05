<%@ page import="java.sql.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"%>

<%
  int type = ParamUtil.getIntParameter(request, "type", 0);
  if(type == 0){
%>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=style/global.css>
<title>内容管理系统</title>
</head>
<frameset rows=75,*,40 border=0 frameborder=0 framespacing=0 name=parentFrame>
<frame src=header.jsp name=header scrolling=no marginheight=5 marginwidth=5 noresize>
<frame src=main.jsp name=main scrolling=auto noresize>
<frame src=footer.jsp name=bottom scrolling=no noresize>
</frameset>
</html>
<%}else{%>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=style/global.css>
<title>内容管理系统</title>
</head>
<frameset rows=75,*,40 border=0 frameborder=0 framespacing=0 name=parentFrame>
<frame src=header2.jsp name=header scrolling=no marginheight=5 marginwidth=5 noresize>
<frame src=main.jsp name=main scrolling=auto noresize>
<frame src=footer.jsp name=bottom scrolling=no noresize>
</frameset>
</html>
<%}%>
