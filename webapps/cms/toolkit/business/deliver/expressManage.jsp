<%@ page contentType="text/html;charset=gbk"%>
<html><head><title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>
<%
 String[][] titlebars = {
   { "送货方式管理", "" },
   { "邮政快递包裹", "" }
   };
   String[][] operations = {
     {"<a href=provManage.jsp>省市县管理</a>", ""},
     {"<a href=postManage.jsp>邮政普递</a>", ""},
     {"<a href=emsManage.jsp>快递公司</a>", ""}
   };
%>
<%@ include file="../inc/titlebar.jsp" %>
</BODY></html>