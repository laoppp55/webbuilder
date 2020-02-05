<%@ page import="com.bizwink.stockinfo.ISpiderManager" %>
<%@ page import="com.bizwink.stockinfo.SpiderPeer" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    ISpiderManager stoMgr = SpiderPeer.getInstance();
    long ttt = stoMgr.getStockpriceFormOraAndMysql("SOHU");
%>

<html>
<head>
    <title>测试数据库访问时间</title>
</head>
<body>
<%=ttt%>
</body>
</html>