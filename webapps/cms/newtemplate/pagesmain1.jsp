<%@ page import="java.sql.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.audit.*,
                 java.util.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.systmeTemplate.*"
                 contentType="text/html;charset=gbk"
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
  String[][] titlebars = {
    { "选择系统模版", "" }
  };

  String[][] operations = {
          { "", ""},
          {"",""}
  };
%>
<%@ include file="../inc/titlebar.jsp" %>
</BODY>
</html>